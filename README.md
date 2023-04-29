# env-file-maven-plugin ![Build Status](https://github.com/cloudsimplus/cloudsimplus/actions/workflows/build.yml/badge.svg) ![Maven Central](https://img.shields.io/maven-central/v/io.github.mjourard/env-file-maven-plugin.svg)

A maven plugin that loads in a dotenv (.env) file during the build process.

For those that liked the ruby package and wanted it for their maven build process. 

This plugin inspires cursed development practices. Talk to a senior before you use this in something that signs your paychecks.

Documentation, download, and usage instructions
===============================================

Full usage details and more are available on the
**[project documentation website](https://mjourard.github.io/env-file-maven-plugin/index.html)**.

Development
===========

Publishing Documentation
========================

Run `mvn site` when you have an OAuth2 Token configured as a server in ~/.m2/settings.xml

Publishing a new version
========================

Prerequisites:
* the git repo is clean
* in your `~/.m2/settings.xml` file, you've got the following sections:
    * ```
      <servers>
          <server>
              <id>ossrh</id>
              <username>_OSSRH_ACCESS_TOKEN</username>
              <password>_OSSRH_ACCESS_TOKEN_PASSWORD</password>
          </server>
      </servers>
      <profiles>
          <profile>
              <id>ossrh</id>
              <activation>
                  <activeByDefault>true</activeByDefault>
              </activation>
              <properties>
                  <gpg.passphrase>_passphrase_for_gpg_key</gpg.passphrase>
              </properties>
          </profile>
      </profiles>
      ```
      
Ensure the git repo is clean, then run the following:
```
mvn clean
mvn release:prepare
mvn release:perform -P release-sign-artifacts
```


