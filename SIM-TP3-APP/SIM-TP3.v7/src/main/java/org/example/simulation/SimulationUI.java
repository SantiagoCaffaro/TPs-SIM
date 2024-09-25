package org.example.simulation;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox; // Importar VBox
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SimulationUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Monte Carlo Simulación - Probabilidades");

        // Crear una cuadrícula para colocar los campos de texto y etiquetas
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Etiquetas y campos de texto
        Label labelTituloGeneral = new Label("1 - Encuesta General");
        labelTituloGeneral.setStyle("-fx-font-weight: bold;");

        Label labelNegarse = new Label("Probabilidad de negarse:");
        TextField textNegarse = new TextField("0.30");

        Label labelNoInternet = new Label("Probabilidad de no usar internet:");
        TextField textNoInternet = new TextField("0.10");

        Label labelAdultoNoRecuerda = new Label("Prob. adulto no recuerda flyer:");
        TextField textAdultoNoRecuerda = new TextField("0.10");

        Label labelAdultoRecuerda = new Label("Prob. adulto recuerda flyer:");
        TextField textAdultoRecuerda = new TextField("0.30");

        Label labelNoAdultoRecuerda = new Label("Prob. no adulto recuerda flyer:");
        TextField textNoAdultoRecuerda = new TextField("0.15");

        Label labelNoAdultoNoRecuerda = new Label("Prob. no adulto no recuerda flyer:");
        TextField textNoAdultoNoRecuerda = new TextField("0.05");

        Label labelCompraRecuerda = new Label("Prob. compra si recuerda flyer:");
        TextField textCompraRecuerda = new TextField("0.20");

        Label labelProbableRecuerda = new Label("Prob. probable compra si recuerda:");
        TextField textProbableRecuerda = new TextField("0.50");

        Label labelPruebaRecuerda = new Label("Prob. solo interesa probar si recuerda:");
        TextField textPruebaRecuerda = new TextField("0.30");

        Label labelCompraNoRecuerda = new Label("Prob. compra si no recuerda flyer:");
        TextField textCompraNoRecuerda = new TextField("0.02");

        Label labelProbableNoRecuerda = new Label("Prob. probable compra si no recuerda:");
        TextField textProbableNoRecuerda = new TextField("0.18");

        Label labelPruebaNoRecuerda = new Label("Prob. solo interesa probar si no recuerda:");
        TextField textPruebaNoRecuerda = new TextField("0.80");

        // Campos para los valores N, i, j
        Label labelMuestras = new Label("Número de muestras (N):");
        TextField textMuestras = new TextField("100");

        Label labelFila = new Label("Número de filas a mostrar (i):");
        TextField textFila = new TextField("10");

        Label labelColumna = new Label("Desde que fila (j):");
        TextField textColumna = new TextField("8");

        // Botón para iniciar la simulación
        Button btnSimular = new Button("Iniciar Simulación");
        btnSimular.setOnAction(e -> {
            try {
                // Validar que todas las probabilidades sean numéricas y estén en el rango correcto (0 a 1)
                double probNegarse = validarProbabilidad(textNegarse);
                double probNoInternet = validarProbabilidad(textNoInternet);
                double probAdultoNoRecuerda = validarProbabilidad(textAdultoNoRecuerda);
                double probAdultoRecuerda = validarProbabilidad(textAdultoRecuerda);
                double probNoAdultoRecuerda = validarProbabilidad(textNoAdultoRecuerda);
                double probNoAdultoNoRecuerda = validarProbabilidad(textNoAdultoNoRecuerda);
                double probCompraRecuerda = validarProbabilidad(textCompraRecuerda);
                double probProbableRecuerda = validarProbabilidad(textProbableRecuerda);
                double probPruebaRecuerda = validarProbabilidad(textPruebaRecuerda);
                double probCompraNoRecuerda = validarProbabilidad(textCompraNoRecuerda);
                double probProbableNoRecuerda = validarProbabilidad(textProbableNoRecuerda);
                double probPruebaNoRecuerda = validarProbabilidad(textPruebaNoRecuerda);

                // Capturar N, i, j
                int N = Integer.parseInt(textMuestras.getText());
                int i = Integer.parseInt(textFila.getText());
                int j = Integer.parseInt(textColumna.getText());

                VectorEstado simulator = new VectorEstado(
                        probNegarse, probNoInternet, probAdultoNoRecuerda, probAdultoRecuerda,
                        probNoAdultoRecuerda, probNoAdultoNoRecuerda, probCompraRecuerda,
                        probProbableRecuerda, probPruebaRecuerda, probCompraNoRecuerda,
                        probProbableNoRecuerda, probPruebaNoRecuerda
                );

                double[][] paresImpares = simulator.generadorVectoresParImpar(N, i, j);
                String excelPath = "resultados_simulacion.xlsx";
                Excel.crearExcel(excelPath, paresImpares);

                // Abrir el archivo Excel
                abrirArchivoExcel(excelPath);

                // Crear el GridPane para mostrar los resultados de la última fila
                GridPane resultGrid = new GridPane();
                resultGrid.setPadding(new Insets(10, 10, 10, 10));
                resultGrid.setVgap(5);
                resultGrid.setHgap(5);

                // Crear las etiquetas para los encabezados de la tabla (columna)
                String[] columnTitles = {"|Muestra |", "RND Gen |", "Ya Comprado |", "Acum. Ya Comprado |", "Probable Compra |", "Acum. Probable Compra |", "Porc. Ya Comprado |", "Prob. Probable Compra |"};
                for (int k = 0; k < columnTitles.length; k++) {
                    Label headerLabel = new Label(columnTitles[k]);
                    headerLabel.setStyle("-fx-font-weight: bold");
                    resultGrid.add(headerLabel, k, 0); // Colocamos los títulos en la primera fila
                }

                // Acceder a la última fila simulada de forma segura
                int filasAMostrar = Math.min(i, N); // Asegura que no exceda el tamaño de la muestra
                double[] ultimaFila = paresImpares[filasAMostrar]; // Acceder a la última fila basada en filasAMostrar

                // Añadir los datos de la última fila al GridPane
                for (int k = 0; k < ultimaFila.length; k++) {
                    String formattedValue;
                    if (k == 1 || k == 6 || k == 7) { // RND Gen, Porc. Ya Comprado y Prob. Probable Compra
                        formattedValue = String.format("%.4f", ultimaFila[k]).replace('.', ',');
                    } else { // Otros valores
                        formattedValue = String.valueOf((int) ultimaFila[k]);
                    }
                    Label dataLabel = new Label(formattedValue); // Formato para mostrar como string
                    resultGrid.add(dataLabel, k, 1); // Colocamos los datos en la segunda fila
                }

                // Crear el Alert y añadir los resultados previos junto con el GridPane que tiene la tabla
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Resultados de la Simulación");
                alert.setHeaderText(null);

                // Crear un contenedor VBox para combinar el texto y la tabla
                VBox dialogContent = new VBox();
                dialogContent.setSpacing(10);
                dialogContent.getChildren().addAll(
                        new Label("Simulación completada."),
                        new Label("Porcentaje de compradores: " + String.format("%.2f", ultimaFila[6]) + "%"),
                        new Label("Probabilidad de compra para adultos: " + String.format("%.4f", ultimaFila[7]).replace('.', ',')),
                        new Label("Última fila simulada:"),
                        resultGrid // Añadimos la tabla con la última fila simulada
                );

                alert.getDialogPane().setContent(dialogContent);
                alert.showAndWait(); // Asegúrate de que se llama showAndWait() aquí

            } catch (IllegalArgumentException ex) {
                mostrarAlertaError("Error en validación", ex.getMessage());
            }
        });


        Label labelTituloAdultosRecuerda = new Label("2 - Tabla Adultos que recuerdan el flyer");
        Label labelTituloAdultosNoRecuerda = new Label("3 - Tabla adultos que no recuerdan el flyer");
        Label labelTituloDatosSimulacion = new Label("4 - Datos de la simulación");


        //propiedades titulos
        labelTituloGeneral.setStyle("-fx-font-weight: bold;");
        labelTituloAdultosRecuerda.setStyle("-fx-font-weight: bold;");
        labelTituloAdultosNoRecuerda.setStyle("-fx-font-weight: bold;");
        labelTituloDatosSimulacion.setStyle("-fx-font-weight: bold;");



        textNegarse.setPrefWidth(400);
        textNoInternet.setPrefWidth(400);
        textAdultoNoRecuerda.setPrefWidth(400);
        textAdultoRecuerda.setPrefWidth(400);
        textNoAdultoRecuerda.setPrefWidth(400);
        textNoAdultoNoRecuerda.setPrefWidth(400);
        textCompraRecuerda.setPrefWidth(400);
        textProbableRecuerda.setPrefWidth(400);
        textPruebaRecuerda.setPrefWidth(400);
        textCompraNoRecuerda.setPrefWidth(400);
        textProbableNoRecuerda.setPrefWidth(400);
        textPruebaNoRecuerda.setPrefWidth(400);
        textMuestras.setPrefWidth(400);
        textFila.setPrefWidth(400);
        textColumna.setPrefWidth(400);

