package com.pokemonreview.api.services;

import com.pokemonreview.api.dto.PokemonDto;
import com.pokemonreview.api.dto.PokemonResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PokemonService {
    PokemonDto createPokemon(PokemonDto pokemonDto);

    PokemonResponse getAllPokemon(int pageNo, int pageSize);
    PokemonDto getPokemonById(int id);

    PokemonDto updatePokemon(PokemonDto pokemonDto, int id);

    void DeletePokemonById(int id);
}
