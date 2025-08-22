package FreddyPerez_20220502.FreddyPerez_20220502.Services.Books;

import FreddyPerez_20220502.FreddyPerez_20220502.Entities.Books.BookEntity;
import FreddyPerez_20220502.FreddyPerez_20220502.Models.DTO.Books.BookDTO;
import FreddyPerez_20220502.FreddyPerez_20220502.Repositories.Books.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//Especializacion de @Component, cada clase Service en el proyecto debe llevar la anotacion
@Service
@Slf4j
public class BookService {
    //Inyectamos el repositorio de Libro
    @Autowired
    private BookRepository repo;
    //Creamos un Metodo de Lista que sirve para buscar todos los los libros que existen
    public List<BookDTO> getBooks(){
        //Mediante un objeto de tipo entity mandamos a llamar al metodo finAll del repository para encontrar todos los elementos del libro
        List<BookEntity> entity = repo.findAll();
        //Retorno de datos convirtiendolos a DTO y mandandolos en formato de lista
        return entity.stream()
                .map(this::BookToDTO)
                .collect(Collectors.toList());
    }

    //Metodo que sirve para buscar un libro mediante su ID
    public Optional<BookDTO> getBookById(Long id){
        //Mediante un objetod de tipo Entity buscamos el libro con el metodo de repository findById
        Optional<BookEntity> entity = repo.findById(id);
        //Retrono de un Map convirtiendo los datos a DTO
        return entity.map(this::BookToDTO);
    }

    //Metdo que sirve para insertar un libro, recibiendo como parametros la Data
    public BookDTO insertBook(BookDTO dto){
        //Si la data llega a ser nula
        if(dto == null){
            //Mandamos una excepcion qie doga que la data no puede ser nula o vacia
            throw new IllegalArgumentException("Book cant be null or empty");
        }
        try{
            //Creamos un objeto de tipo entity que tenga como valor los valores de la data del dto, convirtiendolos antes a datos Entity
            BookEntity entity = DTOToBook(dto);

            //Creamos un objeto de tipo entity que tenga la respuesta y los datos del metodo repo.save ocn parametro de entity
            BookEntity savedEntity = repo.save(entity);
            //Retornamos el metodo de convertir a DTO como parametro savedEntity
            return BookToDTO(savedEntity);
        }
        catch (Exception e){
            //Excepcion diciendo que no ha sido posible crear un libro
            throw new RuntimeException("Cant create book" + e.getMessage());
        }
    }

    //Metodo que sirve para actualizar un libro, obtendiendo com parametros los Id y la data
    public BookDTO updateBook(Long id, BookDTO dto){
        try{
            //Evaluamos si eñ ID existe, si sí existe
            if(repo.existsById(id)){
                //Creamos un objeto de tipo entity que almacene el metodo que obtiene el id, como param,etro el id que nosotros colocamos
                BookEntity entity = repo.getById(id);
                //Despues asignamos los valores de los SET del entity con los valores que tienen los GET del DTO
                entity.setTitulo(dto.getTitulo());
                entity.setIsbn(dto.getIsbn());
                entity.setGenero(dto.getGenero());
                entity.setAnio_publicacion(dto.getAnio_publicacion());
                entity.setId_Autor(dto.getId_Autor());

                //Creamos un objeto de tipo entity que almacene la respuetsa y data del metodo repo.save, que recibe de parametro todos los datos de entity
                BookEntity savedEntity = repo.save(entity);
                //retornamos el la data convertida a DTO
                return BookToDTO(savedEntity);
            }
            //Ecxepcion que dice que el id que se intenta actualizar no existe
            throw new IllegalArgumentException("The book with the id " + id + " doesnt exist");
        }
        catch (Exception e){
            //Excepcion diciendo que no ha sido posible actualizar un libro
            throw new RuntimeException("Cant update book"+ e.getMessage());
        }
    }

    //Metodo que sirve para eliminar un libro recibiendo como para metro su ID
    public boolean deleteBook(Long id){
        try{
            //Evaluamos si existe el ID, si sí existe:
            if(repo.existsById(id)){
                //Mandamos a llamar al metdo de eliminacion que recibe cmo parametro el ID recibido con anterioridafd
                repo.deleteById(id);
                //Retornamos un true
                return true;
            }
            else{
                //Si nada funciona retornamos un False
                return false;
            }
        }
        catch (EmptyResultDataAccessException e){
            //Ecxepcion que dice que el id que se intenta eliminar no existe
            throw new EmptyResultDataAccessException("The book with the id " + id + " doesnt exist", 1);
        }
    }

    //Metodo que sirve para convertir la data de Entity a DTO
    private BookDTO BookToDTO(BookEntity entity){
        //Se crea un objeto de tipo DTO
        BookDTO dto = new BookDTO();
        //Asignamos los valores que tiene eL entity a sus respestivos valores en el DTO
        dto.setId(entity.getId());
        dto.setTitulo(entity.getTitulo());
        dto.setIsbn(entity.getIsbn());
        dto.setGenero(entity.getGenero());
        dto.setAnio_publicacion(entity.getAnio_publicacion());
        dto.setId_Autor(entity.getId_Autor());
        //Finalizando retornando la data convertida a DTO
        return dto;
    }

    //Metodo que sirve para convertir la data de DTO a Entity
    private BookEntity DTOToBook(BookDTO dto){
        //Se crea un objeto de tipo Entity
        BookEntity entity = new BookEntity();

        //Asignamos los valores que tiene eL DTO a sus respestivos valores en el Entity
        entity.setId(dto.getId());
        entity.setTitulo(dto.getTitulo());
        entity.setIsbn(dto.getIsbn());
        entity.setGenero(dto.getGenero());
        entity.setAnio_publicacion(dto.getAnio_publicacion());
        entity.setId_Autor(dto.getId_Autor());
        //Finalizando retornando la data convertida a DTO
        return entity;
    }
}
