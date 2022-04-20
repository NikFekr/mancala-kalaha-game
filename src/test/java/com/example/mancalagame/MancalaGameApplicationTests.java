package com.example.mancalagame;

import com.example.mancalagame.exceptions.GameIsNotStartedException;
import com.example.mancalagame.exceptions.StonesOfBigPitCanNotBePlayedException;
import com.example.mancalagame.models.Pit;
import com.example.mancalagame.servises.MancalaGame;
import com.sun.javaws.exceptions.InvalidArgumentException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.mancalagame.exceptions.GameIsNotStartedException.GAME_IS_NOT_STARTED;
import static com.example.mancalagame.exceptions.StonesOfBigPitCanNotBePlayedException.STONES_OF_BIG_PIT_CAN_NOT_BE_PLAYED;
import static com.example.mancalagame.servises.MancalaGame.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = MancalaGameApplication.class)
class MancalaGameApplicationTests {

    @Test
    public void playGame_startGame_startSuccessfully() {
        MancalaGame mancalaGame = startMancalaGame();

        assertThat(mancalaGame.getGameBoard(), is(notNullValue()));
    }

    @Test
    public void loadBoard_initializeBoard_SuccessfullyInitialize() {
        MancalaGame mancalaGame = startMancalaGame();

        assertThat(mancalaGame.getGameBoard(), notNullValue());
        assertThat(mancalaGame.getGameBoard().length, is(14));
        assertThat(mancalaGame.getGameBoard()[0], is(instanceOf(Pit.class)));

        assertThat(mancalaGame.getGameBoard()[0].getStoneCount(), is(STONE_COUNT));
        assertThat(mancalaGame.getGameBoard()[1].getStoneCount(), is(STONE_COUNT));
        assertThat(mancalaGame.getGameBoard()[2].getStoneCount(), is(STONE_COUNT));
        assertThat(mancalaGame.getGameBoard()[3].getStoneCount(), is(STONE_COUNT));
        assertThat(mancalaGame.getGameBoard()[4].getStoneCount(), is(STONE_COUNT));
        assertThat(mancalaGame.getGameBoard()[5].getStoneCount(), is(STONE_COUNT));

        assertThat(mancalaGame.getGameBoard()[6].getStoneCount(), is(EMPTY));

        assertThat(mancalaGame.getGameBoard()[7].getStoneCount(), is(STONE_COUNT));
        assertThat(mancalaGame.getGameBoard()[8].getStoneCount(), is(STONE_COUNT));
        assertThat(mancalaGame.getGameBoard()[9].getStoneCount(), is(STONE_COUNT));
        assertThat(mancalaGame.getGameBoard()[10].getStoneCount(), is(STONE_COUNT));
        assertThat(mancalaGame.getGameBoard()[11].getStoneCount(), is(STONE_COUNT));
        assertThat(mancalaGame.getGameBoard()[12].getStoneCount(), is(STONE_COUNT));

        assertThat(mancalaGame.getGameBoard()[13].getStoneCount(), is(EMPTY));

    }

    @Test
    public void getGameBoard_gameIsNotStarted_throwsException() {
        MancalaGame mancalaGame = new MancalaGame();

        try {
            mancalaGame.getGameBoard();
            assert false;

        } catch (Exception exp) {
            assertThat(exp, is(instanceOf(GameIsNotStartedException.class)));
            assertThat(exp.getMessage(), is(equalTo(GAME_IS_NOT_STARTED)));
        }
    }

    @Test
    public void pickAPit_pickAnInitializedPit_emptyPitRemains() {
        MancalaGame mancalaGame = startMancalaGame();

        int stones = mancalaGame.pickStonesOfPit(4);

        assertThat(mancalaGame.getGameBoard()[4].getStoneCount(), is(0));
        assertThat(stones, is(STONE_COUNT));
    }

    @Test
    public void pickAPit_pickPit6Stones_throwsException() {
        MancalaGame mancalaGame = startMancalaGame();

        try {
            mancalaGame.pickStonesOfPit(6);
            assert false;

        } catch (Exception exp) {
            assertThat(exp, is(instanceOf(StonesOfBigPitCanNotBePlayedException.class)));
            assertThat(exp.getMessage(), is(equalTo(STONES_OF_BIG_PIT_CAN_NOT_BE_PLAYED)));
        }
    }

