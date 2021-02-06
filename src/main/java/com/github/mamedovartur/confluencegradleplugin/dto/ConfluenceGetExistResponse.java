package com.github.mamedovartur.confluencegradleplugin.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfluenceGetExistResponse {

    private List<Page> results;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Page {
        Long id;
        Version version;
    }


}
