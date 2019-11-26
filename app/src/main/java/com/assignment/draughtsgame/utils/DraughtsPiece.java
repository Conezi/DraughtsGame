package com.assignment.draughtsgame.utils;

public class DraughtsPiece {
    boolean crowned;
    boolean captured;
    boolean dark;
    DraughtsPosition position = new DraughtsPosition();

    private static DraughtsPosition[] push_back(DraughtsPosition[] array, DraughtsPosition push) {
        int count = 0;
        for (DraughtsPosition ignored : array) {
            count++;
        }
        DraughtsPosition[] longer = new DraughtsPosition[count + 1];
        if (count >= 0) System.arraycopy(array, 0, longer, 0, count);
        longer[count] = push;
        return longer;
    }

    public String toString() {
        String str;
        if (!this.crowned) {
            if (!this.dark) {
                str = "w";
            } else {
                str = "b";
            }
        } else {
            if (!this.dark) {
                str = "W";
            } else {
                str = "B";
            }
        }
        return str;

    }

    public DraughtsPiece() {
        crowned = false;
        captured = false;
        dark = false;
        position.row = 0;
        position.col = 0;
    }

    void piece_print() {
        if (!this.crowned) {
            if (!this.dark) {
                System.out.print("w");
            } else {
                System.out.print("b");
            }
        } else {
            if (!this.dark) {
                System.out.print("W");
            } else {
                System.out.print("B");
            }
        }
    }

    DraughtsPosition[]
    piece_positions_in_direction(int dir) {
        DraughtsPiece cp1 = new DraughtsPiece();
        DraughtsPiece cp2 = new DraughtsPiece();
        // cp.dark = false;
        DraughtsPosition[] v_out_pos = new DraughtsPosition[0];
        boolean dark = this.dark;
        boolean crowned = this.crowned;
        boolean pass = false;

        if (crowned) {
            pass = true;
        } else {
            if (dir == 0 && dark) {
                pass = true;
            } else if (dir == 1 && dark) {
                pass = true;
            } else if (dir == 2 && !dark) {
                pass = true;
            } else if (dir == 3 && !dark) {
                pass = true;
            }
        }

        if (pass) {
            if (dir == 0) {
                // north east
                cp1.position.row = this.position.row - 1;
                cp1.position.col = this.position.col + 1;
                cp2.position.row = this.position.row - 1 - 1;
                cp2.position.col = this.position.col + 1 + 1;
            } else if (dir == 1) {
                // north west
                cp1.position.row = this.position.row - 1;
                cp1.position.col = this.position.col - 1;
                cp2.position.row = this.position.row - 1 - 1;
                cp2.position.col = this.position.col - 1 - 1;
            } else if (dir == 2) {
                // south west
                cp1.position.row = this.position.row + 1;
                cp1.position.col = this.position.col - 1;
                cp2.position.row = this.position.row + 1 + 1;
                cp2.position.col = this.position.col - 1 - 1;
            } else if (dir == 3) {
                // south east
                cp1.position.row = this.position.row + 1;
                cp1.position.col = this.position.col + 1;
                cp2.position.row = this.position.row + 1 + 1;
                cp2.position.col = this.position.col + 1 + 1;
            }
        }

        // are they within board?
        if (cp1.position.row <= 7 && cp1.position.col <= 7) {
            if (cp1.position.row >= 0 && cp1.position.col >= 0) {
                v_out_pos = push_back(v_out_pos, cp1.position);
            }
        }

        if (cp2.position.row <= 7 && cp2.position.col <= 7) {
            if (cp2.position.row >= 0 && cp2.position.col >= 0) {
                v_out_pos = push_back(v_out_pos, cp2.position);
            }
        }
        return v_out_pos;
    }
}
