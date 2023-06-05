Changelog
---------

## 2.0
* updated the directory to properly reflect the package name since that was causing issues running tests while also having used the plugin
* added checkstyle formatting of the code itself, as well as some vscode settings for easier work done on it in the future.
* added the bones of a github workflow for eventual publishing of the source code the various distributions and updating the source documentation
* added a <skip> configuration option. When this is set to 'true' in the <configuration> section of the plugin, then it will not run.

## 1.1
* Lowered the required java version from 11 to 8 since no Java 11 features were used in code and 8 is still widely used.

## 1.0
* First stable release
