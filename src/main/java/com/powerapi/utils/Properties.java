package com.powerapi.utils;

public class Properties {
    private static Integer frequency;
    private static String esUrl;

    public static void setFrequency(Integer frequency){
        Properties.frequency = frequency;
    }

    public static void setEsUrl(String esUrl){
        Properties.esUrl = esUrl;
    }

    public static Integer getFrequency(){
        return frequency;
    }

    public static String getEsUrl(){
        return esUrl;
    }
}
