package com.yourcompany;

import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;

public class ToggleBoolean extends PsiElementBaseIntentionAction {
    @NotNull
    @Override
    public String getText() {
        return "Toggle boolean";
    }

    @NotNull
    @Override
    public String getFamilyName() {
        return getText();
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement psiElement) {
        return psiElement.getText().equals("true") ||
                psiElement.getText().equals("false");
    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement psiElement) throws IncorrectOperationException {
        String value = psiElement.getText();
        if (value.equals("true")) {
            value = "false";
        } else if (value.equals("false")) {
            value = "true";
        }
        PsiElementFactory psiElementFactory = PsiElementFactory.SERVICE.getInstance(project);
        psiElement.replace(psiElementFactory.createKeyword(value));
    }
}
