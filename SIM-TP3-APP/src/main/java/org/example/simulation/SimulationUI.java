package org.example.simulation;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SimulationUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Monte Carlo Simulation - Probabilidades");

        // Crear una cuadrícula para colocar los campos de texto y etiquetas
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Campos de texto para probabilidades
        Label labelNegarse = new Label("Probabilidad de negarse:");
        TextField textNegarse = new TextField("0.20");

        Label labelNoInternet = new Label("Probabilidad de no usar internet:");
        TextField textNoInternet = new TextField("0.10");

        Label labelAdultoNoRecuerda = new Label("Prob. adulto no recuerda flyer:");
        TextField textAdultoNoRecuerda = new TextField("0.30");

        Label labelAdultoRecuerda = new Label("Prob. adulto recuerda flyer:");
        TextField textAdultoRecuerda = new TextField("0.25");

        Label labelNoAdultoRecuerda = new Label("Prob. no adulto recuerda flyer:");
        TextField textNoAdultoRecuerda = new TextField("0.10");

        Label labelNoAdultoNoRecuerda = new Label("Prob. no adulto no recuerda flyer:");
        TextField textNoAdultoNoRecuerda = new TextField("0.05");

        // Probabilidades de compra (según recuerdan o no el flyer)
        Label labelCompraRecuerda = new Label("Prob. compra si recuerda flyer:");
        TextField textCompraRecuerda = new TextField("0.15");

        Label labelProbableRecuerda = new Label("Prob. probable compra si recuerda:");
        TextField textProbableRecuerda = new TextField("0.50");

        Label labelPruebaRecuerda = new Label("Prob. solo interesa probar si recuerda:");
        TextField textPruebaRecuerda = new TextField("0.35");

        Label labelCompraNoRecuerda = new Label("Prob. compra si no recuerda flyer:");
        TextField textCompraNoRecuerda = new TextField("0.10");

        Label labelProbableNoRecuerda = new Label("Prob. probable compra si no recuerda:");
        TextField textProbableNoRecuerda = new TextField("0.30");

        Label labelPruebaNoRecuerda = new Label("Prob. solo interesa probar si no recuerda:");
        TextField textPruebaNoRecuerda = new TextField("0.60");

        // Campos para los valores N, i, j
        Label labelMuestras = new Label("Número de muestras (N):");
        TextField textMuestras = new TextField("100");

        Label labelFila = new Label("Número de filas (i):");
        TextField textFila = new TextField("10");

        Label labelColumna = new Label("Número de columnas (j):");
        TextField textColumna = new TextField("8");

        // Botón para iniciar la simulación
        Button btnSimular = new Button("Iniciar Simulación");
        btnSimular.setOnAction(e -> {
            // Capturar las probabilidades ingresadas
            double probNegarse = Double.parseDouble(textNegarse.getText());
            double probNoInternet = Double.parseDouble(textNoInternet.getText());
            double probAdultoNoRecuerda = Double.parseDouble(textAdultoNoRecuerda.getText());
            double probAdultoRecuerda = Double.parseDouble(textAdultoRecuerda.getText());
            double probNoAdultoRecuerda = Double.parseDouble(textNoAdultoRecuerda.getText());
            double probNoAdultoNoRecuerda = Double.parseDouble(textNoAdultoNoRecuerda.getText());
            double probCompraRecuerda = Double.parseDouble(textCompraRecuerda.getText());
            double probProbableRecuerda = Double.parseDouble(textProbableRecuerda.getText());
            double probPruebaRecuerda = Double.parseDouble(textPruebaRecuerda.getText());
            double probCompraNoRecuerda = Double.parseDouble(textCompraNoRecuerda.getText());
            double probProbableNoRecuerda = Double.parseDouble(textProbableNoRecuerda.getText());
            double probPruebaNoRecuerda = Double.parseDouble(textPruebaNoRecuerda.getText());

            // Capturar N, i, j
            int N = Integer.parseInt(textMuestras.getText());
            int i = Integer.parseInt(textFila.getText());
            int j = Integer.parseInt(textColumna.getText());

            // Crear la instancia de MonteCarloSimulator con las probabilidades ingresadas
            MonteCarloSimulator simulator = new MonteCarloSimulator(
                    probNegarse, probNoInternet, probAdultoNoRecuerda, probAdultoRecuerda,
                    probNoAdultoRecuerda, probNoAdultoNoRecuerda, probCompraRecuerda,
                    probProbableRecuerda, probPruebaRecuerda, probCompraNoRecuerda,
                    probProbableNoRecuerda, probPruebaNoRecuerda
            );

            // Ejecutar la simulación y mostrar los resultados
            double[][] resultado = simulator.simular(N, i, j);

            // Mostrar los resultados (esto lo puedes mejorar según necesites)
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Resultados de la Simulación");
            alert.setHeaderText(null);
            alert.setContentText("Simulación completada.\n"
                    + "Porcentaje de compradores: " + simulator.getPorcentajeCompradores() + "%\n"
                    + "Probabilidad de compra para adultos: " + simulator.getProbabilidadCompraAdulto());
            alert.showAndWait();
        });

        // Añadir todos los elementos a la cuadrícula
        grid.add(labelNegarse, 0, 0);
        grid.add(textNegarse, 1, 0);
        grid.add(labelNoInternet, 0, 1);
        grid.add(textNoInternet, 1, 1);
        grid.add(labelAdultoNoRecuerda, 0, 2);
        grid.add(textAdultoNoRecuerda, 1, 2);
        grid.add(labelAdultoRecuerda, 0, 3);
        grid.add(textAdultoRecuerda, 1, 3);
        grid.add(labelNoAdultoRecuerda, 0, 4);
        grid.add(textNoAdultoRecuerda, 1, 4);
        grid.add(labelNoAdultoNoRecuerda, 0, 5);
        grid.add(textNoAdultoNoRecuerda, 1, 5);
        grid.add(labelCompraRecuerda, 0, 6);
        grid.add(textCompraRecuerda, 1, 6);
        grid.add(labelProbableRecuerda, 0, 7);
        grid.add(textProbableRecuerda, 1, 7);
        grid.add(labelPruebaRecuerda, 0, 8);
        grid.add(textPruebaRecuerda, 1, 8);
        grid.add(labelCompraNoRecuerda, 0, 9);
        grid.add(textCompraNoRecuerda, 1, 9);
        grid.add(labelProbableNoRecuerda, 0, 10);
        grid.add(textProbableNoRecuerda, 1, 10);
        grid.add(labelPruebaNoRecuerda, 0, 11);
        grid.add(textPruebaNoRecuerda, 1, 11);

        // Añadir los campos de N, i, j
        grid.add(labelMuestras, 0, 12);
        grid.add(textMuestras, 1, 12);
        grid.add(labelFila, 0, 13);
        grid.add(textFila, 1, 13);
        grid.add(labelColumna, 0, 14);
        grid.add(textColumna, 1, 14);

        grid.add(btnSimular, 1, 15);

        // Crear la escena y mostrar la ventana
        Scene scene = new Scene(grid, 500, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
