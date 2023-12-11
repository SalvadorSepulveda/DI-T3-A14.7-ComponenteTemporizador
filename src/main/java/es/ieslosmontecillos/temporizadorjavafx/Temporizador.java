package es.ieslosmontecillos.temporizadorjavafx;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.io.IOException;

public class Temporizador extends Label {
    private SimpleIntegerProperty tiempoProperty = new SimpleIntegerProperty();
    private Timeline timeline;

    public Temporizador() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("temporizador.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        timeline = new Timeline();
        timeline.setOnFinished(event -> {
            System.out.println("Temporizador terminado");
        });
    }

    public int getTiempo() {
        return tiempoProperty.get();
    }

    public void setTiempo(int tiempo) {
        tiempoProperty.set(tiempo);
        actualizarEtiqueta();
    }

    public SimpleIntegerProperty tiempoProperty() {
        return tiempoProperty;
    }

    private void actualizarEtiqueta() {
        setText("Tiempo: " + tiempoProperty.get());
    }

    @FXML
    private void initialize() {
        actualizarEtiqueta();
    }

    public void iniciarCronometro() {
        tiempoProperty.set(getTiempo());
        textProperty().bind(tiempoProperty.asString("Tiempo: %d"));
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), event -> {
            tiempoProperty.set(tiempoProperty.get() - 1);
            if (tiempoProperty.get() <= 0) {
                timeline.stop();
                System.out.println("Temporizador terminado");
            }
        });
        timeline.getKeyFrames().setAll(keyFrame);
        timeline.setCycleCount(tiempoProperty.get());
        timeline.playFromStart();
    }
}
