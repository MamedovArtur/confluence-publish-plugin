package arturmamedov.plugins.confluencegradleplugin.services;

import arturmamedov.plugins.confluencegradleplugin.dto.ConfluenceGetExistResponse;
import arturmamedov.plugins.confluencegradleplugin.dto.ConfluenceRequest;
import arturmamedov.plugins.confluencegradleplugin.dto.CreatePageResponse;
import lombok.RequiredArgsConstructor;


import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;


@RequiredArgsConstructor
public class ConfluenceService {

    private final ConfluenceHttpClient confluenceHttpClient;

    public CreatePageResponse createPage(ConfluenceRequest confluenceRequest){
        return confluenceHttpClient.confluenceRequest(
                "/rest/api/content",
                "POST",
                confluenceRequest,
                null,
                CreatePageResponse.class
        );
    }

    public CreatePageResponse updatePage(ConfluenceRequest confluenceRequest){
        return confluenceHttpClient.confluenceRequest(
                "/rest/api/content/"+confluenceRequest.getId(),
                "PUT",
                confluenceRequest,
                null,
                CreatePageResponse.class
        );
    }


    public ConfluenceGetExistResponse findPages(String spaceKey, String title) {
        return confluenceHttpClient.confluenceRequest(
                "/rest/api/content",
                "GET",
                null,
                Map.of(
                        "spaceKey",spaceKey,
                        "title",title,
                        "expand","body.storage,version"
                ),
                ConfluenceGetExistResponse.class
        );

    }


}
