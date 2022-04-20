package com.example.mancalagame.servises;

import com.example.mancalagame.enums.PitType;
import com.example.mancalagame.exceptions.GameIsNotEndedException;
import com.example.mancalagame.exceptions.GameIsNotStartedException;
import com.example.mancalagame.exceptions.StonesOfBigPitCanNotBePlayedException;
import com.example.mancalagame.models.Pit;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static com.example.mancalagame.exceptions.GameIsNotEndedException.GAME_IS_NOT_ENDED;
import static com.example.mancalagame.exceptions.GameIsNotStartedException.GAME_IS_NOT_STARTED;
import static com.example.mancalagame.exceptions.StonesOfBigPitCanNotBePlayedException.STONES_OF_BIG_PIT_CAN_NOT_BE_PLAYED;

@Service
public class MancalaGame {

    public static final int EMPTY = 0;
    public static final int STONE_COUNT = 6;
    public static final String PLAYER_ONE = "Player One";
    public static final String PLAYER_TWO = "Player Two";
    private Pit[] gameBoard = null;
    private int turn;
    private boolean hasPrise = false;

    public void start() {
        initializeBoard();
        setTurn(1);
    }

    private void initializeBoard() {
        gameBoard = new Pit[14];
        Pit pit0 = new Pit(STONE_COUNT, "Pit1", PitType.NORMAL_PIT);
        Pit pit1 = new Pit(STONE_COUNT, "Pit2", PitType.NORMAL_PIT);
        Pit pit2 = new Pit(STONE_COUNT, "Pit3", PitType.NORMAL_PIT);
        Pit pit3 = new Pit(STONE_COUNT, "Pit4", PitType.NORMAL_PIT);
        Pit pit4 = new Pit(STONE_COUNT, "Pit5", PitType.NORMAL_PIT);
        Pit pit5 = new Pit(STONE_COUNT, "Pit6", PitType.NORMAL_PIT);

        Pit pit6 = new Pit(EMPTY, "Player1BigPit", PitType.BIG_PIT);

        Pit pit7 = new Pit(STONE_COUNT, "Pit8", PitType.NORMAL_PIT);
        Pit pit8 = new Pit(STONE_COUNT, "Pit9", PitType.NORMAL_PIT);
        Pit pit9 = new Pit(STONE_COUNT, "Pit10", PitType.NORMAL_PIT);
        Pit pit10 = new Pit(STONE_COUNT, "Pit11", PitType.NORMAL_PIT);
        Pit pit11 = new Pit(STONE_COUNT, "Pit12", PitType.NORMAL_PIT);
        Pit pit12 = new Pit(STONE_COUNT, "Pit13", PitType.NORMAL_PIT);

        Pit pit13 = new Pit(EMPTY, "Player2BigPit", PitType.BIG_PIT);

        gameBoard[0] = pit0;
        gameBoard[1] = pit1;
        gameBoard[2] = pit2;
        gameBoard[3] = pit3;
        gameBoard[4] = pit4;
        gameBoard[5] = pit5;
        gameBoard[6] = pit6;
        gameBoard[7] = pit7;
        gameBoard[8] = pit8;
        gameBoard[9] = pit9;
        gameBoard[10] = pit10;
        gameBoard[11] = pit11;
        gameBoard[12] = pit12;
        gameBoard[13] = pit13;
    }

    public Pit[] getGameBoard() {
        if (Objects.isNull(gameBoard)) {
            throw new GameIsNotStartedException(GAME_IS_NOT_STARTED);
        }
        return gameBoard;
    }

    public int pickStonesOfPit(int pitIndex) {

        validatePlayMove(pitIndex);

        Pit pit = gameBoard[pitIndex];
        int stones = pit.getStoneCount();
        pit.setStoneCount(0);

        return stones;
    }

    private void validatePlayMove(int pitIndex) {
        if (pitIndex == 13 || pitIndex == 6) {
            throw new StonesOfBigPitCanNotBePlayedException(STONES_OF_BIG_PIT_CAN_NOT_BE_PLAYED);
        }

        if (Objects.isNull(gameBoard)) {
            throw new GameIsNotStartedException(GAME_IS_NOT_STARTED);
        }
    }

