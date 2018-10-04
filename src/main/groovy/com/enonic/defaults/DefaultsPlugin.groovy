package com.enonic.defaults

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin
import org.gradle.api.publish.plugins.PublishingPlugin

class DefaultsPlugin
    implements Plugin<Project>
{
    private DefaultsExtension ext

    private Project project

    void apply( final Project project )
    {
        this.project = project
        this.ext = DefaultsExtension.create( this.project )

        this.project.plugins.withId( 'com.enonic.xp.doc', new Action<Plugin>() {
            @Override
            void execute( final Plugin plugin )
            {
                configureS3Settings()
            }
        } )

        this.project.afterEvaluate( new Action<Project>() {
            @Override
            void execute( final Project pr )
            {
                configurePublishing()
            }
        } )
    }

    private void configurePublishing()
    {
        def hasPublishPlugins = this.project.plugins.hasPlugin( PublishingPlugin ) && this.project.plugins.hasPlugin( MavenPublishPlugin )
        def hasJavaPlugin = this.project.plugins.hasPlugin( JavaPlugin )

        if ( !hasPublishPlugins )
        {
            return
        }

        if ( hasJavaPlugin ) {
            this.project.with {
                publishing {
                    publications {
                        mavenJava( MavenPublication ) {
                            from components.java
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

    private void configureS3Settings()
    {
        this.project.with {
            doc {
                s3 {
                    bucketName = 'enonic-docs'
                    accessKey = this.project.findProperty( 's3AccessKey' )
                    secretKey = this.project.findProperty( 's3SecretKey' )
                }
            }
        }
    }
}
