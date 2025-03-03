package bookmyservice.movieservice.com.model;

public class MovieRating {
	private String movieId;
	private int rating;

	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public MovieRating(String movieId, int rating) {
		super();
		this.movieId = movieId;
		this.rating = rating;
	}

}
