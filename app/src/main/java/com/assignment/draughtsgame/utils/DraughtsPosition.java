package com.assignment.draughtsgame.utils;

public class DraughtsPosition {
    public int row;
    public int col;

    public DraughtsPosition() {
        row = 0;
        col = 0;
    }

    public void position_parse(String st) {
        row = 8 - (st.charAt(1) - '0');
        col = st.charAt(0) - 'A';
    }
}

