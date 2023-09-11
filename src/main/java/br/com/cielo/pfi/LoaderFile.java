package br.com.cielo.pfi;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public final class LoaderFile {

    private static Gson gson = new Gson();

    public static String javaToGson(AkumoFile obj) throws IOException {
        return gson.toJson(obj);
    }

    public static AkumoFile loadAkumoFile(String pathFile) throws FileNotFoundException {
        return gson.fromJson(new FileReader(pathFile), AkumoFile.class);
    }

}
