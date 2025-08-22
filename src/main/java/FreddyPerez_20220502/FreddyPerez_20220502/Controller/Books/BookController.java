package FreddyPerez_20220502.FreddyPerez_20220502.Controller.Books;

import FreddyPerez_20220502.FreddyPerez_20220502.Models.DTO.Books.BookDTO;
import FreddyPerez_20220502.FreddyPerez_20220502.Services.Books.BookService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;

//Define un controlador en una aplicacion web
@RestController
//Crea el "Directorio para cada clase"
@RequestMapping("/apiBook")
public class BookController {
    //Inyectamos el Service de Libro
    @Autowired
    private BookService service;

    //Metodo que sirve para buscar todos los libros
    @GetMapping("/getBooks")
    public List<BookDTO> getBook(){
        //Retornamos el metodo del servuice
        return service.getBooks();
    }

    //Metodo que sirve para buscar un libro mediante su ID
    @GetMapping("/getBookById/{id}")
    public Optional<BookDTO> getBookById(@PathVariable Long id){
        //Retornamos el metodo del  service
        return service.getBookById(id);
    }

    //Metodo que sirve para insertar un  libro, hay que tener almenos 1 autor para poder insertar un libro
    @PostMapping("/insertBook")
    public ResponseEntity<Map<String, Object>> insertBook(@Valid @RequestBody BookDTO dto, HttpServletRequest request){
        try{
            //Creamos un objeto de tipo DTO para almacenar el resultado del metodo del service
            BookDTO answer = service.insertBook(dto);
            //Si la respuesta es nula mandamos un BAD REQUEST
            if(answer == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                   "status", "error",
                   "error type", "Validation error",
                        "mesage", "No ha sido posible insertar un libro"
                ));
            }
            //Si sale bien mandamos una respuesta que se pudo crear el libro
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "status", "success",
                    "data", answer
            ));
        }
        catch (Exception e){
            //Si hay algun error mandamos un el mensaje con el motivo del error mediante un INTERNAL SERVER ERROR
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "Error de insercion",
                    "detail", e.getMessage()
            ));
        }
    }

    //Metodo que sirve para actualizar un libro mediante su ID
    @PutMapping("/updateBook/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @Valid @RequestBody BookDTO dto, BindingResult bindingResult){
        //Mediante un objeto de tipo binding result evaluamos si hay alguun error, si es que lo hay mandamos un HashMap con el cuerpo de errores mediante un badRequest
        if(bindingResult.hasErrors()){
            Map<String, String > errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        //Si sale bien intentamos:
        try{
            //Crear un objeto de tipo DTO que almacene el resultado del metodo del service
            BookDTO answer = service.updateBook(id, dto);
            //Si la respuesta es nula mandamos un BAD REQUEST
            if(answer == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "status", "error",
                        "error type", "Validation error",
                        "message", answer
                ));
            }
            //Si sale bien mandamos una respuesta que se pudo actualizar el libro
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of(
                    "status", "success",
                    "data", answer
            ));
        }
        catch (Exception e){
            //Si hay algun error mandamos un el mensaje con el motivo del error mediante un INTERNAL SERVER ERROR
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "Error de actualizacion",
                    "detail", e.getMessage()
            ));
        }
    }

    //Metodo que sirve para eliminar un un libro mediante su ID
    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<Map<String, Object>> deleteBook(@PathVariable Long id){
        try{
            //Evaluamos si exite el ID del libro, si no llega a existir
            if(!service.deleteBook(id)){
                //Mandamos un cuerpo de mensaje mediante un NOT FOUND que dice que no exite el libro, ya que la respuesta fue un false
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "error", "not found",
                        "mesaage", "id no encontrado",
                        "timestamp", Instant.now().toString()
                ));
            }
            //Si existe y se pudo eliminar mandamos un cuerpo de mensaje mediante un OK diciendo que la eliminacion fue exitosa
            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "status", "process completed",
                    "message", "Proceso completado con exito"
            ));
        }
        catch (Exception e){
            //Si hay algun error mandamos un el mensaje con el motivo del error mediante un INTERNAL SERVER ERROR
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "Error de eliminacion",
                    "detail", e.getMessage()
            ));
        }
    }
}
