package FreddyPerez_20220502.FreddyPerez_20220502.Repositories.Books;

import FreddyPerez_20220502.FreddyPerez_20220502.Entities.Books.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
}
