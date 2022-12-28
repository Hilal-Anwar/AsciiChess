package org.ascii;

import org.ascii.chess.Game;
import org.jline.utils.InfoCmp;

public class Main {
    static String TITLE= """
            
            ░█████╗░░██████╗░█████╗░██╗██╗  ░█████╗░██╗░░██╗███████╗░██████╗░██████╗
            ██╔══██╗██╔════╝██╔══██╗██║██║  ██╔══██╗██║░░██║██╔════╝██╔════╝██╔════╝
            ███████║╚█████╗░██║░░╚═╝██║██║  ██║░░╚═╝███████║█████╗░░╚█████╗░╚█████╗░
            ██╔══██║░╚═══██╗██║░░██╗██║██║  ██║░░██╗██╔══██║██╔══╝░░░╚═══██╗░╚═══██╗
            ██║░░██║██████╔╝╚█████╔╝██║██║  ╚█████╔╝██║░░██║███████╗██████╔╝██████╔╝
            ╚═╝░░╚═╝╚═════╝░░╚════╝░╚═╝╚═╝  ░╚════╝░╚═╝░░╚═╝╚══════╝╚═════╝░╚═════╝░
            """;
    public static void main(String[] args) throws InterruptedException {
        loading(new StringBuilder("=>"));
        Game game = new Game();
        game.start();
    }
    private static void loading(StringBuilder s) throws InterruptedException {
        while (s.length() <= 45) {
            System.out.println(TITLE.indent(30));
            s.insert(0, "=");
            System.out.println(s.toString().indent(25));
            Thread.sleep(50);
            System.out.println("\033[H\033[J");
        }
    }
}