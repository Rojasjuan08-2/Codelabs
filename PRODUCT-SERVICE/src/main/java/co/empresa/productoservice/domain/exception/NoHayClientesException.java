package co.empresa.productoservice.domain.exception;

public class NoHayClientesException extends RuntimeException {
    public NoHayClientesException() {
        super("No hay clientes en la base de datos.");
    }
}