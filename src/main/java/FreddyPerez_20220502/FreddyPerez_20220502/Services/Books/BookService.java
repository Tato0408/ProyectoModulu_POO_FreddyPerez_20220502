package FreddyPerez_20220502.FreddyPerez_20220502.Services.Books;

import FreddyPerez_20220502.FreddyPerez_20220502.Entities.Books.BookEntity;
import FreddyPerez_20220502.FreddyPerez_20220502.Models.DTO.Books.BookDTO;
import FreddyPerez_20220502.FreddyPerez_20220502.Repositories.Books.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Provider;
import java.util.List;
import java.util.stream.Collectors;

@Service @Slf4j
public class BookService {
    @Autowired
    private BookRepository repo;
    public List<BookDTO> getBooks(){
        List<BookEntity> entity = repo.findAll();
        return entity.stream()
                .map(this::BookToDTO)
                .collect(Collectors.toList());
    }

    private BookDTO BookToDTO(BookEntity entity){
        BookDTO dto = new BookDTO();

    }
}
