plugins {
    groovy
    `java-gradle-plugin`
    id("com.gradle.plugin-publish") version "1.2.0"
}

version = "2.1.3-SNAPSHOT"
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
            implementationClass = "com.enonic.defaults.DefaultsPlugin"
        }
    }
}
