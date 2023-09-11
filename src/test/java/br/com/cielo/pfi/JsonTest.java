package br.com.cielo.pfi;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

public class JsonTest {

    @Test
    void generate_json() throws IOException {
        AkumoFile akumoFile = new AkumoFile();
        akumoFile.rootDir = "C:\\";

        Docker d1 = new Docker();
        d1.name = "KeyCloak";
        d1.rootDir = "C:\\";
        d1.ready = 0;
        d1.type = "docker";

        akumoFile.setContainers(Arrays.asList(d1));

        Microservice m1 = new Microservice();
        m1.name = "pfi-web";
        m1.ready = 12;
        m1.rootDir = "C:\\";

        akumoFile.setMicroservices(Arrays.asList(m1));

        System.out.println(LoaderFile.javaToGson(akumoFile));

    }
}
