package talataa.example.demo.domain;

import java.util.List;
import java.util.Optional;

public interface MovieInterface {

    List<Movie> all(int page);
    Optional<Movie> getMovieById(Long id);
    Message delete(Long id);
}
