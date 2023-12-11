module es.ieslosmontecillos.temporizadorjavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens es.ieslosmontecillos.temporizadorjavafx to javafx.fxml;
    exports es.ieslosmontecillos.temporizadorjavafx;
}