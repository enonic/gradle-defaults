# Enonic Gradle Defaults

This plugin provides some default settings for Enonic gradle projects. To use it, specify the following:

```gradle
plugins {
    id 'com.enonic.defaults' version '1.1.0'
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

## Publishing docs to S3

Docs can also be published to S3 if the `com.enonic.xp.doc` plugin is added. To configure S3 settings
use the global `gradle.properties` file that is under `.gradle/gradle.properties` in your user home 
directory. Set the following:

```
s3AccessKey = <your S3 access key>
s3SecretKey = <your S3 secret key>
```

