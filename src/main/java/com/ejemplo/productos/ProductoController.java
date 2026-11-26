package com.ejemplo.productos;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    private final ProductoRepository repositorio;

    public ProductoController(ProductoRepository repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping
    public List<Producto> listar() {
        return repositorio.findAll(Sort.by("id"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Producto crear(@Valid @RequestBody Producto datos) {
        Producto producto = new Producto();
        producto.setNombre(datos.getNombre());
        producto.setPrecio(datos.getPrecio());
        return repositorio.save(producto);
    }

    @PutMapping("/{id}")
    @Transactional
    public Producto editar(@PathVariable Long id, @Valid @RequestBody Producto datos) {
        Producto producto = buscar(id);
        producto.setNombre(datos.getNombre());
        producto.setPrecio(datos.getPrecio());
        return repositorio.save(producto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void eliminar(@PathVariable Long id) {
        repositorio.delete(buscar(id));
    }

    private Producto buscar(Long id) {
        return repositorio.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
    }
}
