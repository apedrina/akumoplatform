package br.com.cielo.pfi;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.io.output.TeeOutputStream;
import org.apache.maven.shared.invoker.*;

import java.io.*;
import java.nio.file.Files;
import java.util.Collections;

public class Engine {

    private static Gson gson = new Gson();
    private static LogTextArea textArea = new LogTextArea();

    static void run(AkumoFile akumoFile) {
        textArea.setPrefSize(700, 700);
        System.out.println(gson.toJson(akumoFile));
        if (akumoFile.getContainers() != null) {
            akumoFile.getContainers().forEach(c -> {
                openWindow(c);
            });
        }

    }

    private static String checkSO() {
        System.out.println(System.getProperty("os.name"));
        var so = System.getProperty("os.name");
        if (so.toLowerCase().contains("mac")) {
            return "mac";
        } else if (so.toLowerCase().contains("win")) {
            return "win";
        } else {
            return "linux";
        }

    }

    private static void openWindow(Docker c) {
        Platform.runLater(()->{
            Parent root;
            ToolBar toolBar = new ToolBar();

            Button button1 = new Button("Run Command");
            toolBar.getItems().add(button1);

            Button button2 = new Button("Open Log");
            toolBar.getItems().add(button2);

            root = new VBox(toolBar, textArea);
            Stage stage = new Stage();
            stage.setTitle(c.name);

            stage.setScene(new Scene(root, 800, 600));
            stage.show();

            try {
                open();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (MavenInvocationException e) {
                throw new RuntimeException(e);
            }
        });

    }

    static void open() throws IOException, MavenInvocationException {
        new Runnable() {
            @Override
            public void run() {
                Invoker invoker = new DefaultInvoker();
                invoker.setMavenHome(new File("/Users/alissonpedrina/Documents/programas/apache-maven-3.9.1"));
                InvocationRequest request = new DefaultInvocationRequest();
                request.setPomFile(new File("/Users/alissonpedrina/Documents/projetos/pfi/pom.xml"));
                request.setGoals(Collections.singletonList("install"))
                        .setMavenOpts("-Dmaven.test.skip=true")
                        //.setBaseDirectory(file).
                        .setBatchMode(true);
                File outputLog = new File("/Users/alissonpedrina/Documents/projetos", "output.log");
                InvocationOutputHandler outputHandler = null;
                try {
                    outputHandler = new PrintStreamHandler(
                            new PrintStream(new TeeOutputStream(System.out, Files.newOutputStream(outputLog.toPath())), true, "UTF-8"),
                            true);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                invoker.setOutputHandler(outputHandler);

                File invokerLog = new File("/Users/alissonpedrina/Documents/projetos", "invoker.log");
                PrintStreamLogger logger = null;
                try {
                    logger = new PrintStreamLogger(new PrintStream(new FileOutputStream(invokerLog), false, "UTF-8"),
                            InvokerLogger.DEBUG);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                invoker.setLogger(logger);


                try {
                    InvocationResult result = invoker.execute(request);
                } catch (MavenInvocationException e) {
                    throw new RuntimeException(e);
                }
                StringBuilder resultStringBuilder = new StringBuilder();
                try (BufferedReader br
                             = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/alissonpedrina/Documents/projetos/output.log")))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        resultStringBuilder.append(line).append("\n");
                        Platform.runLater(()->{
                            textArea.setMessage(resultStringBuilder.toString());

                        });
                    }

                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }.run();

    }

}
