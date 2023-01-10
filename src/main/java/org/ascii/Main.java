package org.ascii;

import org.ascii.chess.board.Game;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp;

import java.io.IOException;

public class Main {
    static String TITLE = """
                        
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

        try (Terminal terminal = TerminalBuilder.terminal()) {
            while (s.length() <= 80) {
                terminal.puts(InfoCmp.Capability.clear_screen);
                System.out.println(TITLE.indent(30));
                s.insert(0, "=");
                System.out.println(s.toString().indent(25));
                Thread.sleep(16);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}