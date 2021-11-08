plugins {
    groovy
    `java-gradle-plugin`
    id("com.gradle.plugin-publish") version "0.17.0"
}

version = "2.0.2-SNAPSHOT"
group = "com.enonic.gradle"

gradlePlugin {
    plugins {
        register("defaults_plugin") {
            id = "com.enonic.defaults"
            implementationClass = "com.enonic.defaults.DefaultsPlugin"
        }
    }
}

pluginBundle {
    website = "https://enonic.com"
    vcsUrl = "https://github.com/enonic/gradle-defaults"
    (plugins) {
        "defaults_plugin" {
            displayName = "Enonic Defaults Plugin"
            description = "Defaults plugin for Enonic projects."
            tags = listOf("enonic", "java", "groovy")
        }
    }
}
