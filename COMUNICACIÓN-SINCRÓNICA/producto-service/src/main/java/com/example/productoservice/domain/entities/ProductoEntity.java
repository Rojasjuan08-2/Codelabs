package com.example.productoservice.domain.entities;

public class ProductoEntity {
    public record Producto(Long id, String nombre, Double precio) {}
}
