package com.lint.azzert;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

public class LintAssert extends DefaultTask {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @TaskAction
    void lint(){
        System.out.printf("%s !\n", this.message);
    }
}
