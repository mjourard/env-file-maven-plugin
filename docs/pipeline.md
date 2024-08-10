# CICD Pipeline Setup Information

Docs for setting up the CICD pipeline so that if I ever need to replicate this repo, I'll know what's needed

## Repository Variables

These variables should be setup as accessible in all environments for the entire repository.

| Name | optional/required | Possible Values | Description | 
| ---- | ----------------- | --------------- | ----------- |

## Repository Secrets

These are repository secrets that should be setup to be accessible in all environments

## Environment Secrets

These are repository secrets that should only be available in certain environments, or should be different in different environments

| Name | optional/required | Possible Values | Description | 
| ---- | ----------------- | --------------- | ----------- |
| SONATYPE_USERNAME | required | .* | Allows authentication and publishing to the sonatype repository. Should be the JIRA username used to create the repo in https://issues.sonatype.org/ |
| SONATYPE_PASSWORD | required | .* | See `SONATYPE_USERNAME`. Should be the JIRA password. NOTE: For your local settings.xml file, you'll need to escape the following characters according to standard XML escape rules: quote ("), apostrophe ('), less-than (<)\, greather than (>) and ampersand (&) according to https://stackoverflow.com/questions/1091945/what-characters-do-i-need-to-escape-in-xml-documents. The github action for creating the settings.xml file will do this for you, and so encoding the special characters before saving them into the Github environment is not necessary. |
| GPG_SECRET | required | .* | The secret used to sign the GPG key used for signing releases to Maven central. |