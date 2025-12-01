package co.empresa.productoservice.domain.exception;

public class PaginaSinClientesException extends RuntimeException {
    public PaginaSinClientesException(int page) {
        super("No hay clientes en la página solicitada: " + page);
    }
}