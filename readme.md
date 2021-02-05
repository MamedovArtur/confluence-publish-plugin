Gradle plugin for create pages in Confluence from asciidoctor generated html

plugin configuration

```groovy
publishToConfluence{
    confluenceUri "http://confluence.local"//confluence base uri
    confluenceUser "admin"//confluence user name
    confluencePass "admin"//confluence user password
    confluencePageId 123456 //parent page id (optional)
    inputHtmlFile = "build/docs/index.html" //path to generated by asciidoctor html
    confluencePageTitle "Example Page Prefix" //prefix of the pages names
    spaceKey "docs" // space for publishing
}
```

to run plugin exec task
`publishToConfluence` in `confluence` group


example build.gradle
```groovy
plugins {
    id 'java'
    id 'arturmamedov.plugins.confluence-publish-plugin' version '0.0.2'
}


group 'org.example'
version '1.0-SNAPSHOT'

repositories {
   
    mavenCentral()
}

dependencies {
    
}

publishToConfluence{
    confluenceUri "http://confluence.local"//confluence base uri
    confluenceUser "admin"//confluence user name
    confluencePass "admin"//confluence user password
    confluencePageId 123456 //parent page id (optional)
    inputHtmlFile = "build/docs/index.html" //path to generated by asciidoctor html
    confluencePageTitle "Example Page Prefix" //prefix of the pages names
    spaceKey "docs" // space for publishing
}
```


if you have a problem with dependencies of plugin try to add pluginManagement into settings.gradle
```groovy
pluginManagement {
    repositories {
        mavenCentral()
    }
}
```