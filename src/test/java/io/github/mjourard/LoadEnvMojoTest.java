package io.github.mjourard;


import org.apache.maven.plugin.testing.MojoRule;
import org.apache.maven.plugin.testing.WithoutMojo;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadEnvMojoTest {
    @Rule
    public MojoRule rule = new MojoRule() {
        @Override
        protected void before() throws Throwable {
        }

        @Override
        protected void after() {
        }
    };

    /**
     * @throws Exception if any
     */
    @Test
    public void testTypicalUsage() throws Exception {
        File pom = new File("target/test-classes/project-to-test/");
        assertNotNull(pom);
        assertTrue(pom.exists());

        LoadEnvMojo loadEnvMojo = (LoadEnvMojo) rule.lookupConfiguredMojo(pom, "loadenv");
        assertNotNull(loadEnvMojo);
        loadEnvMojo.execute();

        Map<String, String> expectedVars = new HashMap<String, String>();
        expectedVars.put("WEBSITE_URL", "https://mjourard.github.io");
        expectedVars.put("TEMP_USERNAME", "francis");
        expectedVars.put("PASSWORD", "falconfliesfreely");

        checkExpectedEnvVars(expectedVars);
    }

    @Test
    public void testSkipConfiguration() throws Exception {
        File pom = new File("target/test-classes/project-skips-plugin/");
        assertNotNull(pom);
        assertTrue(pom.exists());

        List<String> skippedVars = new ArrayList<String>();
        skippedVars.add("WEBSITE_URL");
        skippedVars.add("TEMP_USERNAME");
        skippedVars.add("PASSWORD");

        for (String key : skippedVars) {
            System.clearProperty(key);
            String systemVal = System.getProperty(key);
            assertNull(systemVal);
        }

        LoadEnvMojo loadEnvMojo = (LoadEnvMojo) rule.lookupConfiguredMojo(pom, "loadenv");
        assertNotNull(loadEnvMojo);
        loadEnvMojo.execute();

        for (String key : skippedVars) {
            String systemVal = System.getProperty(key);
            assertNull(systemVal);
        }
    }

    /**
     * @throws Exception if any
     */
    @Test
    public void testDefaultUsage() throws Exception {
        File pom = new File("target/test-classes/project-using-defaults/");
        assertNotNull(pom);
        assertTrue(pom.exists());

        LoadEnvMojo loadEnvMojo = (LoadEnvMojo) rule.lookupConfiguredMojo(pom, "loadenv");
        assertNotNull(loadEnvMojo);
        loadEnvMojo.execute();

        Map<String, String> expectedVars = new HashMap<String, String>();
        expectedVars.put("WEBSITE_URL", "https://mjourard.github.io");
        expectedVars.put("TEMP_USERNAME", "francis");
        expectedVars.put("PASSWORD", "falconfliesfreely");

        checkExpectedEnvVars(expectedVars);
    }

    public void checkExpectedEnvVars(final Map<String, String> expectedVars) {
        for (String key : expectedVars.keySet()) {
            String systemVal = System.getProperty(key);
            assertNotNull(systemVal);
            assertEquals(expectedVars.get(key), systemVal);
            String envVal = System.getenv(key);
            assertNotNull(envVal);
            assertEquals(expectedVars.get(key), envVal);
        }
    }

    /** Do not need the MojoRule. */
    @WithoutMojo
    @Test
    public void testSomethingWhichDoesNotNeedTheMojoAndProbablyShouldBeExtractedIntoANewClassOfItsOwn() {
        assertTrue(true);
    }

}

