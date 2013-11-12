package com.github.mccxj.go;

import java.io.Serializable;

public class GameConstants implements Serializable {
    public static final String ARR = "abcdefghijklmnopqrs";
    public static final int SIZE = 19;
    public static final int MARK = 5;
    public static final int EMPTY = 0;
    public static final int BLACK = 1;
    public static final int WHITE = 2;
    
    public static final int BLACK_PASS = -1;
    public static final int WHITE_PASS = -2;
    public static final int BLACK_OFFSET = 0;
    public static final int WHITE_OFFSET = 1000;

    public static final int SUCC = -16;
    public static final int INVALID = -32;
    public static final int FAIL = -64;
}
