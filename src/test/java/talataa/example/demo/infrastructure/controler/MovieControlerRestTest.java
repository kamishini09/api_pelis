package talataa.example.demo.infrastructure.controler;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import talataa.example.demo.application.MovieService;
import talataa.example.demo.domain.Message;
import talataa.example.demo.domain.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class MovieControlerRestTest {


    @Mock
    private MovieService movieService;


    private MovieControlerRest movieControlerRest;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        movieControlerRest = new MovieControlerRest(movieService);
    }

    @Test
    void all() {
        List<Movie> movie = new ArrayList<>();
        Movie movie1 = new Movie();
        movie1.setId(1L);
        movie1.setTitle("Película 1");
        movie.add(movie1);

        Mockito.when(movieService.all(Mockito.anyInt())).thenReturn(movie);

        var responseEntity = movieControlerRest.all(Mockito.anyInt());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }


    @Test
    void testGetMovieById() {
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Película 1");

        Mockito.when(movieService.getMovieById(Mockito.anyLong())).thenReturn(Optional.of(movie));

        var  response = movieControlerRest.getMovieById(Mockito.anyLong());

        assertEquals(HttpStatus.OK, response.getStatusCode());



    }

    @Test
    void testGetMovieById_NotFound() {
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Película 1");

        Mockito.when(movieService.getMovieById(Mockito.anyLong())).thenReturn(Optional.empty());

        var  response = movieControlerRest.getMovieById(Mockito.anyLong());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());



    }

    @Test
    void testDelete() {
        Message message =new Message();
        message.setStatus_code(0);
        message.setStatus_message("Ha ocurrido un error desconocido");



        Mockito.when(movieService.delete(Mockito.anyLong())).thenReturn(message);

        var response = movieControlerRest.delete(Mockito.anyLong());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(message, response.getBody());

    }

}