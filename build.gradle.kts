plugins {
    groovy
    `java-gradle-plugin`
    id("com.gradle.plugin-publish") version "1.0.0"
}

version = "2.1.3-SNAPSHOT"
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
