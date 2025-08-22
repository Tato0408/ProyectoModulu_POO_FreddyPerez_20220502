package FreddyPerez_20220502.FreddyPerez_20220502.Entities.Books;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="LIBROS")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "seq_libro")
    @SequenceGenerator(name = "seq_libro", sequenceName = "seq_libro", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TITULO")
    private String titulo;

    @Column(name = "ISBN", unique = true)
    private String isbn;

    @Column(name = "AÑO_PUBLICACION")
    private LocalDate anio_publicacion;

    @Column(name = "GENERO")
    private String genero;

    @Column(name = "AUTOR_ID")
    private Long id_Autor;

}