    public int addStonesToNextPits(int playedPitIndex, int stones) {
        if (Objects.isNull(gameBoard)) {
            throw new GameIsNotStartedException(GAME_IS_NOT_STARTED);
        }

        playedPitIndex++;

        while (stones > 0) {

            if (playedPitIndex > 13) {
                playedPitIndex = 0;
            }

            if (isNotOpponentPigPit(playedPitIndex)) {
                addStoneToPit(gameBoard[playedPitIndex]);
                stones--;
            }

            if (stones > 0)
                playedPitIndex++;
        }

        if (verifyCapturePrise(gameBoard[playedPitIndex])) {
            hasPrise = true;
        }

        if (verifyChangeTurn(playedPitIndex))
            setTurn(turn == 1 ? 2 : 1);

        return playedPitIndex;
    }

    private boolean verifyCapturePrise(Pit pit) {
        return pit.getStoneCount() == 1;
    }

    private void addStoneToPit(Pit pit) {
        int stoneCount = pit.getStoneCount();
        pit.setStoneCount(++stoneCount);
    }

    private boolean verifyChangeTurn(int playedPitIndex) {
        return playedPitIndex != 13 && playedPitIndex != 6;
    }

    private boolean isNotOpponentPigPit(int playedPitIndex) {
        return !(turn == 1 && playedPitIndex == 13 || turn == 2 && playedPitIndex == 6);
    }

    public boolean checkIsGameEnded() {
        return Arrays.stream(gameBoard, 0, 6).allMatch(pit -> pit.getStoneCount() == 0)
                || Arrays.stream(gameBoard, 7, 13).allMatch(pit -> pit.getStoneCount() == 0);
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getTurn() {
        return turn;
    }

    public String findTheWinner() {
        if (Objects.isNull(gameBoard)) {
            throw new GameIsNotStartedException(GAME_IS_NOT_STARTED);
        }

        if (isPlayerOneTheWinner()) {
            return PLAYER_ONE;
        } else {
            return PLAYER_TWO;
        }

    }

    private boolean isPlayerOneTheWinner() {
        if (!checkIsGameEnded()) {
            throw new GameIsNotEndedException(GAME_IS_NOT_ENDED);
        }

        Optional<Integer> sumOfThePlayerOneStones = Arrays.stream(gameBoard, 0, 7)
                .map(Pit::getStoneCount)
                .reduce(Integer::sum);

        Optional<Integer> sumOfThePlayerTwoStones = Arrays.stream(gameBoard, 7, 14)
                .map(Pit::getStoneCount)
                .reduce(Integer::sum);

        return sumOfThePlayerOneStones.orElse(0) > sumOfThePlayerTwoStones.orElse(0);
    }

    public void captureStonesToBigPitInPrise(int lastPlayedIndex, int bigPitIndex) throws IllegalArgumentException {
        if (Objects.isNull(gameBoard)) {
            throw new GameIsNotStartedException(GAME_IS_NOT_STARTED);
        }

        if (hasPrise) {
            capturePitStones(lastPlayedIndex, gameBoard[bigPitIndex]);
            hasPrise = false;
        }

    }

    public void captureStonesToPlayerOwnLastPlayedIndexInPrise(int lastPlayedIndex) throws IllegalArgumentException {
        if (Objects.isNull(gameBoard)) {
            throw new GameIsNotStartedException(GAME_IS_NOT_STARTED);
        }

        if (hasPrise) {
            capturePitStones(lastPlayedIndex, gameBoard[lastPlayedIndex]);
            hasPrise = false;
        }

    }

    private void capturePitStones(int lastPlayedIndex, Pit pit) throws IllegalArgumentException {
        int stoneCount = gameBoard[lastPlayedIndex].getStoneCount() +
                gameBoard[getOpponentPit(lastPlayedIndex)].getStoneCount();

        pit.setStoneCount((isNormalPit(pit) ? 0 : pit.getStoneCount()) + stoneCount);
    }

    private boolean isNormalPit(Pit pit) {
        return pit.getPitType().equals(PitType.NORMAL_PIT);
    }

    private int getOpponentPit(int pitIndex) throws IllegalArgumentException {
        if (pitIndex < 0 || pitIndex >= 13 || pitIndex == 6) {
            throw new IllegalArgumentException("Pit Number is out of range");
        }

        return 12 - pitIndex;

    }
}
