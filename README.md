
# greenci-maven-plugin


This project is a Maven plugin that automates the processing and sending of data related to the PowerAPI tool.

## REQUIREMENTS


PowerAPI must be installed on your machine beforehand(LINUX OS ONLY):
https://github.com/Spirals-Team/powerapi/blob/master/README.md

An Elastic Search Server must be available with a `powerapici` index :
https://www.elastic.co/fr/products/elasticsearch

Add [greenci-handler-plugin](https://github.com/adrien1251/greenci-handler-plugin) to your project. 


## Installation

- Clone the repository of this Maven plugin
  
- In the Maven .m2 configuration file, add :
```
  <PluginGroups>
       <PluginGroup> com.powerapi </ pluginGroup>
   </ PluginGroups>
```

-In the POM.xml of the tested project, add :
```
  <plugin>
       <groupId>com.powerapi</groupId>
       <artifactId>powerapi-maven-plugin</artifactId>
       <version>1.0-SNAPSHOT</version>
       <configuration>
          <scmUrl>https://github.com/adrien1251/spring-boot-rest-example</scmUrl>
          <esUrl>http://elasticsearch.app.projet-davidson.fr/</esUrl>
       </configuration>
  </plugin>
```

- In the project directory:

  `mvn install`

## Usage

- The command to start the process:

  `mvn powerapi: test`
  

Available Options : 

  -Dtest.build=buildname

    buildname takes the commitname if this option is missing

  -Dtest.commit=commitname

    If this option is missing : git must be installed on the computer, and the actual commit will be taken

  -Dtest.frequency=frenquency

    If this option is missing the frenquency will be 50ms


## Background

This project was developed in the framework of a collaboration between Romain Rouvoy and Aurélien Bourdon from the Inria Spirals project-team and Davidson SI Nord, to contribute to the PhD thesis of Chakib Belgaid.

## Authors

Adrien Deblock / Vincent Leclercq.
This project served as a mission to complete our end-of-study internship at Davidson Consulting.

## License

This project is under the MIT license - see file [LICENSE.md](LICENSE.md) for details