//propiedades  top right bottom left
        GridPane.setMargin(btnSimular, new Insets(0, 0, 0, 0)); // 10 píxeles de margen superior
        // Configurar la interfaz
        configurarInterfaz(grid, labelTituloGeneral, labelNegarse, textNegarse, labelNoInternet, textNoInternet,
                labelAdultoNoRecuerda, textAdultoNoRecuerda, labelAdultoRecuerda, textAdultoRecuerda,
                labelNoAdultoRecuerda, textNoAdultoRecuerda, labelNoAdultoNoRecuerda, textNoAdultoNoRecuerda,
                labelCompraRecuerda, textCompraRecuerda, labelProbableRecuerda, textProbableRecuerda,
                labelPruebaRecuerda, textPruebaRecuerda, labelCompraNoRecuerda, textCompraNoRecuerda,
                labelProbableNoRecuerda, textProbableNoRecuerda, labelPruebaNoRecuerda, textPruebaNoRecuerda,
                labelMuestras, textMuestras, labelFila, textFila, labelColumna, textColumna, btnSimular, labelTituloAdultosRecuerda,
                labelTituloAdultosNoRecuerda,labelTituloDatosSimulacion);

        // Configurar la escena
        Scene scene = new Scene(grid, 600, 660);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void configurarInterfaz(GridPane grid, Label labelTituloGeneral, Label labelNegarse, TextField textNegarse,
                                    Label labelNoInternet, TextField textNoInternet, Label labelAdultoNoRecuerda,
                                    TextField textAdultoNoRecuerda, Label labelAdultoRecuerda, TextField textAdultoRecuerda,
                                    Label labelNoAdultoRecuerda, TextField textNoAdultoRecuerda,
                                    Label labelNoAdultoNoRecuerda, TextField textNoAdultoNoRecuerda,
                                    Label labelCompraRecuerda, TextField textCompraRecuerda,
                                    Label labelProbableRecuerda, TextField textProbableRecuerda,
                                    Label labelPruebaRecuerda, TextField textPruebaRecuerda,
                                    Label labelCompraNoRecuerda, TextField textCompraNoRecuerda,
                                    Label labelProbableNoRecuerda, TextField textProbableNoRecuerda,
                                    Label labelPruebaNoRecuerda, TextField textPruebaNoRecuerda,
                                    Label labelMuestras, TextField textMuestras, Label labelFila,
                                    TextField textFila, Label labelColumna, TextField textColumna,
                                    Button btnSimular, Label labelTituloAdultosRecuerda, Label labelTituloAdultosNoRecuerda,Label labelTituloDatosSimulacion) {


        // Añadir elementos al GridPane
        grid.add(labelTituloGeneral, 0, 0, 2, 1); // Título ocupa dos columnas
        grid.add(labelNegarse, 0, 1);
        grid.add(textNegarse, 1, 1);
        grid.add(labelNoInternet, 0, 2);
        grid.add(textNoInternet, 1, 2);
        grid.add(labelAdultoNoRecuerda, 0, 3);
        grid.add(textAdultoNoRecuerda, 1, 3);
        grid.add(labelAdultoRecuerda, 0, 4);
        grid.add(textAdultoRecuerda, 1, 4);
        grid.add(labelNoAdultoRecuerda, 0, 5);
        grid.add(textNoAdultoRecuerda, 1, 5);
        grid.add(labelNoAdultoNoRecuerda, 0, 6);
        grid.add(textNoAdultoNoRecuerda, 1, 6);

        grid.add(labelTituloAdultosRecuerda, 0, 7, 2, 1); // Ocupa dos columnas
        grid.add(labelCompraRecuerda, 0, 8);
        grid.add(textCompraRecuerda, 1, 8);
        grid.add(labelProbableRecuerda, 0, 9);
        grid.add(textProbableRecuerda, 1, 9);
        grid.add(labelPruebaRecuerda, 0, 10);
        grid.add(textPruebaRecuerda, 1, 10);

        grid.add(labelTituloAdultosNoRecuerda, 0, 11, 2, 1); // Ocupa dos columnas
        grid.add(labelCompraNoRecuerda, 0, 12);
        grid.add(textCompraNoRecuerda, 1, 12);
        grid.add(labelProbableNoRecuerda, 0, 13);
        grid.add(textProbableNoRecuerda, 1, 13);
        grid.add(labelPruebaNoRecuerda, 0, 14);
        grid.add(textPruebaNoRecuerda, 1, 14);

        grid.add(labelTituloDatosSimulacion, 0, 15, 2, 1); // Ocupa dos columnas
        grid.add(labelMuestras, 0, 16);
        grid.add(textMuestras, 1, 16);
        grid.add(labelFila, 0, 17);
        grid.add(textFila, 1, 17);
        grid.add(labelColumna, 0, 18);
        grid.add(textColumna, 1, 18);

        grid.add(btnSimular, 0, 19, 2, 1); // Botón ocupa dos columnas

    }

    private double validarProbabilidad(TextField textField) {
        double probabilidad = Double.parseDouble(textField.getText());
        if (probabilidad < 0 || probabilidad > 1) {
            throw new IllegalArgumentException("La probabilidad debe estar entre 0 y 1.");
        }
        return probabilidad;
    }

    private void mostrarAlertaError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void abrirArchivoExcel(String path) {
        try {
            File excelFile = new File(path);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(excelFile);
            } else {
                mostrarAlertaError("Error", "No se puede abrir el archivo Excel.");
            }
        } catch (IOException e) {
            mostrarAlertaError("Error", "No se pudo abrir el archivo Excel: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

