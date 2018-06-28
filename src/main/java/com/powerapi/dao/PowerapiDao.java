package com.powerapi.dao;


import com.powerapi.Entity.ResultatApplication;
import com.powerapi.converter.Converter;
import com.powerapi.enums.HttpMethod;
import com.powerapi.utils.Properties;

public class PowerapiDao {
    private static final PowerapiDao INSTANCE =  new PowerapiDao();
    private final UtilsDao utilsDao = UtilsDao.getInstance();

    private PowerapiDao(){

    }

    public static PowerapiDao getInstance(){
        return INSTANCE;
    }

    public void sendResult(String index, ResultatApplication resultatApplication) {
        utilsDao.executeQuery(Properties.getEsUrl() + index +"/doc", Converter.resultatApplicationToJson(resultatApplication), HttpMethod.POST);
    }



}
