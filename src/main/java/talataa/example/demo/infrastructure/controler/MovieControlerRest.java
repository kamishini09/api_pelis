package talataa.example.demo.infrastructure.controler;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import talataa.example.demo.application.MovieService;
import talataa.example.demo.domain.Message;
import talataa.example.demo.domain.Movie;



import java.util.List;

@RestController
@RequestMapping("/movies")
@AllArgsConstructor
public class MovieControlerRest {

    private final MovieService movieService;

    @GetMapping("/all/{page}")
    public ResponseEntity<List<Movie>> all(@PathVariable("page") int page){
        return new ResponseEntity<>(movieService.all(page), HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable ("id") Long id ){
        return movieService.getMovieById(id).map(movie -> new ResponseEntity<>(movie,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    };

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Message> delete(@PathVariable("id")  Long id){
            return new ResponseEntity(movieService.delete(id),HttpStatus.OK);

    }

}
