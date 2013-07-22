package com.yourcompany;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.openapi.application.PathManager;
import com.intellij.openapi.projectRoots.JavaSdk;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.fixtures.DefaultLightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class SamplePluginTest extends LightCodeInsightFixtureTestCase {
    public void testTogglingTrueToFalse() {
        myFixture.configureByFile("before" + getTestName(false) + ".java");
        IntentionAction intention = myFixture.findSingleIntention("Toggle boolean");
        myFixture.launchAction(intention);
        myFixture.checkResultByFile("after" + getTestName(false) + ".java");
    }

    public void testTogglingFalseToTrue() {
        myFixture.configureByFile("before" + getTestName(false) + ".java");
        IntentionAction intention = myFixture.findSingleIntention("Toggle boolean");
        myFixture.launchAction(intention);
        myFixture.checkResultByFile("after" + getTestName(false) + ".java");
    }

    private static class MyDescriptor extends DefaultLightProjectDescriptor {
        @Override
        public Sdk getSdk() {
            return JavaSdk.getInstance().createJdk(
                    "1.7",
                    new File(getSourceRoot(), "mockJDK-1.7").getPath(),
                    false
            );
        }

    }

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return new MyDescriptor();
    }

    @Override
    protected String getTestDataPath() {
        return new File(getSourceRoot(), "testData").getPath();
    }

    private static File getSourceRoot() {
        String testOutput = PathManager.getJarPathForClass(SamplePluginTest.class);
        return new File(testOutput, "../../..");
    }
}
