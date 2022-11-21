## Testing

When updating this plugin, it is important to test, not only building but also deploying of dependent projects, to make sure the changes
don't break anything. [^1]

[^1]: Version 2.1.1 caused major problems because of this specific issue.  Several projects were automatically updated by Dependabot,
because the build didn't fail, but the projects could not be deployed.

