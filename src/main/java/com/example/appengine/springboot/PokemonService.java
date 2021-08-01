package com.example.appengine.springboot;

import com.example.appengine.springboot.models.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PokemonService {

    @Autowired
    private PokemonRepository pokemonRepository;

    public Optional<Pokemon> getById(Long id) {
        return pokemonRepository.findById(id);
    }

    public void save(Pokemon data) {
        pokemonRepository.save(data);
    }
}