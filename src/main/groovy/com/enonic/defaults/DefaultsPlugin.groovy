package com.enonic.defaults

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin
import org.gradle.api.publish.plugins.PublishingPlugin

import java.util.regex.Matcher
import java.util.regex.Pattern

class DefaultsPlugin
    implements Plugin<Project>
{
    private DefaultsExtension ext

    private Project project

    void apply( final Project project )
    {
        this.project = project
        this.ext = DefaultsExtension.create( this.project )

        this.project.afterEvaluate( new Action<Project>() {
            @Override
            void execute( final Project pr )
            {
                configurePublishing()
            }
        } )
    }

    private String resolveRepositoryName()
    {
        def gitConfigFile = this.project.file( ".git/config" )

        if ( gitConfigFile.exists() )
        {
            Pattern pattern = Pattern.compile( "git@github.com:enonic/(.*).git" )
            Matcher matcher = pattern.matcher( gitConfigFile.text )
            if ( matcher.find() )
            {
                return "${matcher.group( 1 )}"
            }
        }

        return this.project.rootDir.name
    }

    private String[] ensureThatLicenseExistsAndGetMetadata()
    {
        def licenseFileName = 'LICENSE.txt'
        def license = this.project.file( licenseFileName )

        if ( !license.exists() )
        {
            licenseFileName = 'LICENSE'
            license = this.project.file( licenseFileName )
            if ( !license.exists() )
            {
                throw new IllegalStateException( "LICENSE.txt or LICENSE file not found" )
            }
        }

        def licenceText = license.text

        if ( licenceText.contains( 'GNU GENERAL PUBLIC LICENSE' ) && licenceText.contains( 'Version 3, 29 June 2007' ) )
        {
            return ["${licenseFileName}", "GNU General Public License, Version 3.0"]
        }
        if ( licenceText.contains( 'Apache License' ) && licenceText.contains( 'Version 2.0, January 2004' ) )
        {
            return ["${licenseFileName}", "The Apache Software License, Version 2.0"]
        }
        if ( licenceText.contains( 'Enonic License, version 1.0' ) )
        {
            return ["${licenseFileName}", "Enonic License, version 1.0"]
        }

        throw new IllegalStateException( "Unsupported License. LICENSE.txt Must be either GNU, Apache or Enonic License" )
    }

    private void configurePublishing()
    {
        def hasPublishPlugins = this.project.plugins.hasPlugin( PublishingPlugin ) && this.project.plugins.hasPlugin( MavenPublishPlugin )
        def hasJavaPlugin = this.project.plugins.hasPlugin( JavaPlugin )

        if ( !hasPublishPlugins )
        {
            return
        }

        if ( hasJavaPlugin )
        {
            this.project.with {
                def projectNameProp = this.project.findProperty( 'projectName' )
                if ( projectNameProp != null && projectNameProp.startsWith( 'lib-' ) )
                {
                    java {
                        withSourcesJar()
                        withJavadocJar()
                    }
                }

                publishing {
                    publications {
                        mavenJava( MavenPublication ) {
                            from components.java

                            if ( !"inhouse".equals( this.ext.publishRepo ) )
                            {
                                String repositoryName = resolveRepositoryName()
                                String[] licenseTuple = ensureThatLicenseExistsAndGetMetadata()

                                pom {
                                    name = this.project.findProperty( 'publicName' ) ?: this.project.name
                                    description = this.project.findProperty( 'description' ) ?: this.project.name
                                    url = "https://github.com/enonic/${repositoryName}"
                                    licenses {
                                        license {
                                            name = licenseTuple[1]
                                            url = "https://github.com/enonic/${repositoryName}/blob/master/${licenseTuple[0]}"
                                        }
                                    }
                                    developers {
                                        developer {
                                            id = 'developers'
                                            name = 'Enonic developers'
                                            email = 'developers@enonic.com'
                                        }
                                    }
                                    scm {
                                        connection = "scm:git:git@github.com:enonic/${repositoryName}.git"
                                        developerConnection = "scm:git:git@github.com:enonic/${repositoryName}.git"
                                        url = "https://github.com/enonic/${repositoryName}"
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        this.project.with {
            publishing {
                repositories {
                    maven {
                        credentials {
                            username this.project.findProperty( 'repoUser' )
                            password this.project.findProperty( 'repoPassword' )
                        }
                        name "public"
                        url "${this.ext.publishUrl}/${this.ext.publishRepo}"
                    }
                }
            }
        }
    }
}
