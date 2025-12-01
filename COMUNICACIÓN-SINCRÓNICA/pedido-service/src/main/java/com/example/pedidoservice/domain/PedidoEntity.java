package com.example.pedidoservice.domain;

public class PedidoEntity {
    public record PedidoRequest(Long productoId, int cantidad) {}
    public record Pedido(String id, ProductoDTO producto, int cantidad) {}
}
