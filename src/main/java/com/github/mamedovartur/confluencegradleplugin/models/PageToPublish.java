package com.github.mamedovartur.confluencegradleplugin.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageToPublish {
    private String title;
    private String body;
}
