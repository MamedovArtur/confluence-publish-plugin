package com.github.mamedovartur.confluencegradleplugin.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class Version {
    Integer number;

    @JsonIgnore
    public Version getNext() {
        return new Version(number+1);
    }
}
