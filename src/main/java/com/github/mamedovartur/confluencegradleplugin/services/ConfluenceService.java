package com.github.mamedovartur.confluencegradleplugin.services;

import com.github.mamedovartur.confluencegradleplugin.dto.ConfluenceGetExistResponse;
import com.github.mamedovartur.confluencegradleplugin.dto.ConfluenceRequest;
import com.github.mamedovartur.confluencegradleplugin.dto.CreatePageResponse;
import lombok.RequiredArgsConstructor;


import java.util.Map;


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
