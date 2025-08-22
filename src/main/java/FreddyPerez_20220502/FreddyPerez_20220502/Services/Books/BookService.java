package FreddyPerez_20220502.FreddyPerez_20220502.Services.Books;

import FreddyPerez_20220502.FreddyPerez_20220502.Entities.Books.BookEntity;
import FreddyPerez_20220502.FreddyPerez_20220502.Models.DTO.Books.BookDTO;
import FreddyPerez_20220502.FreddyPerez_20220502.Repositories.Books.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
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

    public BookDTO insertBook(BookDTO dto){
        if(dto == null){
            throw new IllegalArgumentException("Book cant be null or empty");
        }
        try{
            BookEntity entity = DTOToBook(dto);

            BookEntity savedEntity = repo.save(entity);
            return BookToDTO(savedEntity);
        }
        catch (Exception e){
            throw new RuntimeException("Cant create book" + e.getMessage());
        }
    }

    public BookDTO updateBook(Long id, BookDTO dto){
        try{
            if(repo.existsById(id)){
                BookEntity entity = repo.getById(id);
                entity.setTitulo(dto.getTitulo());
                entity.setIsbn(dto.getIsbn());
                entity.setGenero(dto.getGenero());
                entity.setAnio_publicacion(dto.getAnio_publicacion());
                entity.setId_Autor(dto.getId_Autor());

                BookEntity savedEntity = repo.save(entity);
                return BookToDTO(savedEntity);
            }
            throw new IllegalArgumentException("The book with the id " + id + " doesnt exist");
        }
        catch (Exception e){
            throw new RuntimeException("Cant create book"+ e.getMessage());
        }
    }

    public boolean deleteBook(Long id){
        try{
            if(repo.existsById(id)){
                repo.deleteById(id);
                return true;
            }
            else{
                return false;
            }
        }
        catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("The book with the id " + id + " doesnt exist", 1);
        }
    }

    private BookDTO BookToDTO(BookEntity entity){
        BookDTO dto = new BookDTO();
        dto.setId(entity.getId());
        dto.setTitulo(entity.getTitulo());
        dto.setIsbn(entity.getIsbn());
        dto.setGenero(entity.getGenero());
        dto.setAnio_publicacion(entity.getAnio_publicacion());
        dto.setId_Autor(entity.getId_Autor());
        return dto;
    }

    private BookEntity DTOToBook(BookDTO dto){
        BookEntity entity = new BookEntity();

        entity.setId(dto.getId());
        entity.setTitulo(dto.getTitulo());
        entity.setIsbn(dto.getIsbn());
        entity.setGenero(dto.getGenero());
        entity.setAnio_publicacion(dto.getAnio_publicacion());
        entity.setId_Autor(dto.getId_Autor());
        return entity;
    }
}
