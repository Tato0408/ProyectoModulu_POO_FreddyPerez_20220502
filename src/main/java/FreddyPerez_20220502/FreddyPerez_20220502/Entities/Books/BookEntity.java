package FreddyPerez_20220502.FreddyPerez_20220502.Entities.Books;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="LIBROS")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class BookEntity {
}