    @Test
    public void pickAPit_pickPit13Stones_throwsException() {
        MancalaGame mancalaGame = startMancalaGame();

        try {
            mancalaGame.pickStonesOfPit(13);
            assert false;

        } catch (Exception exp) {
            assertThat(exp, is(instanceOf(StonesOfBigPitCanNotBePlayedException.class)));
            assertThat(exp.getMessage(), is(equalTo(STONES_OF_BIG_PIT_CAN_NOT_BE_PLAYED)));
        }
    }

    @Test
    public void pickAPit_gameIsNotStarted_throwsException() {
        MancalaGame mancalaGame = new MancalaGame();

        try {
            mancalaGame.pickStonesOfPit(4);
            assert false;

        } catch (Exception exp) {
            assertThat(exp, is(instanceOf(GameIsNotStartedException.class)));
            assertThat(exp.getMessage(), is(equalTo(GAME_IS_NOT_STARTED)));
        }
    }

    @Test
    public void pickAPitAndPlay_startWith6StonePitAtIndexOf5_emptyPitAndAddStoneTo6NextPitExceptOpponentBigPit() {
        MancalaGame mancalaGame = startMancalaGame();

        int stones = mancalaGame.pickStonesOfPit(5);

        mancalaGame.addStonesToNextPits(5, stones);

        assertThat(mancalaGame.getGameBoard()[5].getStoneCount(), is(0));
        assertThat(mancalaGame.getGameBoard()[6].getStoneCount(), is(1));
        assertThat(mancalaGame.getGameBoard()[7].getStoneCount(), is(STONE_COUNT + 1));
        assertThat(mancalaGame.getGameBoard()[8].getStoneCount(), is(STONE_COUNT + 1));
        assertThat(mancalaGame.getGameBoard()[9].getStoneCount(), is(STONE_COUNT + 1));
        assertThat(mancalaGame.getGameBoard()[10].getStoneCount(), is(STONE_COUNT + 1));
        assertThat(mancalaGame.getGameBoard()[11].getStoneCount(), is(STONE_COUNT + 1));
        assertThat(mancalaGame.getGameBoard()[12].getStoneCount(), is(STONE_COUNT));

    }

    private MancalaGame startMancalaGame() {
        MancalaGame mancalaGame = new MancalaGame();
        mancalaGame.start();
        return mancalaGame;
    }

    @Test
    public void changeTurn_startWithPlayer1_successfullyChangeToPlayer2() {
        MancalaGame mancalaGame = startMancalaGame();
        mancalaGame.setTurn(1);

        setSpecificBoardForTest(mancalaGame);

        int stones = mancalaGame.pickStonesOfPit(5);

        mancalaGame.addStonesToNextPits(5, stones);

        assertThat(mancalaGame.getTurn(), is(2));
    }

    private void setSpecificBoardForTest(MancalaGame mancalaGame) {
        mancalaGame.getGameBoard()[0].setStoneCount(6);
        mancalaGame.getGameBoard()[1].setStoneCount(5);
        mancalaGame.getGameBoard()[2].setStoneCount(3);
        mancalaGame.getGameBoard()[3].setStoneCount(4);
        mancalaGame.getGameBoard()[4].setStoneCount(0);
        mancalaGame.getGameBoard()[5].setStoneCount(8);
        mancalaGame.getGameBoard()[6].setStoneCount(3);
        mancalaGame.getGameBoard()[7].setStoneCount(9);
        mancalaGame.getGameBoard()[8].setStoneCount(6);
        mancalaGame.getGameBoard()[9].setStoneCount(5);
        mancalaGame.getGameBoard()[10].setStoneCount(4);
        mancalaGame.getGameBoard()[11].setStoneCount(3);
        mancalaGame.getGameBoard()[12].setStoneCount(10);
        mancalaGame.getGameBoard()[13].setStoneCount(6);
    }

    @Test
    public void changeTurn_player1StartsWithPit1_turnShouldNotChange() {
        MancalaGame mancalaGame = startMancalaGame();
        mancalaGame.setTurn(1);

        setSpecificBoardForTest(mancalaGame);

        int stones = mancalaGame.pickStonesOfPit(1);

        mancalaGame.addStonesToNextPits(1, stones);

        assertThat(mancalaGame.getTurn(), is(1));
    }

