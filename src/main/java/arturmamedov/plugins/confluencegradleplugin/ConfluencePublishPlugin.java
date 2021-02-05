package arturmamedov.plugins.confluencegradleplugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;

public class ConfluencePublishPlugin implements Plugin<Project> {
    private Project project;

    @Override
    public void apply(Project project) {
        this.project = project;

        initTask();
    }

    @TaskAction
    public void initTask(){
        project.getTasks().create("publishToConfluence",ConfluencePublishPluginTask.class);
    }
}
