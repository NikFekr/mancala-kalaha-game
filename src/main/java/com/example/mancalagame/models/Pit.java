package com.example.mancalagame.models;

import com.example.mancalagame.enums.PitType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pit {
    private int stoneCount;
    private String pitName;
    private PitType pitType;
}