    @Test
    public void player1PickAPitAndPlay_startWith8StonePitAtIndexOf5_emptyPitAndAddStoneTo8NextPitExceptOpponentBigPit() {
        MancalaGame mancalaGame = startMancalaGame();
        mancalaGame.setTurn(1);

        setSpecificBoardForTest(mancalaGame);

        int stones = mancalaGame.pickStonesOfPit(5);

        mancalaGame.addStonesToNextPits(5, stones);

        assertThat(mancalaGame.getGameBoard()[5].getStoneCount(), is(0));

        assertThat(mancalaGame.getGameBoard()[6].getStoneCount(), is(4));

        assertThat(mancalaGame.getGameBoard()[7].getStoneCount(), is(10));
        assertThat(mancalaGame.getGameBoard()[8].getStoneCount(), is(7));
        assertThat(mancalaGame.getGameBoard()[9].getStoneCount(), is(6));
        assertThat(mancalaGame.getGameBoard()[10].getStoneCount(), is(5));
        assertThat(mancalaGame.getGameBoard()[11].getStoneCount(), is(4));
        assertThat(mancalaGame.getGameBoard()[12].getStoneCount(), is(11));

        assertThat(mancalaGame.getGameBoard()[13].getStoneCount(), is(6));

        assertThat(mancalaGame.getGameBoard()[0].getStoneCount(), is(7));
        assertThat(mancalaGame.getGameBoard()[1].getStoneCount(), is(5));
        assertThat(mancalaGame.getGameBoard()[2].getStoneCount(), is(3));
        assertThat(mancalaGame.getGameBoard()[3].getStoneCount(), is(4));
        assertThat(mancalaGame.getGameBoard()[4].getStoneCount(), is(0));
    }
    
    @Test
    public void investigateEndGameState_player1MovesHisStonesAndEmptyHisSide_gameEnds() {
        MancalaGame mancalaGame = startMancalaGame();

        mancalaGame.getGameBoard()[13].setStoneCount(14);

        mancalaGame.getGameBoard()[0].setStoneCount(0);
        mancalaGame.getGameBoard()[1].setStoneCount(0);
        mancalaGame.getGameBoard()[2].setStoneCount(0);
        mancalaGame.getGameBoard()[3].setStoneCount(0);
        mancalaGame.getGameBoard()[4].setStoneCount(0);
        mancalaGame.getGameBoard()[5].setStoneCount(7);

        mancalaGame.getGameBoard()[6].setStoneCount(20);

        mancalaGame.getGameBoard()[7].setStoneCount(7);
        mancalaGame.getGameBoard()[8].setStoneCount(6);
        mancalaGame.getGameBoard()[9].setStoneCount(5);
        mancalaGame.getGameBoard()[10].setStoneCount(4);
        mancalaGame.getGameBoard()[11].setStoneCount(3);
        mancalaGame.getGameBoard()[12].setStoneCount(6);

        int stones = mancalaGame.pickStonesOfPit(5);

        mancalaGame.addStonesToNextPits(5, stones);

        assertThat(mancalaGame.checkIsGameEnded(), is(equalTo(true)));
    }

    @Test
    public void findWinner_player1IsWinner_successfullyFindWinner(){
        MancalaGame mancalaGame = startMancalaGame();

        mancalaGame.getGameBoard()[13].setStoneCount(14);

        mancalaGame.getGameBoard()[0].setStoneCount(0);
        mancalaGame.getGameBoard()[1].setStoneCount(0);
        mancalaGame.getGameBoard()[2].setStoneCount(0);
        mancalaGame.getGameBoard()[3].setStoneCount(0);
        mancalaGame.getGameBoard()[4].setStoneCount(0);
        mancalaGame.getGameBoard()[5].setStoneCount(7);

        mancalaGame.getGameBoard()[6].setStoneCount(20);

        mancalaGame.getGameBoard()[7].setStoneCount(7);
        mancalaGame.getGameBoard()[8].setStoneCount(6);
        mancalaGame.getGameBoard()[9].setStoneCount(5);
        mancalaGame.getGameBoard()[10].setStoneCount(4);
        mancalaGame.getGameBoard()[11].setStoneCount(3);
        mancalaGame.getGameBoard()[12].setStoneCount(6);

        int stones = mancalaGame.pickStonesOfPit(5);

        mancalaGame.addStonesToNextPits(5, stones);

        assertThat(mancalaGame.checkIsGameEnded(), is(equalTo(true)));
        assertThat(mancalaGame.findTheWinner(), is(equalTo(PLAYER_TWO)));
    }

