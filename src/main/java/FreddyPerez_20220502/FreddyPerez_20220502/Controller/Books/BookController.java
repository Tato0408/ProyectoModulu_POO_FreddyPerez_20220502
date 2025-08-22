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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/apiBook")
public class BookController {
    @Autowired
    private BookService service;

    @GetMapping("/getBooks")
    public List<BookDTO> getBook(){
        return service.getBooks();
    }

    @GetMapping("/getBookById/{id}")
    public List<BookDTO> getBookById(@PathVariable Long id){
        return service.getBookById();
    }

    @PostMapping("/insertBook")
    public ResponseEntity<Map<String, Object>> insertBook(@Valid @RequestBody BookDTO dto, HttpServletRequest request){
        try{
            BookDTO answer = service.insertBook(dto);
            if(answer == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                   "status", "error",
                   "error type", "Validation error",
                        "mesage", "No ha sido posible insertar un libro"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "status", "success",
                    "data", answer
            ));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "Error de insercion",
                    "detail", e.getMessage()
            ));
        }
    }

    @PutMapping("/updateBook/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @Valid @RequestBody BookDTO dto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            Map<String, String > errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        try{
            BookDTO answer = service.updateBook(id, dto);
            if(answer == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "status", "error",
                        "error type", "Validation error",
                        "message", answer
                ));
            }
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of(
                    "status", "success",
                    "data", answer
            ));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "Error de actualizacion",
                    "detail", e.getMessage()
            ));
        }
    }

    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<Map<String, Object>> deleteBook(@PathVariable Long id){
        try{
            if(!service.deleteBook(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "error", "not found",
                        "mesaage", "id no encontrado",
                        "timestamp", Instant.now().toString()
                ));
            }
            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "status", "process completed",
                    "message", "Proceso completado con exito"
            ));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "Error de eliminacion",
                    "detail", e.getMessage()
            ));
        }
    }
}
