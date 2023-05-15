package talataa.example.demo.application;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import talataa.example.demo.domain.Message;
import talataa.example.demo.domain.Movie;
import talataa.example.demo.domain.MovieInterface;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {


    private final MovieInterface movieInterface;

    public List<Movie> all(int page) {
        return movieInterface.all(page);
    }

    public Optional<Movie> getMovieById(Long id){
        return movieInterface.getMovieById(id);
    }

    public Message delete(Long id){
        movieInterface.getMovieById(id).ifPresent(movie -> movieInterface.delete(id));
        Message message =new Message();
        message.setStatus_code(0);
        message.setStatus_message("Ha ocurrido un error desconocido");
        return message;
    }
}
