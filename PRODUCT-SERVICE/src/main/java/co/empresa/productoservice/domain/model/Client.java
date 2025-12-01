package co.empresa.productoservice.domain.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message ="No puede estar vacio")
    @Size(min=2, max=100, message="El tamaño tiene que estar entre 2 y 100 caracteres")
    @Column(nullable=false)
    private String name;

    @Min(0)
    @Max(100)
    @Column(nullable=false)
    private Integer age;

    @NotEmpty(message = "No puede estar vacio")
    @Size(min = 3, max = 20, message = "El tamaño tiene que estar entre 3 y 20 caracteres")
    @Column(nullable = false)
    private String gender;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe tener un formato válido")
    private String email;

    @NotBlank(message = "El celular es obligatorio")
    @Pattern(regexp = "\\d{10}", message = "El celular debe tener 10 dígitos")
    private String phone;


}
