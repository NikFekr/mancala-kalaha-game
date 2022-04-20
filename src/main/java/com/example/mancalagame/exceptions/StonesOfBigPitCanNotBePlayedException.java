package com.example.mancalagame.exceptions;

public class StonesOfBigPitCanNotBePlayedException extends RuntimeException {

    public static final String STONES_OF_BIG_PIT_CAN_NOT_BE_PLAYED = "Stones of big pit can not be played";
    public StonesOfBigPitCanNotBePlayedException(String message) {
        super(message);
    }
}
