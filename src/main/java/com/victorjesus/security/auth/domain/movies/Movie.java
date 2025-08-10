package com.victorjesus.security.auth.domain.movies;

import com.victorjesus.security.auth.dto.movies.MoviePostRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "movies")
@Entity(name = "movies")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Movie {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private Integer durationInMinutes;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    private String directorName;

    public Movie(MoviePostRequest request) {
        this.name = request.name();
        this.durationInMinutes = request.durationInMinutes();
        this.genre = request.genre();
        this.directorName = request.directorName();
    }
}
