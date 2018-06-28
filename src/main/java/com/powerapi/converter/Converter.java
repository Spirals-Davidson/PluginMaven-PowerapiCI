package com.powerapi.converter;

import com.google.gson.Gson;
import com.powerapi.Entity.ResultatApplication;

public class Converter {

    public static String resultatApplicationToJson(ResultatApplication resultatApplication) {
        return new Gson().toJson(resultatApplication) + "\n";
    }
}
