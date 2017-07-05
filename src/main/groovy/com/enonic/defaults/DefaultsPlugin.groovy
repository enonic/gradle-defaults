package com.enonic.defaults

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.MavenPlugin

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
        def mavenPlugin = this.project.plugins.hasPlugin( MavenPlugin )
        if ( !mavenPlugin )
        {
            return
        }

        this.project.with {
            uploadArchives {
                repositories {
                    mavenDeployer {
                        repository( url: "${this.ext.publishUrl}/${this.ext.publishRepo}" ) {
                            authentication( userName: this.project.findProperty( 'repoUser' ),
                                            password: this.project.findProperty( 'repoPassword' ) )
                        }
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
