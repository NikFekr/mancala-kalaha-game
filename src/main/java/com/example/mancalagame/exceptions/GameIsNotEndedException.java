package com.example.mancalagame.exceptions;

public class GameIsNotEndedException extends RuntimeException {

    public static final String GAME_IS_NOT_ENDED = "Game IS Not Ended";
    public GameIsNotEndedException(String message) {
        super(message);
    }
}
