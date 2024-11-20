package peaksoft.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import peaksoft.enums.Country;
import peaksoft.enums.Language;

@Entity
@Table(name = "movie_infos")
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class MovieInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String director;
    String actor;
    @Enumerated(EnumType.STRING)
    Country country;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Language language;
    @Lob                // использовал для добавления неограниченного размера текста
    String description;

    @OneToOne(mappedBy = "movieInfo",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Movie movie;
}
