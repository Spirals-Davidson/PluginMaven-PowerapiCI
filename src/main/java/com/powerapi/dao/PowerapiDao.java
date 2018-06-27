package com.powerapi.dao;


import com.powerapi.enums.HttpMethod;
import com.powerapi.utils.Constants;
import com.powerapi.converter.Converter;
import com.powerapi.Entity.ResultatApplication;
import com.powerapi.utils.Properties;

public class PowerapiDao {
    private static final PowerapiDao INSTANCE =  new PowerapiDao();

    private PowerapiDao(){

    }

    public static PowerapiDao getInstance(){
        return INSTANCE;
    }

    public void sendResultat(String index, ResultatApplication resultatApplication) {
        UtilsDao.executeQuery(Properties.getEsUrl() + index +"/doc", Converter.resultatApplicationToJson(resultatApplication), HttpMethod.POST);
    }



}
