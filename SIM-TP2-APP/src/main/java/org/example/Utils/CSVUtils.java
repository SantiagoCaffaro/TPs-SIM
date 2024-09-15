package org.example.Utils;


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
public class CSVUtils {
    public static void abrirCSV(String csvFilePath) {
        // Ejecutar en un nuevo hilo para no bloquear la interfaz gráfica
        new Thread(() -> {
            try {
                File csvFile = new File(csvFilePath);

                // Verificar si Desktop está soportado
                if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
                    // Verificar si el archivo existe
                    if (csvFile.exists()) {
                        desktop.open(csvFile);  // Abrir el archivo
                    } else {
                        System.out.println("El archivo CSV no existe: " + csvFilePath);
                    }
                } else {
                    System.out.println("La clase Desktop no está soportada en este sistema.");
                }
            } catch (IOException e) {
                System.out.println("Error al intentar abrir el archivo CSV: " + e.getMessage());
            }
        }).start(); // Iniciar el nuevo hilo
    }
}
