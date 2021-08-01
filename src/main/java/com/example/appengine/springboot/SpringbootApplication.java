/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.appengine.springboot;

// [START gae_java11_helloworld]
import com.example.appengine.springboot.models.Pokemon;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
public class SpringbootApplication {

  @Autowired
  PokemonService pokemonService;

  final static String SOURCE_URL = "https://pokeapi.co/api/v2/pokemon/";

  public static void main(String[] args) {
    SpringApplication.run(SpringbootApplication.class, args);
  }

  @GetMapping("/pokemon/{id}")
  public String pokemon(@PathVariable int id) {
    var result = pokemonService.getById((long) id);
    if (result.isPresent()) {
      return result.get().getName();
    } else {
      return "Not found!";
    }
  }

  @GetMapping("/")
  public String readme() {
    return "Hello world!<br/>" +
            "1. Visit /load to tell the server to (re)fetch the dataset from https://pokeapi.co/api/v2/pokemon/.<br/>" +
            "2. Visit /pokemon/{id} to view pokemon names";
  }

  @GetMapping("/load")
  public String load() throws IOException  {

    System.out.println("Loading");
    URL url = new URL(SOURCE_URL);
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("GET");
    con.setRequestProperty("accept", "application/json");

    InputStream responseStream = con.getInputStream();
    ObjectMapper mapper = new ObjectMapper();
    System.out.println("Returned");
    PokemonResult pokemonResult = mapper.readValue(responseStream, PokemonResult.class);

    System.out.println("Got " + pokemonResult.results.size() + " results");
    List<Pokemon> parsed = new ArrayList<>();
    for (int i = 0; i < pokemonResult.results.size(); i++) {
      parsed.add(new Pokemon((long) i, pokemonResult.results.get(i).name));
    }
    pokemonService.saveAll(parsed);

    return "OK!";
  }

}
// [END gae_java11_helloworld]
