package org.ascii.chess.util;

public class Text {
    public static String getColorText(String text, Colors colors){
        return colors.getColor()+text+"\33[0m";
    }
}
