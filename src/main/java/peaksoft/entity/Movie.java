package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import peaksoft.enums.Genre;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "movies")
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString(exclude = {"cinemas", "showTimes"})

public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false, length = 500)
    String image;
    @Column(name = "movie_name",nullable = false)
    String movieName;
    @Column(nullable = false)
    int duration;
    @Enumerated(EnumType.STRING)
    Genre genre;
    @Column(name = "release_date")
    LocalDate releaseDate;
    @Column(name = "age_rating",nullable = false)
    int ageRating;
    @Lob @Column (name = "trailer_url")
    String trailerUrl;

    @ManyToMany(mappedBy = "movies", fetch = FetchType.EAGER)
    private Set<Cinema> cinemas = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "movie_info_id", referencedColumnName = "id")
    private MovieInfo movieInfo;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<ShowTime> showTimes;

    @Override
    public String toString() {
        return "Movie{id=" + id + ", name='" + movieName + "'}";
    }


}
