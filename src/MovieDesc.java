import java.io.Serializable;

public class MovieDesc implements Serializable{
    String movieName;
    String isbn;
    String synopsis;
    Integer price;

    public MovieDesc(String movieName, String isbn, String synopsis,Integer price) {
        this.movieName = movieName;
        this.isbn = isbn;
        this.synopsis = synopsis;
        this.price = price;
    }

	public String getMovieName() {
		return movieName;
	}

	public String getIsbn() {
		return isbn;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public Integer getPrice() {
		return price;
	}
	
	@Override
	public String toString() {
		return "[" + this.isbn + ", " + movieName + ", " + this.price + "]";
	}
}
