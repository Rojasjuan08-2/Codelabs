package com.example.pedidoservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @GetMapping("/crear")
    public String crearPedido() {
        return "El pedido está en proceso y se recibirá en cuanto llegue el mensaje asíncrono.";
    }
}
