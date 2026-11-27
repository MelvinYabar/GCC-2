package com.ejemplo.pokemon;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/pokemon")
public class PokemonController {
    private final PokemonRepository repositorio;

    public PokemonController(PokemonRepository repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping
    public List<Pokemon> listar() {
        return repositorio.findAll(Sort.by("id"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pokemon crear(@Valid @RequestBody Pokemon datos) {
        Pokemon pokemon = new Pokemon();
        pokemon.setNombre(datos.getNombre());
        pokemon.setTipo(datos.getTipo());
        return repositorio.save(pokemon);
    }

    @PutMapping("/{id}")
    @Transactional
    public Pokemon editar(@PathVariable Long id, @Valid @RequestBody Pokemon datos) {
        Pokemon pokemon = buscar(id);
        pokemon.setNombre(datos.getNombre());
        pokemon.setTipo(datos.getTipo());
        return repositorio.save(pokemon);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void eliminar(@PathVariable Long id) {
        repositorio.delete(buscar(id));
    }

    private Pokemon buscar(Long id) {
        return repositorio.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pokemon no encontrado"));
    }
}
