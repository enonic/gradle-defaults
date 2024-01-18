# Enonic Gradle Defaults

This plugin provides some default deployment settings for Enonic gradle projects: 
- Sets up correct private and public repositories for publishing.
- Streamlines authentication for publishing.
- Verifies that the supported project license is used.
- Configures correct metadata for publishing into Maven Central.

It is intended mostly for internal use by Enonic, but also can be used by external projects to simplify publishing to Enonic's maven repository.

To use it, specify the following:

```gradle
plugins {
    id 'com.enonic.defaults' version '2.1.1'
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

## Testing

When updating this plugin, it is important to test, not only building but also deploying of dependent projects, to make sure the changes
don't break anything. [^1]

[^1]: Version 2.1.1 caused major problems because of this specific issue.  Several projects were automatically updated by Dependabot,
because the build didn't fail, but the projects could not be deployed.

## Releasing

To release a new version of the plugin, change the version in `build.gradle.kts` (for instance `version = "2.1.3"`,
tag the commit with the version number (for instance `git tag v2.1.3`) and push to GitHub (`git push --follow-tags`).

After the release is done, update the version in `build.gradle.kts` to the next snapshot version (for instance `version = "2.1.4-SNAPSHOT"`).


