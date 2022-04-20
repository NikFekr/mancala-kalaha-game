package com.example.mancalagame.controllers;

import com.example.mancalagame.servises.MancalaGame;
import com.example.mancalagame.models.Pit;
import com.sun.javaws.exceptions.InvalidArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Esmaeil NikFekr on 10/28/20.
 */
@RestController
public class GameController {

    @Autowired
    private MancalaGame mancalaGame;

    @GetMapping("/startGame")
    public void startGame() {
        mancalaGame.start();
    }

    @GetMapping("/getGameBoard")
    public Pit[] getGameBoard() {
        return mancalaGame.getGameBoard();
    }

    @PostMapping("/playGame")
    public Pit[] playGame(@RequestParam int pitNumber) {
        pitNumber--;
        int stones = mancalaGame.pickStonesOfPit(pitNumber);
        mancalaGame.addStonesToNextPits(pitNumber, stones);
        return mancalaGame.getGameBoard();
    }

    @GetMapping("/findWinner")
    public String findWinner() {
        return mancalaGame.findTheWinner();
    }

    @GetMapping("/captureStonesToBigPitInPrise")
    public void captureStonesToBigPitInPrise(@RequestParam int pitNumber, @RequestParam int bigPitNumber)
            throws InvalidArgumentException {

        mancalaGame.captureStonesToBigPitInPrise(--pitNumber, --bigPitNumber);
    }

    @GetMapping("/captureStonesToPlayerOwnLastPlayedIndexInPrise")
    public void captureStonesToPlayerOwnLastPlayedIndexInPrise(@RequestParam int pitNumber)
            throws InvalidArgumentException {

        mancalaGame.captureStonesToPlayerOwnLastPlayedIndexInPrise(--pitNumber);
    }


}
