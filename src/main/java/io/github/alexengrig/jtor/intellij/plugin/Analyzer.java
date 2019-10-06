package io.github.alexengrig.jtor.intellij.plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Analyzer {
    private boolean isIterable;

    public void analyze(List<String> strings) {
        List<String[]> splits = new ArrayList<>();
        for (String string : strings) {
            splits.add(string.split("\\d+"));
        }
        isIterable = true;
        for (int i = 1; i < splits.size(); i++) {
            if (!Arrays.equals(splits.get(i - 1), splits.get(i))) {
                isIterable = false;
                break;
            }
        }
    }

    public boolean isIterable() {
        return isIterable;
    }
}
