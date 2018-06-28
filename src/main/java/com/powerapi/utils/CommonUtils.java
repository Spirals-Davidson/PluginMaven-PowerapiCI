package com.powerapi.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommonUtils {

    public static String readProcessus(Process p) throws IOException {
        String back = "";

        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            back += line +"\n";
        }
        reader.close();

        return back;
    }
}
