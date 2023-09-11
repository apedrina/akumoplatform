package br.com.cielo.pfi;


import java.util.List;

public class AkumoFile {

    public String rootDir;
    public List<Microservice> microservices;

    public List<Docker> containers;

    public void execute(){
        Engine.run(this);
    }

    public List<Docker> getContainers() {
        return containers;
    }

    public void setContainers(List<Docker> containers) {
        this.containers = containers;
    }

    public String getRootDir() {
        return rootDir;
    }

    public void setRootDir(String rootDir) {
        this.rootDir = rootDir;
    }

    public List<Microservice> getMicroservices() {
        return microservices;
    }

    public void setMicroservices(List<Microservice> microservices) {
        this.microservices = microservices;
    }
}
