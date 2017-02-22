package com.enonic.defaults

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.MavenPlugin
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin
import org.jfrog.gradle.plugin.artifactory.ArtifactoryPlugin

class DefaultsPlugin
    implements Plugin<Project>
{
    private DefaultsExtension ext;

    private Project project;

    void apply( final Project project )
    {
        this.project = project;
        this.ext = DefaultsExtension.create( this.project );

        this.project.afterEvaluate( new Action<Project>() {
            @Override
            void execute( final Project pr )
            {
                configurePublishing();
            }
        } );
    }

    private void configurePublishing()
    {
        def mavenPlugin = this.project.plugins.hasPlugin( MavenPlugin );
        def publishPlugin = this.project.plugins.hasPlugin( MavenPublishPlugin );

        if ( !( mavenPlugin || publishPlugin ) )
        {
            return;
        }

        this.project.plugins.apply( ArtifactoryPlugin )

        this.project.with {
            artifactory {
                contextUrl = this.ext.publishUrl
                publish {
                    repository {
                        repoKey = this.ext.publishRepo
                        username = this.project.findProperty( 'repoUser' )
                        password = this.project.findProperty( 'repoPassword' )
                    }
                    defaults {
                        publishBuildInfo = this.ext.publishBuildInfo
                        publishArtifacts = this.ext.publishArtifacts

                        if ( mavenPlugin )
                        {
                            publishConfigs( this.ext.publishConfigs )
                        }

                        if ( publishPlugin )
                        {
                            publications( this.ext.publishPublications )
                        }
                    }
                }
            }
        }
    }
}
