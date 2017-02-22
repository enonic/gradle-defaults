package com.enonic.defaults

import org.gradle.api.Project

class DefaultsExtension
{
    private final static KEY = 'enonic';

    def publishRepo = 'public';

    def publishUrl = 'https://repo.enonic.com';

    def publishBuildInfo = false;

    def publishArtifacts = true;

    def String[] publishConfigs = ['archives'];

    def String[] publishPublications = ['mavenJava'];

    static DefaultsExtension get( final Project project )
    {
        return project.extensions.getByType( DefaultsExtension );
    }

    static DefaultsExtension create( final Project project )
    {
        return project.extensions.create( KEY, DefaultsExtension );
    }
}
