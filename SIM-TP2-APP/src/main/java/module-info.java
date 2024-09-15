module org.example {
    requires javafx.controls;
    requires com.opencsv;
    requires static lombok;
    requires commons.math3;
    exports org.example;
    exports org.example.Distribuciones;
    requires java.desktop; // Agrega esta línea para tener acceso a java.awt

}
