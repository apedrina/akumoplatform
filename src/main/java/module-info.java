module br.com.cielo.pfi.pfi {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.desktop;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    requires com.google.gson;
    requires maven.invoker;
    requires org.apache.commons.io;

    opens br.com.cielo.pfi to com.google.gson, javafx.fxml;

    exports br.com.cielo.pfi;

}