package com.enonic.defaults

import org.gradle.api.Project

class DefaultsExtension
{
    private final static KEY = 'enonic'

    def publishRepo = 'public'

    def publishUrl = 'https://repo.enonic.com'

    DefaultsExtension( final Project project )
    {
        def repoKey = project.findProperty( 'repoKey' )
        if ( repoKey != null )
        {
            this.publishRepo = repoKey
        }
    }

    static DefaultsExtension create( final Project project )
    {
        return project.extensions.create( KEY, DefaultsExtension, project )
    }
}
