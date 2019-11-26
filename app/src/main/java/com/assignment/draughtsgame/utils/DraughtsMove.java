package com.assignment.draughtsgame.utils;

public class DraughtsMove {
    SimpleMove[] simple_moves = new SimpleMove[0];

    // need to show this function to sir

    private static String[] push_back(String[] array, String push) {
        String[] longer = new String[array.length + 1];
        System.arraycopy(array, 0, longer, 0, array.length);
        longer[array.length] = push;
        return longer;
    }

    private static SimpleMove[] push_back(SimpleMove[] array, SimpleMove push) {
        int count = 0;
        for (SimpleMove ignored : array) {
            count++;
        }
        SimpleMove[] longer = new SimpleMove[count + 1];
        if (count >= 0) System.arraycopy(array, 0, longer, 0, count);
        longer[count] = push;
        return longer;
    }

    // --
    private String[] string_split(String st1, char c) {
        StringBuilder st_temp = new StringBuilder();
        String st_blank = "";
        String[] v = new String[0];
        for (int i = 0; i < st1.length(); i++) {
            if (st1.charAt(i) != c) {
                st_temp.append(st1.charAt(i));
            } else {
                v = push_back(v, st_temp.toString());
                st_temp = new StringBuilder(st_blank);
            }
        }
        v = push_back(v, st_temp.toString());
        return v;
    }

    private void move_parse(String st2) {
        // splits the input string
        char splitter = '-';

        // stores the splitted strings in vmove vector
        String[] vmove  = string_split(st2, splitter);

        int count = 0;
        for (String ignored : vmove) {
            count = count + 1;
        }

        DraughtsPosition[] cp = new DraughtsPosition[count];
        for (int i = 0; i < count; i++) {
            cp[i] = new DraughtsPosition();
        }
        int a = 0;
        for (DraughtsPosition c : cp) {
            c.position_parse(vmove[a]);
            a = a + 1;
        }
        a = 0;

        int count_sm = count - 1;

        SimpleMove[] sm = new SimpleMove[count_sm];
        for (int i = 0; i < count_sm; i++) {
            sm[i] = new SimpleMove();
        }
        for (SimpleMove d : sm) {
            d.start.row = cp[a].row;
            d.start.col = cp[a].col;
            d.end.row = cp[a+1].row;
            d.end.col = cp[a+1].col;
            a = a + 1;
        }

        for (SimpleMove d : sm) {
            simple_moves = push_back(simple_moves, d);
        }

    }

    public DraughtsMove(String st2) {
        this.move_parse(st2);
    }
}
