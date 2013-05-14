package com.sodoku;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(AllTests.suite());
	}

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for com.sodoku");
		//$JUnit-BEGIN$
		suite.addTestSuite(SodokuTest.class);
		//$JUnit-END$
		return suite;
	}

}
