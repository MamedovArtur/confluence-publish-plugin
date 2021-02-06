package com.github.mamedovartur.confluencegradleplugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class ConfluencePublishPlugin implements Plugin<Project> {
    private Project project;

    @Override
    public void apply(Project project) {
        this.project = project;

        initTask();
    }

    public void initTask(){
        project.getTasks().create("publishToConfluence",ConfluencePublishPluginTask.class);
    }
}
