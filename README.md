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
        classpath 'com.enonic.gradle:gradle-defaults:1.0.0'
    }
}

apply plugin: 'com.enonic.defaults'
```

The plugin can also be configured using `enonic`-closure:

```gradle
enonic {

    // Repository to publish to. Default to 'public'.
    publishRepo = 'public' 
    
    // Repository URL. Default to 'https://repo.enonic.com'.
    publishUrl = 'https://repo.enonic.com'

    // Publish build info. Default to false.
    publishBuildInfo = false

    // Publish artifacts. Default to true.
    publishArtifacts = true

    // A list of configs to publish. Defaults to 'archives'.
    publishConfigs = ['archives']

    // A list of publications to publish. Defaults to 'mavenJava'.
    publishPublications = ['mavenJava']
}
```