package com.victorjesus.security.auth.domain.movies;

import lombok.Getter;

@Getter
public enum Genre {
    ACTION("acao"),
    COMEDY("comedia"),
    DRAMA("drama"),
    ROMANCE("romance"),
    CRIME("crime");

    private final String genre;

    Genre(String genre) {
        this.genre = genre;
    }

}
