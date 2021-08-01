package com.example.appengine.springboot;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PokemonResult {

    @JsonProperty("results")
    List<PokemonEntry> results;
}

class PokemonEntry {
    @JsonProperty("name")
    String name;

    @JsonProperty("url")
    String url;
}
