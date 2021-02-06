package com.github.mamedovartur.confluencegradleplugin;

import com.github.mamedovartur.confluencegradleplugin.services.AsciiDoctorHtmlPreparer;
import com.github.mamedovartur.confluencegradleplugin.services.ConfluencePublishService;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;
import java.io.IOException;


public class ConfluencePublishPluginTask extends DefaultTask {

    private String confluenceUri;
    private String confluencePass;
    private String confluenceUser;
    private String confluencePageTitle;
    private Long confluencePageId;
    private String spaceKey;
    private String inputHtmlFile;

    public ConfluencePublishPluginTask() {
        super.setGroup("confluence");
    }


    @TaskAction
    public void execute() {
        AsciiDoctorHtmlPreparer preparer = ObjectFactory.getInstance().createAsciiDoctorHtmlPreparer();
        ConfluencePublishService publisher = ObjectFactory.getInstance().buildConfluencePublishService(this);
        try {
            preparer.prepareHtml(inputHtmlFile,confluencePageTitle+": ").forEach(page->{
                System.out.println("publish: "+page.getTitle());
                publisher.push(page.getTitle(),page.getBody(),confluencePageId);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Input
    public String getConfluenceUri() {
        return confluenceUri;
    }

    public void setConfluenceUri(String confluenceUri) {
        this.confluenceUri = confluenceUri;
    }

    @Input
    public String getConfluencePass() {
        return confluencePass;
    }

    public void setConfluencePass(String confluencePass) {
        this.confluencePass = confluencePass;
    }

    @Input
    @Optional
    public Long getConfluencePageId() {
        return confluencePageId;
    }

    public void setConfluencePageId(Long confluencePageId) {
        this.confluencePageId = confluencePageId;
    }

    @Input
    public String getInputHtmlFile() {
        return inputHtmlFile;
    }

    public void setInputHtmlFile(String inputHtmlFile) {
        this.inputHtmlFile = inputHtmlFile;
    }

    @Input
    public String getSpaceKey() {
        return spaceKey;
    }

    public void setSpaceKey(String spaceKey) {
        this.spaceKey = spaceKey;
    }

    @Input
    public String getConfluencePageTitle() {
        return confluencePageTitle;
    }

    public void setConfluencePageTitle(String confluencePageTitle) {
        this.confluencePageTitle = confluencePageTitle;
    }

    @Input
    public String getConfluenceUser() {
        return confluenceUser;
    }

    public void setConfluenceUser(String confluenceUser) {
        this.confluenceUser = confluenceUser;
    }
}


