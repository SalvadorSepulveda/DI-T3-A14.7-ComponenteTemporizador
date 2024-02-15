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

/**
 * Es un control de JavaFX que muestra un temporizador con una cuenta atrás.
 * La clase Temporizador extiende de Label y se utiliza para crear un temporizador gráfico
 * que cuenta atrás desde un tiempo especificado. Esta clase está diseñada para ser usada en aplicaciones JavaFX.
 * @author Salvador Sepúlveda Millán
 * @version 1.0
 * @since 1.0
 */
public class Temporizador extends Label {
    private SimpleIntegerProperty tiempoProperty = new SimpleIntegerProperty();
    private Timeline timeline;

    /**
     * Constructor de la clase Temporizador que inicializa un temporizador y carga la interfaz de usuario desde un archivo FXML.
     * Si el temporizador termina, se imprime un mensaje en la consola.
     */
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

    /**
     * Obtiene el valor actual del tiempo del temporizador.
     *
     * @return     el valor actual del tiempo del temporizador
     */
    public int getTiempo() {
        return tiempoProperty.get();
    }

    /**
     * Establece el valor del tiempo del temporizador y actualiza la etiqueta.
     *
     * @param tiempo     el nuevo valor del tiempo del temporizador
     */
    public void setTiempo(int tiempo) {
        tiempoProperty.set(tiempo);
        actualizarEtiqueta();
    }

    /**
     * Devuelve la propiedad del tiempo del temporizador.
     *
     * @return     la propiedad del tiempo del temporizador
     */
    public SimpleIntegerProperty tiempoProperty() {
        return tiempoProperty;
    }

    /**
     * Actualiza la etiqueta del temporizador con el valor actual del tiempo.
     */
    private void actualizarEtiqueta() {
        setText("Tiempo: " + tiempoProperty.get());
    }

    /**
     * Inicializa la etiqueta del temporizador.
     */
    @FXML
    private void initialize() {
        actualizarEtiqueta();
    }

    /**
     * Inicia el cronómetro del temporizador y actualiza visualmente el tiempo restante.
     */
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
