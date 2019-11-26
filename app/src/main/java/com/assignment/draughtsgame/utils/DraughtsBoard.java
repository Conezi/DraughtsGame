package com.assignment.draughtsgame.utils;



public class DraughtsBoard {
    private DraughtsPiece[] light_pieces = new DraughtsPiece[12];
    private DraughtsPiece[] dark_pieces = new DraughtsPiece[12];
    private DraughtsPiece none_piece = new DraughtsPiece();

    public DraughtsBoard() {
        int a = 0;
        int odds = 1;
        int evens = 0;
        none_piece.position.row = -1;
        none_piece.position.col = -1;

        for (int i = 0; i < 12; i++) {
            DraughtsPiece dp = new DraughtsPiece();
            a = a + 1;
            if (a <= 4) {
                dp.position.row = 0;
                dp.position.col = odds;
                odds = odds + 2;
            } else if (a > 4 && a <= 8) {
                odds = 1;
                dp.position.row = 1;
                dp.position.col = evens;
                evens = evens + 2;
            } else {
                dp.position.row = 2;
                dp.position.col = odds;
                odds = odds + 2;
            }
            light_pieces[i] = dp;
        }

        a = 0;
        odds = 1;
        evens = 0;
        for (int i = 0; i < 12; i++) {
            DraughtsPiece dp = new DraughtsPiece();
            a++;
            if (a <= 4) {
                dp.position.row = 5;
                dp.position.col = evens;
                dp.dark = true;
                evens = evens + 2;
            } else if (a > 4 && a <= 8) {
                evens = 0;
                dp.position.row = 6;
                dp.position.col = odds;
                dp.dark = true;
                odds = odds + 2;
            } else {
                dp.position.row = 7;
                dp.position.col = evens;
                dp.dark = true;
                evens = evens + 2;
            }
            dark_pieces[i] = dp;
        }
    }

    public DraughtsPiece board_get_piece_at(int r, int c) {
        for (int i = 0; i < 12; i++) {
            if (light_pieces[i].position.col == c && light_pieces[i].position.row == r) {
                return light_pieces[i];
            }
        }
        for (int i = 0; i < 12; i++) {
            if (dark_pieces[i].position.col == c && dark_pieces[i].position.row == r) {
                return dark_pieces[i];
            }
        }
        return none_piece;
    }

    public String[] vec_string() {
        String[] str = new String[64];
        for (int r = 0; r < 8; r = r + 1) {
            for (int a = 0; a < 8; a = a + 1) {
                if (board_get_piece_at(r, a).position.col ==
                        -1) {
                    str[(r*8)+a] = ".";
                } else {
                    str[(r*8)+a] = (board_get_piece_at(r, a)).toString();
                }
            }
        }
        return str;
    }
}