package com.github.mamedovartur.confluencegradleplugin.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public class ConfluenceHttpClient {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String baseUri;
    private final String credential;

    public <T> T confluenceRequest(
            String uri,
            String method,
            Object request,
            Map<String,String> queryParams,
            Class<T> responseType
    ){
        try {
            uri = buildURI(uri,queryParams);
            HttpRequest httpRequest = buildHttpRequest(uri, method, request);
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response: "+response.body());
            if(responseType == String.class) return (T)response.body();
            else return objectMapper.readValue(response.body(),responseType);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Ошибка при запросе на confluence "+e.getMessage(),e);
        }
    }

    private String buildURI(String uri, Map<String, String> queryParams) {

        String query = Optional.ofNullable(queryParams)
                .map(Map::entrySet).stream()
                .flatMap(Collection::stream)
                .map(e->String.format("%s=%s",e.getKey(), URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8)))
                .collect(Collectors.joining("&"));

        return query.isBlank() ? uri : uri+"?"+query;

    }

    private HttpRequest buildHttpRequest(String uri, String method, Object request) throws JsonProcessingException {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(baseUri + uri))
                .header("Authorization", "Basic " + credential);
        System.out.println(method+" "+baseUri + uri);
        if(!method.equals("GET")){
            String requestBody = objectMapper.writeValueAsString(request);
            System.out.println("Request: "+requestBody);
            requestBuilder.headers("Content-type","application/json")
                    .method(method, HttpRequest.BodyPublishers.ofString(requestBody));
        }else{
            requestBuilder.GET();
        }
        return requestBuilder.build();
    }
}
