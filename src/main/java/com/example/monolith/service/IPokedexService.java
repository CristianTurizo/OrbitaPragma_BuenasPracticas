package com.example.monolith.service;

import com.example.monolith.dto.PokemonDto;

import java.util.List;

public interface IPokedexService {

    void savePokemon(PokemonDto pokemonDto);

    PokemonDto getPokemon(Long pokemonNumber);

    List<PokemonDto> getAllPokemon();

    void updatePokemon (PokemonDto pokemonDto);

    void deletePokemon (Long pokemonNumber);

}