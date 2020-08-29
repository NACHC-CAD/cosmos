package org.nachc.cad.cosmos;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.nachc.cad.cosmos.util.databricks.DeleteTestFilesIntegrationTestSetup;

import com.googlecode.junittoolbox.SuiteClasses;
import com.googlecode.junittoolbox.WildcardPatternSuite;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(WildcardPatternSuite.class)
@SuiteClasses({ "**/*Test.class" })
public class RunAllIntegrationTests {

	@BeforeClass
	public static void setup() {
		log.info("***********************************************************");
		log.info("Starting set up");
		new DeleteTestFilesIntegrationTestSetup().shouldDeleteTestFilesDir();
		log.info("Done with set up");
		log.info("***********************************************************");
	}
	
}
