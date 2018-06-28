package com.powerapi.dao;

import com.powerapi.utils.CommonUtils;

import java.io.IOException;

public class GitDao {
    private static final GitDao INSTANCE =  new GitDao();

    private GitDao(){

    }

    public static GitDao getInstance(){
        return INSTANCE;
    }

    public String getCommitName(){
        String[] cmd = {"sh", "-c", "git describe --always"};
        String back = "";
        try {
            Process powerapiProc = Runtime.getRuntime().exec(cmd);
            back += CommonUtils.readProcessus(powerapiProc);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return back;
    }
}
