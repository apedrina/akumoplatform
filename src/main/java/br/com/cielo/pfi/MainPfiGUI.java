package br.com.cielo.pfi;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.awt.*;
import java.io.*;

public class MainPfiGUI extends Application {

    public String akumoFile = null;

    @Override
    public void start(final Stage stage) {
        stage.setTitle("Akumo Platform");
        stage.setHeight(220);
        stage.setWidth(300);
        stage.setResizable(false);
        stage.centerOnScreen();

        final FileChooser fileChooser = new FileChooser();
        ImageView imageView_0 = new ImageView("document.png");
        imageView_0.setFitHeight(20);
        imageView_0.setFitWidth(20);
        final Button openButton = new Button("Select Akumo File...", imageView_0);
        openButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        configureFileChooser(fileChooser);
                        File file = fileChooser.showOpenDialog(stage);
                        if (file != null) {
                            openButton.setText(file.getAbsolutePath());
                            akumoFile = file.getAbsolutePath();

                        }
                    }
                });

        ImageView imageView = new ImageView("execute.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        final Button executeButton = new Button("Execute", imageView);
        executeButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        if (akumoFile == null) {
                            showAlert(Alert.AlertType.ERROR, stage, "Error", "Nenhum arquivo de script foi selecionado.");

                        } else {
                            //showAlert(Alert.AlertType.INFORMATION, stage, "Arquivo Selecionado", "O arquivo : " + akumoFile + "\n vai ser executado.");
                            try {
                                var akumoScript = LoaderFile.loadAkumoFile(akumoFile);
                                akumoScript.execute();

                            } catch (FileNotFoundException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                        akumoFile = null;
                    }
                });

        final GridPane inputGridPane = new GridPane();
        inputGridPane.setPadding(new Insets(10, 10, 10, 10));

        GridPane.setConstraints(openButton, 0, 1);
        GridPane.setConstraints(executeButton, 0, 2);
        inputGridPane.setHgap(20);
        inputGridPane.setVgap(20);
        inputGridPane.getChildren().addAll(openButton, executeButton);

        final Pane rootGroup = new VBox(12);

        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("Ajuda");
        menuFile.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        try {
                            openFile();
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
        menuBar.getMenus().addAll(menuFile);

        rootGroup.getChildren().addAll(menuBar, inputGridPane);

        stage.setScene(new Scene(rootGroup));

        stage.show();
    }

    public void openFile() throws Exception {
        help();
    }

    public void help() {
        //if (Desktop.isDesktopSupported()) {
        try {
            InputStream is = getClass().getResourceAsStream("/help.pdf");
            byte[] data = new byte[is.available()];
            is.read(data);
            is.close();
            String tempFile = "file";
            File temp = File.createTempFile(tempFile, ".pdf");
            FileOutputStream fos = new FileOutputStream(temp);
            fos.write(data);
            fos.flush();
            fos.close();
            Desktop.getDesktop().open(temp);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("NO PDF READER INSTALLED");
        }
        //}
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    private static void configureFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("View Files");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Akumo's File", "*.json")
        );
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
    }

    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();

    }
}
