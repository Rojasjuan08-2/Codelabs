package com.example.productoservice;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductorService productorService;

    public ProductoController(ProductorService productorService) {
        this.productorService = productorService;
    }

    @PostMapping("/enviar")
    public String enviarProductos(@RequestBody List<ProductoDTO> productos) {
        productorService.enviarListaProductos(productos);
        return "Productos enviados con éxito al pedido-service";
    }
}
