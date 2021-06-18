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
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Goal which loads a dotenv (.env) file into the environment variables for the rest of the maven phase which the plugin is defined for
 */
@Mojo(name = "loadenv", defaultPhase = LifecyclePhase.TEST)
public class MyMojo extends AbstractMojo {
    /**
     * Directory of the env file.
     * TODO: redo default directory to look in root of directory where pom.xml exists.
     * ${project.basedir} should work but is evaluating to empty
     */
    @Parameter(property = "envFileDirectory", required = true)
    private String envFileDirectory;

    /**
     * Name of the env file, including the extension if it has one
     *
     */
    @Parameter(defaultValue = ".env", property = "envFileName", required = true)
    private String envFileName;

    public void execute() throws MojoExecutionException, MojoFailureException {
        if (envFileDirectory == null || envFileDirectory.isEmpty()) {
            throw new MojoFailureException("env file directory was empty");
        }

        if (envFileName == null || envFileName.isEmpty()) {
            throw new MojoFailureException("env file name was empty");
        }

        String tempEnvFileDirectory =  evaluatePath(envFileDirectory);
        getLog().info("Loading env file from '" + makePathDirectory(tempEnvFileDirectory) + envFileName + "'");

        try {
            Dotenv dotenv = Dotenv.configure()
                    .directory(tempEnvFileDirectory)
                    .filename(envFileName)
                    .systemProperties()
                    .load();
        } catch (DotenvException dee) {
            throw new MojoExecutionException("Error while loading env file", dee);
        }
    }

    private String evaluatePath(String path) {
        return FileSystems.getDefault().getPath(path).normalize().toAbsolutePath().toString();
    }

    public static String makePathDirectory(String path) {
        if (Files.isDirectory(Paths.get(path))) {
            return path;
        }
        return path + File.separator;
    }
}
