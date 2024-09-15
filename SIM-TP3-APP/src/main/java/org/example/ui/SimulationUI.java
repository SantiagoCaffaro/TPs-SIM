package org.example.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.simulation.MonteCarloSimulator;

public class SimulationUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Crear los campos de texto para ingresar N, i, y j
        TextField fieldN = new TextField();
        fieldN.setPromptText("Ingrese N (número de iteraciones)");

        TextField fieldI = new TextField();
        fieldI.setPromptText("Ingrese i (cantidad de iteraciones a mostrar)");

        TextField fieldJ = new TextField();
        fieldJ.setPromptText("Ingrese j (hora a partir de la cual visualizar)");

        // Crear botón para iniciar simulación
        Button startButton = new Button("Iniciar Simulación");

        // Área de texto para mostrar resultados
        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);

        // Acción del botón: ejecuta la simulación con los valores ingresados
        startButton.setOnAction(e -> {
            try {
                int N = Integer.parseInt(fieldN.getText());
                int i = Integer.parseInt(fieldI.getText());
                int j = Integer.parseInt(fieldJ.getText());

                // Crear instancia del simulador MonteCarlo y ejecutar la simulación
                MonteCarloSimulator simulator = new MonteCarloSimulator();
                simulator.ejecutarSimulacion1(N);

                // Calcular el porcentaje de compradores y la probabilidad de compra de un adulto
                double porcentajeCompradores = simulator.getPorcentajeCompradores();
                double probabilidadCompraAdulto = simulator.getProbabilidadCompraAdulto();

                // Mostrar resultados en el TextArea
                resultArea.setText("Resultados:\n" +
                        "Total Compradores: " + simulator.getTotalCompradores() + "\n" +
                        "Total Probables Compradores: " + simulator.getTotalProbablesCompradores() + "\n" +
                        "Total Interesados Prueba: " + simulator.getTotalInteresadosPrueba() + "\n\n" +
                        "Porcentaje de personas que ya compraron el nuevo perfume: " + String.format("%.2f", porcentajeCompradores) + "%\n" +
                        "Probabilidad de que un adulto compre el perfume: " + String.format("%.2f", probabilidadCompraAdulto) + "\n" +
                        "Simulaciones desde j=" + j + " hasta i=" + (j + i) + " (iteraciones mostradas no implementadas)");
            } catch (NumberFormatException ex) {
                resultArea.setText("Por favor, ingrese valores numéricos válidos para N, i, y j.");
            }
        });


        // Crear el layout
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(
                new Label("Simulación Montecarlo - Ingrese valores:"),
                fieldN, fieldI, fieldJ,
                startButton, resultArea
        );

        // Crear escena y mostrar ventana
        Scene scene = new Scene(vbox, 400, 400);
        primaryStage.setTitle("Simulación Montecarlo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
