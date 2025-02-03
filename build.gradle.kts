plugins {
    groovy
    `java-gradle-plugin`
    id("com.gradle.plugin-publish") version "1.3.1"
}

version = "2.1.6-SNAPSHOT"
group = "com.enonic.gradle"

tasks.compileJava {
    options.release.set(11)
}

gradlePlugin {
    website.set("https://enonic.com")
    vcsUrl.set("https://github.com/enonic/gradle-defaults")
    plugins {
        create("defaults_plugin") {
            id = "com.enonic.defaults"
            displayName = "Enonic XP Defaults Plugin"
            description = "Defaults plugin for Enonic projects."
            tags.set(listOf("enonic", "java", "groovy"))
            implementationClass = "com.enonic.defaults.DefaultsPlugin"
        }
    }
}
