package org.lint.azzert.util;

import java.net.URL;
import java.util.List;

import org.junit.jupiter.api.Test;

import junit.framework.Assert;

public class TestClassFinderTest {
	
	@Test
	public void test() {
		List<URL> classes = new TestClassFinder().getClasses();
		classes.forEach(c -> System.out.println(c));
		
		long count = classes.stream().filter(url -> 
						url.toString().contains("/org/lint/azzert/util/TestClassFinderTest.class")
						&& url.toString().startsWith("file:")).count();
		Assert.assertEquals(1,  count);
	}

}
