package edu.stanford.nlp.semgraph.semgrex;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Properties;

/**
 * Created by sonalg on 7/15/14.
 */
public class SemgrexPatternITest extends TestCase {

  @Test
  public void testNER() throws Exception{
    String sentence = "John lives in California.";
    Properties props = new Properties();
    props.setProperty("annotators","tokenize, ssplit, pos, lemma, ner, parse");
    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
    Annotation doc = new Annotation(sentence);
    pipeline.annotate(doc);
    CoreMap sent = doc.get(CoreAnnotations.SentencesAnnotation.class).get(0);
    SemanticGraph graph = sent.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
    graph.prettyPrint();
    String patStr = "({word:/lives/} >/prep_in/ {word:/California/} >nsubj {ner:PERSON})";
    SemgrexPattern pat = SemgrexPattern.compile(patStr);
    SemgrexMatcher mat = pat.matcher(graph, true);
    assertTrue(mat.find());
  }

}
