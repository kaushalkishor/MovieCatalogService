package bookmyservice.movieservice.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import bookmyservice.movieservice.com.model.Movie;
import bookmyservice.movieservice.com.model.MovieCatalog;
import bookmyservice.movieservice.com.model.MovieRating;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private WebClient.Builder webclient;

	@GetMapping("{userId}")
	@ResponseBody
	@CircuitBreaker(name = "movie-circut", fallbackMethod = "fallbackMoviecatalog")
	public List<MovieCatalog> getMovieCatlog(@PathVariable("userId") String userId) {
		// get all the rating for all the movies that userId
		// put it altogether

		List<MovieRating> movieRatings = webclient.build().get().uri("http://localhost:9092/ratings/user/" + userId)
				.retrieve().bodyToFlux(MovieRating.class).collectList().block();

		return movieRatings.stream().map(t -> {
			Movie movie = webclient.build().get().uri("http://localhost:9095/movies/" + t.getMovieId()).retrieve()
					.bodyToMono(Movie.class).block();

			// its will block and its async call
			//

			// restTemplate.getForObject("http://localhost:9095/movies/" + t.getMovieId(),
			// Movie.class);
			return new MovieCatalog(movie.getMovieId(), movie.getName(), t.getRating());

		}).collect(Collectors.toList());

	}

	public List<MovieCatalog> fallbackMoviecatalog(String movieId, Exception ex) {
		return List.of(new MovieCatalog("bahubali", "123", 5));
	}

}
