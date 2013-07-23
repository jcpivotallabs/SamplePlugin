package com.pivotallabs;

import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class ToggleBoolean extends PsiElementBaseIntentionAction {
    private static List<String> BOOLEAN_VALUES = Arrays.asList(new String[] { "true", "false" });

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
        return isBooleanPsiElement(psiElement) ||
                isAfterABooleanPsiElement(psiElement);
    }

    private boolean isAfterABooleanPsiElement(PsiElement psiElement) {
        String previousSiblingText = psiElement.getPrevSibling().getText();
        return BOOLEAN_VALUES.contains(previousSiblingText);
    }

    private boolean isBooleanPsiElement(PsiElement psiElement) {
        String text = psiElement.getText();
        return BOOLEAN_VALUES.contains(text);
    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement psiElement) throws IncorrectOperationException {
        PsiElement booleanPsiElement = getBooleanPsiElement(psiElement);
        String text = booleanPsiElement.getText();
        String keyword = null;
        if (text.equals("true")) {
            keyword = "false";
        } else if (text.equals("false")) {
            keyword = "true";
        }
        PsiElementFactory psiElementFactory = PsiElementFactory.SERVICE.getInstance(project);
        booleanPsiElement.replace(psiElementFactory.createKeyword(keyword));
    }

    private PsiElement getBooleanPsiElement(PsiElement psiElement) {
        if (isBooleanPsiElement(psiElement)) {
            return psiElement;
        } else {
            return psiElement.getPrevSibling();
        }
    }
}
