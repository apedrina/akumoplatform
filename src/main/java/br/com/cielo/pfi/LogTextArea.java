package br.com.cielo.pfi;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;

public class LogTextArea extends TextArea {

    private boolean pausedScroll = false;
    private double scrollPosition = 0;

    public LogTextArea() {
        super();
    }

    public void setMessage(String data) {
        if (pausedScroll) {
            scrollPosition = this.getScrollTop();
            this.setText(data);
            this.setScrollTop(scrollPosition);
        } else {
            Platform.runLater(()->{
                this.appendText(data);
                this.setScrollTop(Double.MAX_VALUE);
            });

        }
    }

    public void pauseScroll(Boolean pause) {
        pausedScroll = pause;
    }

}
