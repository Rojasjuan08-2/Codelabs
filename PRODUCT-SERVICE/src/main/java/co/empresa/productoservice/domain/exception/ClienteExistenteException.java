package co.empresa.productoservice.domain.exception;

public class ClienteExistenteException extends RuntimeException {
    public ClienteExistenteException(String nombre) {
        super("El cliente con nombre '" + nombre + "' ya existe.");
    }
}
