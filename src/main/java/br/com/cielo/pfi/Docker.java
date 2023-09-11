package br.com.cielo.pfi;


public class Docker {
    public String name;
    public double ready;
    public String rootDir;

    public String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getReady() {
        return ready;
    }

    public void setReady(double ready) {
        this.ready = ready;
    }

    public String getRootDir() {
        return rootDir;
    }

    public void setRootDir(String rootDir) {
        this.rootDir = rootDir;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}


