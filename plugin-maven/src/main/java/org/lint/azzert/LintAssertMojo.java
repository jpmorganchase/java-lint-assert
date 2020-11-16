package org.lint.azzert;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.classworlds.realm.ClassRealm;
import org.lint.azzert.context.MethodMetadata;
import org.lint.azzert.processor.LintAssertBuildParameters;
import org.lint.azzert.processor.LintAssertProcessor;
import org.lint.azzert.strategy.output.ToStringStrategy;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Set;

/*
Test stand-alone:
mvn install lint-assert:lint-assert  -DprintMode=ALL
 */
@Mojo(name = "lint-assert")
public class LintAssertMojo extends AbstractMojo {

    @Parameter( property = "packageName", required = false)
    private String packageName;

    @Parameter( property = "includeClasspathJars", defaultValue = "false", required = false)
    private boolean includeClasspathJars;

    @Parameter( property = "verbose", required = false)
    private boolean verbose;

    @Parameter( property = "printMode", required = false)
    private String printMode = "ASSERTLESS_ONLY";

    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    protected MavenProject project;

    public void execute() {
        getLog().info(this.toString());
        try {
            Set<MethodMetadata> methodMetadata = new LintAssertProcessor(getUrlClassLoader(),
                    new LintAssertBuildParameters(packageName, verbose, includeClasspathJars, printMode)).process();

            final ToStringStrategy strategy = new ToStringStrategy(methodMetadata);
            String result = strategy.render();

            getLog().info(result);
        } catch (Exception e) {
            getLog().error("Failed to lint", e);
        }
    }

    URLClassLoader getUrlClassLoader() throws DependencyResolutionRequiredException {
        final List<String> testDir = this.project.getCompileClasspathElements();
        testDir.addAll(this.project.getTestClasspathElements());

        URL[] compiledTestsDirs = testDir.stream()
                .map(File::new)
                .map(File::toURI)
                .map(u -> {
                            try {
                                return u.toURL();
                            } catch (MalformedURLException ignore) {
                                return "";
                            }
                        }
                )
                .toArray(URL[]::new);

        if (includeClasspathJars) {
            final PluginDescriptor pluginDescriptor = (PluginDescriptor) getPluginContext().get("pluginDescriptor");
            final ClassRealm classRealm = pluginDescriptor.getClassRealm();
            compiledTestsDirs = ArrayUtils.addAll(compiledTestsDirs,classRealm.getURLs() );
        }

        for (URL compiledTestsDir : compiledTestsDirs) {
            getLog().info("compiledTestsDir::" + compiledTestsDir);
        }

        return new URLClassLoader(compiledTestsDirs, null);
    }

    @Override
    public String toString() {
        return "LintAssertMojo{" +
                "packageName='" + packageName + '\'' +
                ", includeClasspathJars=" + includeClasspathJars +
                ", verbose=" + verbose +
                '}';
    }
}