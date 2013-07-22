package com.yourcompany;

import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;

public class SamplePlugin extends PsiElementBaseIntentionAction {
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
        PsiJavaToken psiJavaToken = (PsiJavaToken) psiElement;
        return psiJavaToken.getTokenType() == JavaTokenType.TRUE_KEYWORD ||
                psiJavaToken.getTokenType() == JavaTokenType.FALSE_KEYWORD;
    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement psiElement) throws IncorrectOperationException {
        PsiJavaToken psiJavaToken = (PsiJavaToken) psiElement;
        IElementType tokenType = psiJavaToken.getTokenType();
        String keyword = null;
        if (tokenType == JavaTokenType.TRUE_KEYWORD) {
            keyword = "false";
        } else if (tokenType == JavaTokenType.FALSE_KEYWORD) {
            keyword = "true";
        }
        PsiElementFactory psiElementFactory = PsiElementFactory.SERVICE.getInstance(project);
        psiElement.replace(psiElementFactory.createKeyword(keyword));
    }
}
