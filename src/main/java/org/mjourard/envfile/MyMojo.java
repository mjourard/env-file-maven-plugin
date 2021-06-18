package org.mjourard.envfile;


import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Goal which touches a timestamp file.
 */
@Mojo(name = "touch", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class MyMojo extends AbstractMojo {
    /**
     * Location of the file.
     */
    @Parameter(defaultValue = "${project.build.directory}", property = "outputDir", required = true)
    private File outputDirectory;

    /**
     * Directory of the env file.
     * TODO: redo default directory to look in root of directory where pom.xml exists.
     * ${project.basedir} should work but is evaluating to empty
     */
    @Parameter(defaultValue = "${envFileDirectory}", property = "envFileDirectory", required = true)
    private String envFileDirectory;

    /**
     * Name of the env file.
     */
    @Parameter(defaultValue = ".env", property = "name", required = true)
    private String envFileName;

    public void execute() throws MojoExecutionException, MojoFailureException {
        if (envFileDirectory == null || envFileDirectory.isEmpty()) {
            throw new MojoFailureException("env file directory (directory) was empty");
        }

        if (envFileName == null || envFileName.isEmpty()) {
            throw new MojoFailureException("env file name (name) was empty");
        }

        getLog().info("Loading env file from '" + envFileDirectory + envFileName + "'");

        try {
            Dotenv dotenv = Dotenv.configure()
                    .directory(envFileDirectory)
                    .filename(envFileName)
                    .load();
        } catch (DotenvException dee) {
            throw new MojoExecutionException("Error while loading env file", dee);
        }
    }
}
