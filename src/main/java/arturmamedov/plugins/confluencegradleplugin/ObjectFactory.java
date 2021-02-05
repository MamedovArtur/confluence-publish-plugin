package arturmamedov.plugins.confluencegradleplugin;

import arturmamedov.plugins.confluencegradleplugin.models.BaseCredentials;
import arturmamedov.plugins.confluencegradleplugin.services.AsciiDoctorHtmlPreparer;
import arturmamedov.plugins.confluencegradleplugin.services.ConfluenceHttpClient;
import arturmamedov.plugins.confluencegradleplugin.services.ConfluencePublishService;
import arturmamedov.plugins.confluencegradleplugin.services.ConfluenceService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpClient;

public class ObjectFactory {
    private final static ObjectFactory instance = new ObjectFactory();
    public static ObjectFactory getInstance(){
        return instance;
    }

    private BaseCredentials buildCredential(ConfluencePublishPluginTask task) {
        return new BaseCredentials(task.getConfluenceUser(),task.getConfluencePass());
    }

    public ConfluenceHttpClient buildConfluenceHttpClient(ConfluencePublishPluginTask task){
        return new ConfluenceHttpClient(
                HttpClient.newBuilder().build(),
                new ObjectMapper(),
                task.getConfluenceUri(),
                buildCredential(task).toString()
        );
    }

    public ConfluenceService buildConfluenceService(ConfluencePublishPluginTask task){
        return new ConfluenceService(buildConfluenceHttpClient(task));
    }

    public ConfluencePublishService buildConfluencePublishService(ConfluencePublishPluginTask task){
        return new ConfluencePublishService(task.getSpaceKey(),buildConfluenceService(task));
    }

    public AsciiDoctorHtmlPreparer createAsciiDoctorHtmlPreparer(){
        return new AsciiDoctorHtmlPreparer();
    }
}
