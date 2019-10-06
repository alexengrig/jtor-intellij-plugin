package io.github.alexengrig.jtor.intellij.plugin;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class AnalyzerTest {
    @Test
    public void checkIterable() {
        List<String> strings = Arrays.asList("field1", "field2", "field3");
        Analyzer analyzer = new Analyzer();
        analyzer.analyze(strings);
        assertTrue(analyzer.isIterable());
    }

    @Test
    public void checkNotIterable() {
        List<String> strings = Arrays.asList("field1", "some2", "field3");
        Analyzer analyzer = new Analyzer();
        analyzer.analyze(strings);
        assertFalse(analyzer.isIterable());
    }
}