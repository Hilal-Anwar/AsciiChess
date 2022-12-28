package org.ascii.chess.util;

public enum Colors {
    RED("\033[0;31m"),   // RED
    GREEN("\033[0;32m"),   // GREEN
    YELLOW("\033[0;33m"),  // YELLOW
    BLUE("\033[0;34m"),    // BLUE
    PURPLE("\033[0;35m"),  // PURPLE
    CYAN("\033[0;36m"),  // CYAN
    WHITE("\033[0;97m"),  // WHITE
    CYAN_BRIGHT("\033[0;96m"),
    MAGENTA("\u001B[35m"),
    ORANGE("\u001b[38;5;208m");
    private final String color;

    Colors(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

}
