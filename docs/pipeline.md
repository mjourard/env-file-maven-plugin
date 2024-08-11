# CICD Pipeline Setup Information

Docs for setting up the CICD pipeline so that if I ever need to replicate this repo, I'll know what's needed.

This repo follows the gitflow model of release, such that new releases are done from a release/hotfix branch, and then after successful publication, the release branch is merged into the main branch and then tagged.

The main branch is then merged back into develop for additional work to be done.

## Github App Setup

This repo expects there to be a github app setup for access to a github actions token. The Github app will require the following permissions:

| Grouping | Name | Access Level | Reasoning |
| -------- | ---- | ------------ | --------- |
| Repository Permissions | Actions | Read and write | Needed for calling actions on other repos within the org |
| Repository Permissions | Contents | Read and write | Needing for creating tags, commits and branches |
| Repository Permissions | Issues | Read only | **Not actually sure if this is needed** |
| Repository Permissions | Metadata | Read only | Required, so leave this |
| Repository Permissions | Pull requests | Read and write | Required to create pull requests for core team to merge the release branches into main |

## Repository Variables

These variables should be setup as accessible in all environments for the entire repository.

| Name | optional/required | Possible Values | Description | 
| ---- | ----------------- | --------------- | ----------- |
| GH_APP_ID | required | `\d{5}` | The app id of the github application that is installed on this repo. Get this from the github org admin. |

## Repository Secrets

These are repository secrets that should be setup to be accessible in all environments

| Name | optional/required | Possible Values | Description | 
| ---- | ----------------- | --------------- | ----------- |
| GH_APP_SECRET_KEY | required | `-----BEGIN (RSA|DSA|EC|OPENSSH|PGP) PRIVATE KEY-----[\s\S]+?-----END (RSA|DSA|EC|OPENSSH|PGP) PRIVATE KEY-----` | The private key that was given when the app was initially created. It'll be the same private key on every repo. |


## Environment Secrets

These are repository secrets that should only be available in certain environments, or should be different in different environments

| Name | optional/required | Possible Values | Description | 
| ---- | ----------------- | --------------- | ----------- |
| SONATYPE_USERNAME | required | .* | Allows authentication and publishing to the sonatype repository. Should be the JIRA username used to create the repo in https://issues.sonatype.org/ |
| SONATYPE_PASSWORD | required | .* | See `SONATYPE_USERNAME`. Should be the JIRA password. NOTE: For your local settings.xml file, you'll need to escape the following characters according to standard XML escape rules: quote ("), apostrophe ('), less-than (<)\, greather than (>) and ampersand (&) according to https://stackoverflow.com/questions/1091945/what-characters-do-i-need-to-escape-in-xml-documents. The github action for creating the settings.xml file will do this for you, and so encoding the special characters before saving them into the Github environment is not necessary. |
| GPG_SECRET | required | .* | The secret used to sign the GPG key used for signing releases to Maven central. |