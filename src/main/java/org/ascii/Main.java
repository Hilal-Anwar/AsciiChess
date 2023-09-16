package org.ascii;

import org.ascii.chess.board.Game;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp;

import java.io.IOException;

public class  Main {
    static String TITLE1 = """
            ░█████╗░░██████╗░█████╗░██╗██╗
            ██╔══██╗██╔════╝██╔══██╗██║██║
            ███████║╚█████╗░██║░░╚═╝██║██║
            ██╔══██║░╚═══██╗██║░░██╗██║██║
            ██║░░██║██████╔╝╚█████╔╝██║██║
            ╚═╝░░╚═╝╚═════╝░░╚════╝░╚═╝╚═╝
            """;
    static String TITLE2= """                 
            ░█████╗░██╗░░██╗███████╗░██████╗░██████╗
            ██╔══██╗██║░░██║██╔════╝██╔════╝██╔════╝
            ██║░░╚═╝███████║█████╗░░╚█████╗░╚█████╗░
            ██║░░██╗██╔══██║██╔══╝░░░╚═══██╗░╚═══██╗
            ╚█████╔╝██║░░██║███████╗██████╔╝██████╔╝
            ░╚════╝░╚═╝░░╚═╝╚══════╝╚═════╝░╚═════╝░
            """;


    public static void main(String[] args) throws InterruptedException {
        loading(new StringBuilder("=>"));
        Game game = new Game();
        game.start();
    }

    private static void loading(StringBuilder s) throws InterruptedException {

        try (Terminal terminal = TerminalBuilder.terminal()) {
            int w1=terminal.getSize().getColumns()/2-15;
            int w2=terminal.getSize().getColumns()/2-20;
            int h=terminal.getSize().getRows()/2;
            while (s.length() <= 40) {
                //terminal.puts(InfoCmp.Capability.clear_screen);
                System.out.println("\u001b[H");
                System.out.println("\n".repeat(h-12));
                System.out.println(TITLE1.indent(w1));
                System.out.println(TITLE2.indent(w2));
                s.insert(0, "=");
                System.out.println(s.toString().indent(w2));
                Thread.sleep(16);
            }
            terminal.puts(InfoCmp.Capability.clear_screen);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
