package org.ascii.chess.util;

public class Test {
    public static void main(String[] args) {
        String st, code;
        int k = 0;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                code = "" + (i * 16 + j);
                st = "\u001b[38;5;" + code + "m";
                System.out.print(adjust_space(st + k + "\u001b[0m" + "  ",code));
                k++;
            }
            System.out.println();
        }
    }

    private static String adjust_space(String s,String s1) {
        return (s+" ".repeat(3-s1.length()));
    }
}
