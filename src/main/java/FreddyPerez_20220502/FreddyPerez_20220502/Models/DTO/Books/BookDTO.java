package FreddyPerez_20220502.FreddyPerez_20220502.Models.DTO.Books;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

@ToString @EqualsAndHashCode
@Getter @Setter
public class BookDTO {

    @Getter @Setter
    private Long id;

    @NotBlank(message = "El titulo no puede ser nulo")
    private String titulo;

    @NotBlank(message = "El isbn no puede ser nulo")
    private String isbn;

    @Positive
    private Long anio_publicacion;

    @NotBlank(message = "El genero no puede ser nulo")
    private String genero;

    @Getter @Setter
    private Long id_Autor;
}
