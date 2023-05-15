package talataa.example.demo.infrastructure.adapter;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import talataa.example.demo.domain.Message;
import talataa.example.demo.domain.Movie;
import talataa.example.demo.domain.MovieInterface;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Service
@RequiredArgsConstructor
public class MoviesRepository implements MovieInterface {

    @Value("${tmdb.api.base-url}")
    private String tmdbApiBaseUrl;

    @Value("${tmdb.api.key}")
    private String tmdbApiKey;

    @Autowired
    private final RestTemplate restTemplate;

    @Override
    public List<Movie> all(int page) {
        String url =tmdbApiBaseUrl +"discover/movie?language=en-US&page="+page+"&sort_by=popularity.desc&api_key="+tmdbApiKey;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            return tranformMovieList(response);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<Movie> getMovieById(Long id) {
        String url =tmdbApiBaseUrl +"movie/"+id+"?api_key="+tmdbApiKey;
        Movie search = restTemplate.getForObject(url,Movie.class);
        if ( search == null){
            return Optional.ofNullable(null);
        }else {
            return Optional.of(search);
        }
    }

    @Override
    public Message delete(Long id) {
        String url =tmdbApiBaseUrl +"movie/"+id+"?api_key="+tmdbApiKey;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();
        try {
            Response response = client.newCall(request).execute();
            return transforMessage(response);
        } catch (IOException e) {
            e.printStackTrace();
            return messageError();
        }

    }


    private List<Movie> tranformMovieList(Response response) throws IOException {
        if (response.isSuccessful()) {
            Gson gson = new Gson();
            String jsonResponse = response.body().string();
            JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
            JsonArray resultsArray = jsonObject.getAsJsonArray("results");
            List<Movie> movieList = gson.fromJson(resultsArray, new TypeToken<List<Movie>>() {}.getType());
            return movieList;
        } else {
            return new ArrayList<>();
        }
    }

    private Message transforMessage(Response response) throws IOException{
        if (response.isSuccessful()){
            Gson gson = new Gson();
            String jsonResponse = response.body().string();
            JsonObject jsonObject =gson.fromJson(jsonResponse,JsonObject.class);
            Message message= gson.fromJson(jsonObject,Message.class);
            return message;
        }else {
            return messageError();
        }
    }

    private Message messageError(){
        Message message = new Message();
        message.setStatus_code(0);
        message.setStatus_message("Ha ocurrido un error desconocido");
        return message;
    }



}
