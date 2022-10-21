# Enonic Gradle Defaults

This plugin provides some default settings for Enonic gradle projects. To use it, specify the following:

```gradle
plugins {
    id 'com.enonic.defaults' version '2.0.1'
}
```

## Publishing to Repository

This plugin sets up default values for publishing to our repository at https://repo.enonic.com. To
set up publishing, first add the `maven-publish` plugin.

```
apply plugin: 'maven-publish'
```

Then publish using `publish` task. This will default publish to `public` repo. To override this, 
you can add this to your local `gradle.properties` file:

```
repoKey = inhouse
```

The publishing task also needs some credentials. This can be set up in your global `gradle.properties`
file that is under `.gradle/gradle.properties` in your user home directory. Set the following:

```
repoUser = <your user>
repoPassword = <your repo 'API key'>
```

The password should not be your password that you use to log into artifactory with. Please generate
a token under your profile and use that.

## Configuration

Starting from version 2.1.0 you can customize the `description` and `name` tags in the pom.xml file:

## gradle.properties

```properties
description=<This value will be used in the `description` tag in the pom.xml file>
publicName=<This value will be used in the `name` tag in the pom.xml file. Optional. Default `value project.name`>
```