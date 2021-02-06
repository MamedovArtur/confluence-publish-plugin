package com.github.mamedovartur.confluencegradleplugin.services;


import com.github.mamedovartur.confluencegradleplugin.dto.ConfluenceGetExistResponse;
import com.github.mamedovartur.confluencegradleplugin.dto.ConfluenceRequest;
import com.github.mamedovartur.confluencegradleplugin.dto.CreatePageResponse;
import lombok.RequiredArgsConstructor;


import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class ConfluencePublishService {
    private final String spaceKey;
    private final ConfluenceService confluenceService;

    public CreatePageResponse push(String title, String body, Long parent) {
        ConfluenceRequest confluenceRequest = buildPageRequest(title, body, parent);

        return getOld(title).map(page -> {
                    confluenceRequest.setId(page.getId());
                    confluenceRequest.setVersion(page.getVersion().getNext());
                    return confluenceService.updatePage(confluenceRequest);
                })
                .orElseGet(() -> confluenceService.createPage(confluenceRequest));
    }

    private ConfluenceRequest buildPageRequest(String title, String body, Long parent) {
        ConfluenceRequest confluenceRequest = new ConfluenceRequest();
        confluenceRequest.setType("page");

        if (parent != null) {
            confluenceRequest.setAncestors(List.of(ConfluenceRequest.Ancestors.builder()
                    .id(parent)
                    .type("page")
                    .build()
            ));
        }

        confluenceRequest.setSpace(Map.of("key", spaceKey));
        confluenceRequest.setBody(new ConfluenceRequest.Body(
                ConfluenceRequest.Storage.builder()
                        .value(body)
                        .representation("storage")
                        .build()
        ));
        confluenceRequest.setTitle(title);
        return confluenceRequest;
    }


    private Optional<ConfluenceGetExistResponse.Page> getOld(String title) {

        ConfluenceGetExistResponse resp = confluenceService.findPages(spaceKey, title);

        return Optional.ofNullable(resp.getResults()).flatMap(l -> l.stream().max(
                (p1, p2) -> p1.getVersion().getNumber() - p2.getVersion().getNumber())
        );
    }
}
