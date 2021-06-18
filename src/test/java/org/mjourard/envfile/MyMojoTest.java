package org.mjourard.envfile;


import org.apache.maven.plugin.testing.MojoRule;
import org.apache.maven.plugin.testing.WithoutMojo;

import org.junit.Rule;
import static org.junit.Assert.*;
import org.junit.Test;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MyMojoTest
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
    public void testSomething()
            throws Exception
    {
        File pom = new File( "target/test-classes/project-to-test/" );
        assertNotNull( pom );
        assertTrue( pom.exists() );

        MyMojo myMojo = ( MyMojo ) rule.lookupConfiguredMojo( pom, "loadenv" );
        assertNotNull( myMojo );
        myMojo.execute();

        Map<String, String> expectedVars = new HashMap<String, String>();
        expectedVars.put("WEBSITE_URL", "https://mjourard.github.io");
        expectedVars.put("USERNAME", "francis");
        expectedVars.put("PASSWORD", "falconfliesfreely");

        for (String key : expectedVars.keySet()) {
            String systemVal = System.getProperty(key);
            assertNotNull(systemVal);
            assertEquals(systemVal, expectedVars.get(key));
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

