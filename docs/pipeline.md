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

## Environment Variables

These are environment variables that should only be available in certain environments, or should be different in different environments

| Name | optional/required | Possible Values | Description | 
| ---- | ----------------- | --------------- | ----------- |
| SONATYPE_USERNAME | required | .* | Allows authentication and publishing to the sonatype repository. Should be the access token username when you sign into `https://s01.oss.sonatype.org/#profile;User%20Token` |

## Environment Secrets

These are repository secrets that should only be available in certain environments, or should be different in different environments

To get GPG secret keys after they've been generated, so the following:

```
gpg --list-secret-keys --keyid-format LONG
gpg --export-secret-keys --armor ${KEY_ID}
```

| Name | optional/required | Possible Values | Description | 
| ---- | ----------------- | --------------- | ----------- |
| SONATYPE_PASSWORD | required | .* | See `SONATYPE_USERNAME` from 'Environment Variables'. |
| GPG_PRIVATE_KEY | required | .* | The gpg private key used to releases to Maven central. |
| GPG_SECRET | required | .* | The secret used to sign the GPG key used for signing releases to Maven central. |
