package org.lint.azzert;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;

/*
Test stand-alone:
mvn install
mvn lint-assert:lint-assert
 */
@Mojo(name = "lint-assert")
public class LintAssertMojo extends AbstractMojo {

    public void execute() {
        getLog().info("Hello from LintAssertMojo");
    }
}
