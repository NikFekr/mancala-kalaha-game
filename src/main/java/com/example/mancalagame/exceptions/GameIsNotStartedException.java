package com.example.mancalagame.exceptions;

public class GameIsNotStartedException extends RuntimeException {

    public static final String GAME_IS_NOT_STARTED = "Game is Not Started";
    public GameIsNotStartedException(String message) {
        super(message);
    }
}
