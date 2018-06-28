package com.powerapi.Entity;

import com.powerapi.utils.Constants;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ResultatApplication {
    private long timestamp;
    private String branch;
    private String build_name;
    private String build_url;
    /**
     * Energy total of the project
     */
    private double energy;
    private String app_name;
    private long duration;
    private String commit_name;
    private List<Classe> classes;
    private String scm_url;

    public ResultatApplication(long timestamp, String branch, String build_name, String commit_name, String app_name, String scm_url) {
        this.timestamp = timestamp;
        this.branch = branch;
        this.build_name = build_name;
        this.commit_name = commit_name;
        this.app_name = app_name;
        this.scm_url = scm_url;
        this.build_url = Constants.BUILD_URL + branch + "/" + build_name + "/pipeline";
    }

    public ResultatApplication fillResultatApplication(List<List<PowerapiCI>> powerapiCIList, final Map<String, String> classes) {
        List<Classe> classeL = new ArrayList<>();

        String lastTestName = "";
        String lastClassName = "";
        for (PowerapiCI papici : powerapiCIList.get(0)) {
            if (!papici.getTestName().equals(lastTestName)) {
                if (!classes.get(papici.getTestName()).equals(lastClassName)) {
                    lastClassName = classes.get(papici.getTestName());
                    classeL.add(new Classe(lastClassName));
                }

                List<Iteration> iterations = new ArrayList<>();
                int cpt = 1;

                for (List<PowerapiCI> papiciL : powerapiCIList) {
                    List<PowerData> powerDatas = new ArrayList<>();
                    double averageEnergy = 0;
                    long time_b = 0;
                    long time_e = 0;
                    for (PowerapiCI papici1 : papiciL) {
                        if (papici1.getTestName().equals(papici.getTestName())) {
                            powerDatas.add(new PowerData(papici1.getPower(), papici1.getTimestamp()));
                            averageEnergy = papici1.getEnergy();
                            time_b = papici1.getTimeBeginTest();
                            time_e = papici1.getTimeEndTest();
                        }

                    }

                    iterations.add(new Iteration(cpt, averageEnergy, time_b, time_e, powerDatas));
                    cpt++;
                }

                Methods newMethods = new Methods(papici.getTestName(), (papici.getTimeEndTest() - papici.getTimeBeginTest()));
                newMethods.setIterations(iterations);

                double energy = 0;
                for (Iteration ite : newMethods.getIterations()) {
                    energy += ite.getEnergy();
                }
                newMethods.setEnergy(energy / newMethods.getIterations().size());

                for (Classe c : classeL) {
                    if (c.getName().equals(classes.get(papici.getTestName()))) {
                        c.getMethods().add(newMethods);
                        break;
                    }
                }

            }

            lastTestName = papici.getTestName();
        }

        for (Classe c : classeL) {
            long duration = 0;
            for (Methods m : c.getMethods()) {
                duration += m.getDuration();
            }
            c.setDuration(duration);

            double energy = 0;
            for (Methods m : c.getMethods()) {
                energy += m.getEnergy();
            }
            c.setEnergy(energy);
        }

        this.setClasses(classeL);

        //Total Energy
        double totalEnergy = 0;
        long totalDuration = 0;
        for (Classe c : this.getClasses()) {
            totalEnergy += c.getEnergy();
            totalDuration += c.getDuration();
        }

        this.setDuration(totalDuration);
        this.setEnergy(totalEnergy);

        return this;
    }
}
