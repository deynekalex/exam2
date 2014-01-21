package com.example.exam2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * User: Xottab
 * Date: 14.01.14
 */
public class General {
    public static final String PREFERENCES = "CarWashPrefs";
    public static final String NAME = "CarWashName";
    public static final String BOX_NUMBER = "CarWashBoxNumber";
    public static String REAL_NAME = null;
    public static int NUMBER_OF_BOXES = 0;
    public static HashMap<Integer, ArrayList<Integer>> freeTime = new LinkedHashMap<Integer, ArrayList<Integer>>();

    public static String formatTime(int time) {
        StringBuilder builder = new StringBuilder();
        int hours = time / 2 + 10;
        String mins = time % 2 == 1 ? "30" : "00";
        builder.append(hours < 12 ? hours : hours);
        builder.append(":").append(mins);
        return builder.toString();
    }
}