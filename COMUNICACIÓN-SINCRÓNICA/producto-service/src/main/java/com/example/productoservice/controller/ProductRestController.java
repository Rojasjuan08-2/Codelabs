package com.example.productoservice.controller;

import com.example.productoservice.domain.entities.ProductoEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductRestController {
    private static final List<ProductoEntity.Producto> PRODUCTOS = List.of(
            new ProductoEntity.Producto(1L, "Laptop", 1200.00),
            new ProductoEntity.Producto(2L, "Smartphone", 800.00),
            new ProductoEntity.Producto(3L, "Laptop ASUS", 1200.00),
            new ProductoEntity.Producto(4L, "Smartphone Samsung", 800.00)
    );

    @GetMapping("/{id}")
    public ProductoEntity.Producto obtenerProducto(@PathVariable Long id) {
        return PRODUCTOS.stream()
                .filter(p -> p.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/productos")
    public List<ProductoEntity.Producto> listarProductos() {
        return List.of(
                new ProductoEntity.Producto(1L, "Laptop", 1500.0),
                new ProductoEntity.Producto(2L, "Mouse", 25.0),
                new ProductoEntity.Producto(3L, "Laptop ASUS", 1200.00),
                new ProductoEntity.Producto(4L, "Smartphone Samsung", 800.00)
        );
    }
}
