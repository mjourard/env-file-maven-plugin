package io.github.mjourard.envfile;


import org.apache.maven.plugin.testing.MojoRule;
import org.apache.maven.plugin.testing.WithoutMojo;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class LoadEnvMojoTest
{
    @Rule
    public MojoRule rule = new MojoRule()
    {
        @Override
        protected void before() throws Throwable
        {
        }

        @Override
        protected void after()
        {
        }
    };

    /**
     * @throws Exception if any
     */
    @Test
    public void testTypicalUsage()
            throws Exception
    {
        File pom = new File( "target/test-classes/project-to-test/" );
        assertNotNull( pom );
        assertTrue( pom.exists() );

        LoadEnvMojo loadEnvMojo = (LoadEnvMojo) rule.lookupConfiguredMojo( pom, "loadenv" );
        assertNotNull(loadEnvMojo);
        loadEnvMojo.execute();

        Map<String, String> expectedVars = new HashMap<String, String>();
        expectedVars.put("WEBSITE_URL", "https://mjourard.github.io");
        expectedVars.put("TEMP_USERNAME", "francis");
        expectedVars.put("PASSWORD", "falconfliesfreely");

        checkExpectedEnvVars(expectedVars);
    }

    /**
     * @throws Exception if any
     */
    @Test
    public void testDefaultUsage()
            throws Exception
    {
        File pom = new File( "target/test-classes/project-using-defaults/" );
        assertNotNull( pom );
        assertTrue( pom.exists() );

        LoadEnvMojo loadEnvMojo = (LoadEnvMojo) rule.lookupConfiguredMojo( pom, "loadenv" );
        assertNotNull(loadEnvMojo);
        loadEnvMojo.execute();

        Map<String, String> expectedVars = new HashMap<String, String>();
        expectedVars.put("WEBSITE_URL", "https://mjourard.github.io");
        expectedVars.put("TEMP_USERNAME", "francis");
        expectedVars.put("PASSWORD", "falconfliesfreely");

        checkExpectedEnvVars(expectedVars);
    }

    public void checkExpectedEnvVars(Map<String, String> expectedVars) {
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
    public void testSomethingWhichDoesNotNeedTheMojoAndProbablyShouldBeExtractedIntoANewClassOfItsOwn()
    {
        assertTrue( true );
    }

}

