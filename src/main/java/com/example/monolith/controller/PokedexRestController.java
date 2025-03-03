package com.example.monolith.controller;

import com.example.monolith.dto.PokemonDto;
import com.example.monolith.service.IPokedexService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pokedex")
@RequiredArgsConstructor
public class PokedexRestController {

    private final IPokedexService pokedexService;

    @Operation(summary = "Add a new pokemon")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pokemon created", content = @Content),
            @ApiResponse(responseCode = "409", description = "Pokemon already exists", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<Void> savePokemon(@RequestBody PokemonDto pokemonDto) {
        pokedexService.savePokemon(pokemonDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get a pokemon by their Number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pokemon found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PokemonDto.class))),
            @ApiResponse(responseCode = "404", description = "Pokemon not found", content = @Content)
    })
    @GetMapping("/{number}")
    public ResponseEntity<PokemonDto> getPokemonByNumber(@PathVariable(name = "number") Long pokemonNumber) {
        return ResponseEntity.ok(pokedexService.getPokemonByNumber(pokemonNumber));
    }

    @Operation(summary = "Get all the pokemons")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All pokemons returned",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PokemonDto.class)))),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)
    })
    @GetMapping("/")
    public ResponseEntity<List<PokemonDto>> getAllPokemon() {
        return ResponseEntity.ok(pokedexService.getAllPokemon());
    }

    @Operation(summary = "Update an existing pokemon")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pokemon updated", content = @Content),
            @ApiResponse(responseCode = "404", description = "Pokemon not found", content = @Content)
    })
    @PutMapping("/")
    public ResponseEntity<Void> updatePokemon(@RequestBody PokemonDto pokemonDto) {
        pokedexService.updatePokemon(pokemonDto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete a pokemon by their Number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pokemon deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Pokemon not found", content = @Content)
    })
    @DeleteMapping("/{number}")
    public ResponseEntity<Void> deletePokemonByNumber(
            @Parameter(description = "Number of the pokemon to be deleted")
            @PathVariable(name = "number") Long pokemonNumber
    ) {
        pokedexService.deletePokemon(pokemonNumber);
        return ResponseEntity.noContent().build();
    }
}