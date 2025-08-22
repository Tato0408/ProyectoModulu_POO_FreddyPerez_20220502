package FreddyPerez_20220502.FreddyPerez_20220502.Entities.Books;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
//Anotacion que deben llevar todos los Entities, significa que habrean interacciones con la base de datos
@Entity
//Vincula la clase con una tabla de la base de datos
@Table(name="LIBROS")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class BookEntity {
    //Señala cual es la llave primaria
    @Id
    //Define la estrategia de generacion de IDs
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "SEQ_LIBRO")
    @SequenceGenerator(name = "SEQ_LIBRO", sequenceName = "SEQ_LIBRO", allocationSize = 1)
    //Funciona para enlazar cada atributo con una columna en especifico de la base de datos
    @Column(name = "ID")
    private Long id;

    @Column(name = "TITULO")
    private String titulo;

    @Column(name = "ISBN", unique = true)
    private String isbn;

    @Column(name = "AÑO_PUBLICACION")
    private Long anio_publicacion;

    @Column(name = "GENERO")
    private String genero;

    @Column(name = "AUTOR_ID")
    private Long id_Autor;

}






