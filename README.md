# Enonic Gradle Defaults

This plugin provides some default settings for Enonic gradle projects. To use it, specify the following:

```gradle
buildscript {
    repositories {
        jcenter()
        maven {
            url 'https://repo.enonic.com/public'
        }
    }

    dependencies {
        classpath 'com.enonic.gradle:gradle-defaults:1.0.1'
    }
}

apply plugin: 'com.enonic.defaults'
```

## Publishing to Repository

This plugin sets up default values for publishing to our repository at https://repo.enonic.com. To
set up publishing, first add the `maven` plugin.

```
apply plugin: 'maven'
```

Then publish using `uploadArchives` task. This will default publish to `public` repo. To override this, 
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

