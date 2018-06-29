package com.powerapi;

import com.powerapi.dao.GitDao;
import com.powerapi.service.PowerapiService;
import com.powerapi.utils.CommonUtils;
import com.powerapi.utils.Logger;
import com.powerapi.utils.Properties;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Says "Hi" to the user.
 */
@Mojo(name = "test")
public class PowerapiMojo extends AbstractMojo {

    @Parameter(property = "test.build")
    private String build;

    @Parameter(property = "test.commit")
    private String commit;

    @Parameter
    private String scmUrl;

    @Parameter
    private String esUrl;

    @Parameter(property = "test.frequency")
    private Integer frequency;

    @Parameter(property = "test.nbIterations")
    private Integer nbIterations;

    private PowerapiService powerapiService = new PowerapiService();
    private GitDao gitDao = GitDao.getInstance();

    private List<String> powerapiCSVList = new ArrayList<>();
    private List<String> testCSVList = new ArrayList<>();

    public void execute() throws MojoExecutionException {
        Logger.setLog(getLog());

        configureParam();        
        Properties.setFrequency(frequency);
        Properties.setEsUrl(esUrl);
        
        Long beginApp = new Date().getTime();

        for(int i=0; i<nbIterations; i++) {
            getLog().info("En cours d'execution... Iteration "+i);
            fillTestAndPowerapiData();
        }
        powerapiService.sendPowerapiciData(beginApp, "MASTER", build, commit, scmUrl, powerapiCSVList, testCSVList);

        getLog().info("Data send");
    }
    
    private void configureParam() throws  MojoExecutionException {
        if (esUrl == null)
            throw new MojoExecutionException("No ElasticSearch url found, precise him in our plugin configuration in your pom.xml (saw the doc for more information)");
        else if (scmUrl == null)
            throw new MojoExecutionException("No scm url found, precise him in our plugin configuration in your pom.xml (saw the doc for more information)");

        if(nbIterations == null){
            Logger.warning("No iterations number found, so it will be 3 iterations");
            nbIterations = 3;
        }

        if (frequency == null) {
            Logger.warning("No frequence found, the plugin will work with 50ms frequency");
            frequency = 50;
        }

        if (commit == null) {
            Logger.warning("No commit name: work with git for have commit name");
            commit = gitDao.getCommitName();
            Logger.info("Commit name: " + commit);
        }
        if (build == null) {
            Logger.warning("No build name found, your build name will be the commit name");
            build = commit;
        }
    }

    private void fillTestAndPowerapiData() {
        String[] cmd = {"sh", "-c", "(mvn test -DforkCount=0 | grep timestamp= | cut -d '-' -f 2 | tr -d ' ') > test1.csv & (powerapi duration 30 modules procfs-cpu-simple monitor --frequency 50 --console --all | grep muid) > data1.csv;"};

        try {
            Process p = Runtime.getRuntime().exec(cmd);
            getLog().info(CommonUtils.readProcessus(p));

            p.waitFor();
            Process powerapiProc = Runtime.getRuntime().exec(new String[]{"sh", "-c", "cat data1.csv | tr '\n' ' '"});
            powerapiCSVList.add(CommonUtils.readProcessus(powerapiProc));

            Process testProc = Runtime.getRuntime().exec(new String[]{"sh", "-c", "cat test1.csv | grep timestamp= | cut -d '-' -f 2 | tr -d ' '"});
            testCSVList.add(CommonUtils.readProcessus(testProc));
        } catch (IOException | InterruptedException e) {
            getLog().error("", e);
        }
    }
}