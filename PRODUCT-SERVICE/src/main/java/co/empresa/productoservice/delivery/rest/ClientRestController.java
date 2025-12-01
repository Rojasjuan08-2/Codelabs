package co.empresa.productoservice.delivery.rest;

import co.empresa.productoservice.domain.exception.NoHayClientesException;
import co.empresa.productoservice.domain.exception.PaginaSinClientesException;
import co.empresa.productoservice.domain.exception.ClienteNoEncontradoException;
import co.empresa.productoservice.domain.exception.ValidationException;
import co.empresa.productoservice.domain.model.Client;
import co.empresa.productoservice.domain.service.IClientService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/client-service")
public class ClientRestController {

    // Declaramos como final el servicio para mejorar la inmutabilidad
    private final IClientService clientService;

    // Constantes para los mensajes de respuesta
    private static final String MENSAJE = "mensaje";
    private static final String CLIENTE = "cliente";
    private static final String CLIENTES = "clientes";

    // Inyección de dependencia del servicio que proporciona servicios de CRUD
    public ClientRestController(IClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * Listar todos los clientes.
     */
    @GetMapping("/clientes")
    public ResponseEntity<Map<String, Object>> getClients() {
        List<Client> clients = clientService.findAll();

        if (clients.isEmpty()) {
            throw new NoHayClientesException();
        }

        Map<String, Object> response = new HashMap<>();
        response.put(CLIENTES, clients);
        return ResponseEntity.ok(response);
    }

    /**
     * Listar clientes con paginación de 4 elementos.
     */
    @GetMapping("/cliente/page/{page}")
    public ResponseEntity<Object> index(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 4);
        Page<Client> clients = clientService.findAll(pageable);

        if (clients.isEmpty()) {
            throw new PaginaSinClientesException(page);
        }

        return ResponseEntity.ok(clients);
    }

    /**
     * Crear un nuevo cliente pasando el objeto en el cuerpo de la petición, usando validaciones
     */
    @PostMapping("/clientes")
    public ResponseEntity<Map<String, Object>> save(@Valid @RequestBody Client client, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(result);
        }

        Map<String, Object> response = new HashMap<>();
        Client newClient = clientService.save(client);
        response.put(MENSAJE, "El cliente ha sido creado con éxito!");
        response.put(CLIENTE, newClient);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    /**
     * Eliminar un cliente pasando el objeto en el cuerpo de la petición.
     */
    @DeleteMapping("/clientes")
    public ResponseEntity<Map<String, Object>> delete(@RequestBody Client client) {
        clientService.findById(client.getId())
            .orElseThrow(() -> new ClienteNoEncontradoException(client.getId()));
        clientService.delete(client);
        Map<String, Object> response = new HashMap<>();
        response.put(MENSAJE, "El cliente ha sido eliminado con éxito!");
        response.put(CLIENTE, null);
        return ResponseEntity.ok(response);
    }

    /**
     * Actualizar un cliente pasando el objeto en el cuerpo de la petición.
     * @param client: Objeto Cliente que se va a actualizar
     */
    @PutMapping("/clientes")
    public ResponseEntity<Map<String, Object>> update(@Valid @RequestBody Client client, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(result);
        }

        clientService.findById(client.getId())
                .orElseThrow(() -> new ClienteNoEncontradoException(client.getId()));
        Map<String, Object> response = new HashMap<>();
        Client updatedClient = clientService.update(client);
        response.put(MENSAJE, "El cliente ha sido actualizado con éxito!");
        response.put(CLIENTE, updatedClient);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener un cliente por su ID.
     */
    @GetMapping("/clientes/{id}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long id) {
        Client client = clientService.findById(id)
                .orElseThrow(() -> new ClienteNoEncontradoException(id));
        Map<String, Object> response = new HashMap<>();
        response.put(MENSAJE, "El cliente ha sido encontrado con éxito!");
        response.put(CLIENTE, client);
        return ResponseEntity.ok(response);
    }
}
