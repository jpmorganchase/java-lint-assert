package com.lint.azzert;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class LintAssert extends DefaultTask {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @TaskAction
    void lint() throws IOException, ParseException {
        System.out.printf("%s !\n", this.message);

        new ContextBuilder().build();
    }
}
