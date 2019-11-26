package com.assignment.draughtsgame.utils;



public class DraughtsBoard {
    private DraughtsPiece[] light_pieces = new DraughtsPiece[12];
    private DraughtsPiece[] dark_pieces = new DraughtsPiece[12];
    private DraughtsPiece none_piece = new DraughtsPiece();
    private int index;
    public int turn;
    public boolean playAgain=false;

    public void setPlayAgain(boolean playAgain) {
        this.playAgain = playAgain;
    }

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
        turn = 1;
    }

    public DraughtsPiece board_get_piece_at(int r, int c) {
        for (int i = 0; i < 12; i++) {
            if (light_pieces[i].position.col == c && light_pieces[i].position.row == r) {
                index=i;
                return light_pieces[i];
            }
        }
        for (int i = 0; i < 12; i++) {
            if (dark_pieces[i].position.col == c && dark_pieces[i].position.row == r) {
                index=i;
                return dark_pieces[i];
            }
        }
        return none_piece;
    }

    public  void board_print() {
        for (int r = 0; r < 8; r = r + 1) {
            System.out.print((8 - r) + (" "));
            for (int a = 0; a <= 7; a = a + 1) {
                if (board_get_piece_at(r, a).position.col ==
                        -1 || board_get_piece_at(r, a).captured) {
                    System.out.print(".");
                } else {
                    (board_get_piece_at(r, a)).piece_print();
                }
            }
            System.out.println("\n");
        }
        System.out.print("  ABCDEFGH");
    }

    public String[] vec_string() {
        String[] str = new String[64];
        for (int r = 0; r < 8; r = r + 1) {
            for (int a = 0; a < 8; a = a + 1) {
                if (board_get_piece_at(r, a).position.col ==
                        -1 || board_get_piece_at(r, a).captured) {
                    str[(r*8)+a] = ".";
                } else {
                    str[(r*8)+a] = (board_get_piece_at(r, a)).toString();
                }
            }
        }
        return str;
    }

    public int board_move_and_capture(DraughtsMove dm) {
        if (this.validate(dm) == 0) {
            int ro1 = dm.simple_moves[0].start.row;
            int co1 = dm.simple_moves[0].start.col;
            new DraughtsPiece();
            DraughtsPiece p;
            p = board_get_piece_at(ro1, co1);
            boolean cul;
            cul = p.dark;
            int count = 0;
            for (SimpleMove n : dm.simple_moves) {
                count = count + 1;
                n.start.row = n.start.row + 0;
            }
            int i = 0;
            for (SimpleMove n : dm.simple_moves) {
                n.start.row = n.start.row + 0;
                // start and end points of this move
                int ro = dm.simple_moves[i].start.row;
                int co = dm.simple_moves[i].start.col;
                int rn = dm.simple_moves[i].end.row;
                int cn = dm.simple_moves[i].end.col;
                // draught piece at the start point, the one which is being moved

                int dir = 0;
                // find out which direction piece went

                if (rn < ro && cn > co) {
                    dir = 0;
                } else if (rn < ro && co > cn) {
                    dir = 1;
                } else if (ro < rn && co > cn) {
                    dir = 2;
                } else if (ro < rn && cn > co) {
                    dir = 3;
                }
                // for the direction it went to, check the box diagonally behind it
                // if it is of the other color, and was filled before, then make it
                // into a none piece
                if (dir == 0) {
                    for (int k = 0; k < 12; k ++) {
                        if (rn + 1 == dark_pieces[k].position.row && cn - 1 == dark_pieces[k].position.col
                                && dark_pieces[k].dark != cul) {
                            playAgain=(doubleMove(dark_pieces,rn-1, cn+1, rn-2, cn+2 )||
                                    doubleMove(dark_pieces,rn-1, cn-1, rn-2, cn-2 ));
                            dark_pieces[k] = none_piece;
                            dark_pieces[k].position.row = -1;
                            dark_pieces[k].position.col = -1;
                            dark_pieces[k].captured = true;
                        }
                    }
                    for (int k = 0; k < 12; k ++) {
                        if (rn + 1 == light_pieces[k].position.row && cn - 1 == light_pieces[k].position.col
                                && light_pieces[k].dark != cul) {
                            playAgain=(doubleMove(light_pieces,rn-1, cn+1, rn-2, cn+2 )||
                                    doubleMove(light_pieces,rn-1, cn-1, rn-2, cn-2 ));

                            light_pieces[k] = none_piece;
                            light_pieces[k].position.row = -1;
                            light_pieces[k].position.col = -1;
                            light_pieces[k].captured = true;
                        }
                    }
                } else if (dir == 1) {
                    for (int k = 0; k < 12; k ++) {
                        if (rn + 1 == dark_pieces[k].position.row && cn + 1 == dark_pieces[k].position.col
                                && dark_pieces[k].dark != cul) {
                            playAgain=doubleMove(dark_pieces, rn-1, cn-1, rn-2, cn-2)||
                                    doubleMove(dark_pieces,rn-1, cn+1, rn-2, cn+2 );
                            dark_pieces[k] = none_piece;
                            dark_pieces[k].position.row = -1;
                            dark_pieces[k].position.col = -1;
                            dark_pieces[k].captured = true;
                        }
                    }
                    for (int k = 0; k < 12; k ++) {
                        if (rn + 1 == light_pieces[k].position.row && cn + 1 == light_pieces[k].position.col
                                && light_pieces[k].dark != cul) {
                            playAgain=(doubleMove(light_pieces, rn-1, cn-1, rn-2, cn-2)||
                                    doubleMove(light_pieces,rn-1, cn+1, rn-2, cn+2 ));
                            light_pieces[k] = none_piece;
                            light_pieces[k].position.row = -1;
                            light_pieces[k].position.col = -1;
                            light_pieces[k].captured = true;
                        }
                    }
                } else if (dir == 2) {
                    for (int k = 0; k < 12; k ++) {
                        if (rn - 1 == dark_pieces[k].position.row && cn + 1 == dark_pieces[k].position.col
                                && dark_pieces[k].dark != cul) {
                            playAgain=doubleMove(dark_pieces, rn+1, cn-1, rn+2, cn-2)||
                                    doubleMove(dark_pieces, rn+1, cn+1, rn+2, cn+2);
                            dark_pieces[k] = none_piece;
                            dark_pieces[k].position.row = -1;
                            dark_pieces[k].position.col = -1;
                            dark_pieces[k].captured = true;
                        }
                    }
                    for (int k = 0; k < 12; k ++) {
                        if (rn - 1 == light_pieces[k].position.row && cn + 1 == light_pieces[k].position.col
                                && light_pieces[k].dark != cul) {
                            playAgain=(doubleMove(light_pieces, rn+1, cn-1, rn+2, cn-2)||
                                    doubleMove(light_pieces, rn+1, cn+1, rn+2, cn+2));
                            light_pieces[k] = none_piece;
                            light_pieces[k].position.row = -1;
                            light_pieces[k].position.col = -1;
                            light_pieces[k].captured = true;
                        }
                    }
                } else {
                    for (int k = 0; k < 12; k ++) {
                        if (rn - 1 == dark_pieces[k].position.row && cn - 1 == dark_pieces[k].position.col
                                && dark_pieces[k].dark != cul) {
                            playAgain=doubleMove(dark_pieces, rn+1, cn+1, rn+2, cn+2)||
                                    doubleMove(dark_pieces, rn+1, cn-1, rn+2, cn-2);
                            dark_pieces[k] = none_piece;
                            dark_pieces[k].position.row = -1;
                            dark_pieces[k].position.col = -1;
                            dark_pieces[k].captured = true;
                        }
                    }
                    for (int k = 0; k < 12; k ++) {
                        if (rn - 1 == light_pieces[k].position.row && cn - 1 == light_pieces[k].position.col
                                && light_pieces[k].dark != cul) {
                            playAgain=(doubleMove(light_pieces, rn+1, cn+1, rn+2, cn+2)||
                                    doubleMove(light_pieces, rn+1, cn-1, rn+2, cn-2));
                            light_pieces[k] = none_piece;
                            light_pieces[k].position.row = -1;
                            light_pieces[k].position.col = -1;
                            light_pieces[k].captured = true;
                        }
                    }
                }
                board_move(dm);
                if(!playAgain) {
                    turn = turn * -1;
                }
                i = i + 1;
            }
        } else {
            System.out.println("Invalid Move");
            return -1;
        }
        return 1;
    }

    private boolean doubleMove(DraughtsPiece[] pieces, int row1, int col1, int row2, int col2){
        return (board_get_piece_at(row1, col1)==pieces[index]&&board_get_piece_at(row2, col2)==none_piece)&&(row2>=0&&row2<=7&&col2>=0&&col2<=7);
    }


    private void board_move(DraughtsMove dm) {
        DraughtsPiece dp = new DraughtsPiece();
        dp.position = dm.simple_moves[0].start;
        int r_old = dp.position.row;
        int c_old = dp.position.col;
        for (SimpleMove smm : dm.simple_moves) {
            dp.position = smm.end;
        }
        int r_new = dp.position.row;
        int c_new = dp.position.col;

        for (int k = 0; k < 12; k ++) {
            if (r_old == dark_pieces[k].position.row && c_old == dark_pieces[k].position.col) {
                dark_pieces[k].position.row = r_new;
                dark_pieces[k].position.col = c_new;
                if (r_new == 0) {
                    dark_pieces[k].crowned = true;
                }
            }
        }

        for (int k = 0; k < 12; k ++) {
            if (r_old == light_pieces[k].position.row && c_old == light_pieces[k].position.col) {
                light_pieces[k].position.row = r_new;
                light_pieces[k].position.col = c_new;
                if (r_new == 7) {
                    light_pieces[k].crowned = true;
                }
            }
        }
    }

    public int winner() {
        int count_dark_dead = 0;
        int count_light_dead = 0;
        int win = 0;
        for (DraughtsPiece x : dark_pieces) {
            if (x.position.row == -1 || x.captured ||
                    x.position.col == -1) {
                count_dark_dead++;
            }
        }
        for (DraughtsPiece x : light_pieces) {
            if (x.position.row == -1 || x.captured ||
                    x.position.col == -1) {
                count_light_dead++;
            }
        }
        if (count_dark_dead == 12) {
            win = 2;
        }
        if (count_light_dead == 12) {
            win = 1;
        }

        return win;
    }

    private int validate(DraughtsMove dm) {
        int ro = dm.simple_moves[0].start.row;
        int co = dm.simple_moves[0].start.col;
        int rn = dm.simple_moves[0].end.row;
        int cn = dm.simple_moves[0].end.col;
        boolean cull = (board_get_piece_at(ro, co)).dark;


        if (winner() != 0) {
            return 1;
        }

        int junk = 0;
        if (!cull && turn == 1) {
            junk++;
        } else if (cull && turn == -1) {
            junk++;
        } else {
            return 2;
        }

        if (board_get_piece_at(ro, co).position.row == -1) {
            return 3;
        }

        if (board_get_piece_at(rn, cn).position.row != -1) {
            return 4;
        }

        int dif_r = ro - rn;
        DraughtsPiece p = board_get_piece_at(ro, co);
        boolean cul = p.dark;
        int check_dark = 0;
        int check_light = 0;
        int a = 0;
        int dir = 0;
        if (rn < ro && cn > co) {
            dir = 0;
        } else if (rn < ro && co > cn) {
            dir = 1;
        } else if (ro < rn && co > cn) {
            dir = 2;
        } else if (ro < rn && cn > co) {
            dir = 3;
        }

        // return 5
        if (dif_r != 1 && dif_r != -1) {
            if (dir == 0) {
                for (DraughtsPiece dp : dark_pieces) {
                    if (rn + 1 == dp.position.row && cn - 1 == dp.position.col
                            && dp.dark == cul) {
                        a = 1;
                    }
                    check_dark++;
                }
                for (DraughtsPiece dp : light_pieces) {
                    if (rn + 1 == dp.position.row && cn - 1 == dp.position.col
                            && dp.dark == cul) {
                        a = 1;
                    }
                    check_light++;
                }
            } else if (dir == 1) {
                for (DraughtsPiece dp : dark_pieces) {
                    if (rn + 1 == dp.position.row && cn + 1 == dp.position.col
                            && dp.dark == cul) {
                        a = 1;
                    }
                    check_dark++;
                }
                for (DraughtsPiece dp : light_pieces) {
                    if (rn + 1 == dp.position.row && cn + 1 == dp.position.col
                            && dp.dark == cul) {
                        a = 1;
                    }
                    check_light++;
                }
            } else if (dir == 2) {
                for (DraughtsPiece dp : dark_pieces) {
                    if (rn - 1 == dp.position.row && cn + 1 == dp.position.col
                            && dp.dark == cul) {
                        a = 1;
                    }
                    check_dark++;
                }
                for (DraughtsPiece dp : light_pieces) {
                    if (rn - 1 == dp.position.row && cn + 1 == dp.position.col
                            && dp.dark == cul) {
                        a = 1;
                    }
                    check_light++;
                }
            } else {
                for (DraughtsPiece dp : dark_pieces) {
                    if (rn - 1 == dp.position.row && cn - 1 == dp.position.col
                            && dp.dark == cul) {
                        a = 1;
                    }
                    check_dark++;
                }
                for (DraughtsPiece dp : light_pieces) {
                    if (rn - 1 == dp.position.row && cn - 1 == dp.position.col
                            && dp.dark == cul) {
                        a = 1;
                    }
                    check_light++;
                }
            }
            if (check_light == 12 || check_dark == 12) {
                if (a == 1) {
                    return 5;
                }
            }
            if (dir == 0) {
                if (board_get_piece_at(rn + 1, cn - 1).position.row == -1) {
                    a = 1;
                }
            } else if (dir == 1) {
                if (board_get_piece_at(rn + 1, cn + 1).position.row == -1) {
                    a = 1;
                }
            } else if (dir == 2) {
                if (board_get_piece_at(rn - 1, cn + 1).position.row == -1) {
                    a = 1;
                }
            } else {
                if (board_get_piece_at(rn - 1, cn - 1).position.row == -1) {
                    a = 1;
                }
            }
            if (a == 1) {
                return 5;
            }
        }
        // return 6

        DraughtsPosition[] pp = p.piece_positions_in_direction(dir);
        int t = 0;
        if (!p.crowned) {
            if (!cull) {
                if (dir == 0 || dir == 1) {
                    return 6;
                }
            } else {
                if (dir == 2 || dir == 3) {
                    return 6;
                }
            }
        }
        for (DraughtsPosition x : pp) {
            if (x.row == rn && x.col == cn) {
                t = 1;
            }
        }
        if (t == 0) {
            return 6;
        }
        return 0;
    }
}