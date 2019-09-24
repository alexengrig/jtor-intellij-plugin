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
import com.intellij.openapi.util.TextRange;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DemoAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        final Project project = event.getProject();
        final Editor editor = event.getData(CommonDataKeys.EDITOR);
        final Document document = editor.getDocument();
        CaretModel caretModel = editor.getCaretModel();
        List<Caret> carets = caretModel.getAllCarets();
        for (Caret caret : carets) {
            int start = caret.getSelectionStart();
            int end = caret.getSelectionEnd();
            String text = document.getText(TextRange.create(start, end));
            WriteCommandAction.runWriteCommandAction(project, () ->
                    document.insertString(start, text.toUpperCase())
            );
            caret.removeSelection();
        }
    }
}
