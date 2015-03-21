package com.emilochhektor.quizcous.util;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by Hektor on 2015-03-21.
 */
public class QuizcousColors {
    private QuizcousColors() {}

    public final static int Red = Color.parseColor("#f44336");
    public final static int Pink = Color.parseColor("#e91e63");
    public final static int Purple = Color.parseColor("#9c27b0");
    public final static int DeepPurple = Color.parseColor("#673ab7");
    public final static int Indigo = Color.parseColor("#3f51b5");
    public final static int Blue = Color.parseColor("#2196f3");
    public final static int LightBlue = Color.parseColor("#03a9f4");
    public final static int Orange = Color.parseColor("#ff9800");
    public final static int DeepOrange = Color.parseColor("#ff5722");
    public final static int Yellow = Color.parseColor("#ffeb3b");

    private final static int[] Colors = {
        QuizcousColors.Red,
        QuizcousColors.Pink,
        QuizcousColors.Purple,
        QuizcousColors.DeepPurple,
        QuizcousColors.Indigo,
        QuizcousColors.Blue,
        QuizcousColors.LightBlue,
        QuizcousColors.Orange,
        QuizcousColors.DeepOrange,
        QuizcousColors.Yellow
    };

    public static int getNextColorIndex(int index, int direction) {
        return (QuizcousColors.Colors.length + index + direction) % QuizcousColors.Colors.length;
    }
    public static int getColorByIndex(int index) {
        return QuizcousColors.Colors[index];
    }

    public static int randomColor() {
        Random rand = new Random();
        return QuizcousColors.Colors[rand.nextInt(QuizcousColors.Colors.length - 1)];
    }

    public static String intColorToHex(int color) {
        return String.format("#%06X", (0xFFFFFF & color));
    }
}
