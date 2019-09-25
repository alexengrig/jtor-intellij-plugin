package io.github.alexengrig.jtor.intellij.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DemoAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        final Project project = event.getProject();
        final Editor editor = event.getData(CommonDataKeys.EDITOR);
        final Document document = editor.getDocument();
        CaretModel caretModel = editor.getCaretModel();
        List<Caret> carets = caretModel.getAllCarets();
        List<Integer> lines = carets.stream()
                .map(Caret::getLogicalPosition)
                .map(logicalPosition -> logicalPosition.line)
                .collect(Collectors.toList());
        List<Integer> offsets = carets.stream()
                .map(Caret::getOffset)
                .collect(Collectors.toList());
        List<String> texts = carets.stream()
                .map(Caret::getSelectedText)
                .collect(Collectors.toList());
        int count = lines.size();
        int step = getAverageStep(lines);
        List<String> generated = getGenerated(texts);
        Integer line = lines.get(lines.size() - 1);
        for (int i = 0; i < count; i++) {
            Integer offset = offsets.get(i);
            String text = generated.get(i);
            WriteCommandAction.runWriteCommandAction(project, () -> {
                document.insertString(offset, text);
            });
        }

        carets.forEach(Caret::removeSelection);
    }

    private int getAverageStep(List<Integer> lines) {
        List<Integer> steps = new ArrayList<>();
        for (int i = 1, length = lines.size(); i < length; i++) {
            steps.add(lines.get(i) - lines.get(i - 1));
        }
        return (int) steps.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0);
    }

    private List<String> getGenerated(List<String> texts) {
        List<Integer> numbers = new ArrayList<>();
        for (String text : texts) {
            String[] split = text.split("\\D+");
            numbers.add(Integer.valueOf(split[1]));
        }
        List<Integer> steps = new ArrayList<>();
        for (int i = 1, length = numbers.size(); i < length; i++) {
            steps.add(numbers.get(i) - numbers.get(i - 1));
        }
        int step = (int) steps.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0);
        Integer current = numbers.get(numbers.size() - 1);
        List<String> results = new ArrayList<>();
        for (String text : texts) {
            String[] split = text.split("\\d+");
            current += step;
            String result = split[0] + current;
            results.add(result);
        }
        return results;
    }
}
