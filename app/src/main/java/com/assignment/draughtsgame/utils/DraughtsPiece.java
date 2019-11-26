package com.assignment.draughtsgame.utils;

public class DraughtsPiece {
    boolean dark;
    DraughtsPosition position = new DraughtsPosition();

    public String toString() {
        String str;
            if (!this.dark) {
                str = "w";
            } else {
                str = "b";
            }
        return str;

    }

    public DraughtsPiece() {
        dark = false;
        position.row = 0;
        position.col = 0;
    }
}
