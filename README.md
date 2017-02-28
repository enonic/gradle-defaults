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

The plugin can also be configured using `enonic`-closure:

```gradle
enonic {

    // Repository to publish to. Default to 'public'. Can also be set using repoKey property.
    publishRepo = 'public' 
    
    // Repository URL. Default to 'https://repo.enonic.com'.
    publishUrl = 'https://repo.enonic.com'
}
```