    @Test
    public void findWinner_gameIsNotStarted_throwsException(){
        MancalaGame mancalaGame = new MancalaGame();

        try {
            mancalaGame.findTheWinner();
            assert false;
        } catch (Exception exp) {
            assertThat(exp, is(instanceOf(GameIsNotStartedException.class)));
            assertThat(exp.getMessage(), is(equalTo(GAME_IS_NOT_STARTED)));
        }
    }

    @Test
    public void captureAllStones_player1putsLastStonesInHisOwnEmptyPit_captureAllStonesOfOpponentAndHimselfInHisOwnBigPit()
            throws InvalidArgumentException {
        MancalaGame mancalaGame = startMancalaGame();

        mancalaGame.getGameBoard()[13].setStoneCount(14);

        mancalaGame.getGameBoard()[0].setStoneCount(3);
        mancalaGame.getGameBoard()[1].setStoneCount(0);
        mancalaGame.getGameBoard()[2].setStoneCount(2);
        mancalaGame.getGameBoard()[3].setStoneCount(0);
        mancalaGame.getGameBoard()[4].setStoneCount(3);
        mancalaGame.getGameBoard()[5].setStoneCount(9);

        mancalaGame.getGameBoard()[6].setStoneCount(10);

        mancalaGame.getGameBoard()[7].setStoneCount(7);
        mancalaGame.getGameBoard()[8].setStoneCount(6);
        mancalaGame.getGameBoard()[9].setStoneCount(5);
        mancalaGame.getGameBoard()[10].setStoneCount(4);
        mancalaGame.getGameBoard()[11].setStoneCount(3);
        mancalaGame.getGameBoard()[12].setStoneCount(6);

        int stones = mancalaGame.pickStonesOfPit(5);

        int lastPlayedIndex = mancalaGame.addStonesToNextPits(5, stones);

        mancalaGame.captureStonesToBigPitInPrise(lastPlayedIndex, 6);

        assertThat(mancalaGame.getGameBoard()[6].getStoneCount(), is(equalTo(16)));
    }

    @Test
    public void captureAllStones_player1putsLastStonesInHisOwnEmptyPit_captureAllStonesOfOpponentAndHimselfInHisOwnPit()
            throws InvalidArgumentException {
        MancalaGame mancalaGame = startMancalaGame();

        mancalaGame.getGameBoard()[13].setStoneCount(14);

        mancalaGame.getGameBoard()[0].setStoneCount(3);
        mancalaGame.getGameBoard()[1].setStoneCount(0);
        mancalaGame.getGameBoard()[2].setStoneCount(2);
        mancalaGame.getGameBoard()[3].setStoneCount(0);
        mancalaGame.getGameBoard()[4].setStoneCount(3);
        mancalaGame.getGameBoard()[5].setStoneCount(9);

        mancalaGame.getGameBoard()[6].setStoneCount(10);

        mancalaGame.getGameBoard()[7].setStoneCount(7);
        mancalaGame.getGameBoard()[8].setStoneCount(6);
        mancalaGame.getGameBoard()[9].setStoneCount(5);
        mancalaGame.getGameBoard()[10].setStoneCount(4);
        mancalaGame.getGameBoard()[11].setStoneCount(3);
        mancalaGame.getGameBoard()[12].setStoneCount(6);

        int stones = mancalaGame.pickStonesOfPit(5);

        int lastPlayedIndex = mancalaGame.addStonesToNextPits(5, stones);

        mancalaGame.captureStonesToPlayerOwnLastPlayedIndexInPrise(lastPlayedIndex);

        assertThat(mancalaGame.getGameBoard()[1].getStoneCount(), is(equalTo(5)));
    }
}
