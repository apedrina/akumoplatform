package br.com.cielo.pfi;

import org.apache.commons.io.output.TeeOutputStream;
import org.apache.maven.shared.invoker.*;
import org.apache.maven.shared.utils.cli.CommandLineException;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.util.Collections;

public class MavenTest {

    @Test
    void call_mvn() {
        StringBuilder cmd = new StringBuilder();
        try {
            Invoker invoker = new DefaultInvoker();
            invoker.setMavenHome(new File("/Users/alissonpedrina/Documents/programas/apache-maven-3.9.1"));
            InvocationRequest request = new DefaultInvocationRequest();
            request.setPomFile(new File("/Users/alissonpedrina/Documents/projetos/pfi/pom.xml"));
            request.setGoals(Collections.singletonList("install"))
                    .setMavenOpts("-Dmaven.test.skip=true")
                    //.setBaseDirectory(file).
                    .setBatchMode(true);
            try {
                InvocationResult invocationResult = invoker.execute(request);
                CommandLineException clEx = invocationResult.getExecutionException();
                var x = clEx.getStackTrace();
                System.out.println(x);
            } catch (MavenInvocationException ex) {
            }
            try {
                InvocationResult result = invoker.execute(request);
                invoker.setOutputHandler(line -> {
                    cmd.append(line);
                });
                if (result.getExitCode() != 0) {
                    throw new IllegalStateException("Build failed.");
                }
            } catch (Exception e) {
                System.out.println("erro");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("result " + cmd);
    }


    @Test
    void test() throws IOException, MavenInvocationException {
        //getEnv().forEach(request::addShellEnvironment);
        Invoker invoker = new DefaultInvoker();
        invoker.setMavenHome(new File("/Users/alissonpedrina/Documents/programas/apache-maven-3.9.1"));
        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(new File("/Users/alissonpedrina/Documents/projetos/pfi/pom.xml"));
        request.setGoals(Collections.singletonList("install"))
                .setMavenOpts("-Dmaven.test.skip=true")
                //.setBaseDirectory(file).
                .setBatchMode(true);
        File outputLog = new File("/Users/alissonpedrina/Documents/projetos", "output.log");
        InvocationOutputHandler outputHandler = new PrintStreamHandler(
                new PrintStream(new TeeOutputStream(System.out, Files.newOutputStream(outputLog.toPath())), true, "UTF-8"),
                true);
        invoker.setOutputHandler(outputHandler);

        File invokerLog = new File("/Users/alissonpedrina/Documents/projetos", "invoker.log");
        PrintStreamLogger logger = new PrintStreamLogger(new PrintStream(new FileOutputStream(invokerLog), false, "UTF-8"),
                InvokerLogger.DEBUG);
        invoker.setLogger(logger);


        InvocationResult result = invoker.execute(request);



        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/alissonpedrina/Documents/projetos/output.log")))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("line______ >>> " + line);
                resultStringBuilder.append(line).append("\n");
            }
        }
    }
}
