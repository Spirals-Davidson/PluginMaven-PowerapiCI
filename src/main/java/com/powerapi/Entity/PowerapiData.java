package com.powerapi.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PowerapiData implements Comparable {
    private String muid;
    private String devices;
    private String targets;
    private long timestamp;
    private Double power;

    public int compareTo(Object powerapiData) {
        return ((int) (timestamp - ((PowerapiData) powerapiData).timestamp));
    }

    public PowerapiData(String powerapiDataCSV) {
        String[] parsingCSV = powerapiDataCSV.split(";");
        for (String st : parsingCSV) {
            String[] secParsing = st.split("=");

            switch(secParsing[0]){
                case "muid":
                    muid = secParsing[1];
                    break;
                case "devices":
                    devices = secParsing[1];
                    break;
                case "targets":
                    targets = secParsing[1];
                    break;
                case "timestamp":
                    timestamp = Long.parseLong(secParsing[1]);
                    break;
                case "power":
                    power = Double.parseDouble(secParsing[1]);
                    break;
            }
        }

    }
}
