package org.example.Distribuciones;

import com.opencsv.CSVWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.example.Graphics.Histogram;
import org.example.Utils.CSVUtils;
import org.example.Utils.ListUtils;
import org.example.App;  // Ajusta el paquete según sea necesario

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Data
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class Distribucion {

    static final String csvFilePath = "Tabla-Numero-Aleatorios/numerosgenerados.csv";

    int cantidadDeIntervalosK;
    double[] rnd01;
    double[] rndDistribution;

    int N;
    double min;
    double max;
    double rango;
    double amplitud;
    double media;
    double varianza;
    ArrayList<Double[]> limites;
    ArrayList<Integer> frecuenciasObservadas;
    ArrayList<Double> frecuenciasEsperadas;
    ArrayList<Double> chiCuadradoCalculado;

    public abstract void distributionInput();
    abstract double[] generateRandomNumbersWithDistribution();
    abstract ArrayList<Double> getFrecuenciasEsperadas();

    void pruebaChiCuadrado() {
        rndDistribution = generateRandomNumbersWithDistribution();

        // N
        N = rndDistribution.length;
        // MINIMO
        min = Arrays.stream(rndDistribution).min().orElseThrow(() ->
                new RuntimeException("No se pudo obtener el minimo"));
        // MAXIMO
        max = Arrays.stream(rndDistribution).max().orElseThrow(() ->
                new RuntimeException("No se pudo obtener el maximo"));
        // RANGO
        rango = max - min;
        // AMPLITUD
        amplitud = rango / (double) cantidadDeIntervalosK;
        // MEDIA
        media = Arrays.stream(rndDistribution).average().orElseThrow(() ->
                new RuntimeException("No se pudo calcular la media"));
        // VARIANZA
        varianza = Arrays.stream(rndDistribution)
                .map(x -> Math.pow(x - media, 2))
                .sum() / (double) N;

        // INTERVALOS
        limites = new ArrayList<>();
        for (int i = 0; i < cantidadDeIntervalosK; i++) {
            Double[] interval = new Double[2];
            interval[0] = min + (i * amplitud);
            interval[1] = min + ((i + 1) * amplitud);
            limites.add(interval);
        }

        // FRECUENCIA OBSERVADA
        frecuenciasObservadas = new ArrayList<>(Collections.nCopies(cantidadDeIntervalosK, 0));
        for (double dataValue : rndDistribution) {
            for (int i = 0; i < cantidadDeIntervalosK; i++) {
                if (dataValue >= limites.get(i)[0] && dataValue < limites.get(i)[1]) {
                    frecuenciasObservadas.set(i, frecuenciasObservadas.get(i) + 1);
                    break;
                } else if (i == cantidadDeIntervalosK - 1) {
                    frecuenciasObservadas.set(i, frecuenciasObservadas.get(i) + 1);
                }
            }
        }

        // FRECUENCIA ESPERADA
        frecuenciasEsperadas = getFrecuenciasEsperadas();

        // CHI CUADRADO CALCULADO
        chiCuadradoCalculado = new ArrayList<>(Collections.nCopies(cantidadDeIntervalosK, 0.0));
        for (int i = 0; i < cantidadDeIntervalosK; i++) {
            chiCuadradoCalculado.set(i, Math.pow(frecuenciasObservadas.get(i) - frecuenciasEsperadas.get(i), 2) /
                    frecuenciasEsperadas.get(i));
        }

        // Acumular cuando las frecuencias esperadas sean menor que 5
        filaActualLoop: for (int filaActual = 0; filaActual < cantidadDeIntervalosK; filaActual++) {
            if (frecuenciasEsperadas.get(filaActual) < 5) {
                int ultimaFilaASumar = filaActual;
                double sumaFrecuenciasEsperadas = 0;
                ultimaFilaLoop: while (sumaFrecuenciasEsperadas < 5) {
                    if (ultimaFilaASumar < cantidadDeIntervalosK) {
                        sumaFrecuenciasEsperadas += frecuenciasEsperadas.get(ultimaFilaASumar);
                        ultimaFilaASumar++;
                    } else {
                        if (filaActual != 0) {
                            filaActual -= 1;
                            break ultimaFilaLoop;
                        } else {
                            break filaActualLoop;
                        }
                    }
                }
                ultimaFilaASumar -= 1;

                cantidadDeIntervalosK -= ultimaFilaASumar - filaActual;
                limites.get(filaActual)[1] = limites.get(ultimaFilaASumar)[1];
                frecuenciasObservadas.set(filaActual, ListUtils.sumIntegerListFromIToJ(frecuenciasObservadas, filaActual, ultimaFilaASumar));
                frecuenciasEsperadas.set(filaActual, ListUtils.sumDoubleListFromIToJ(frecuenciasEsperadas, filaActual, ultimaFilaASumar));
                chiCuadradoCalculado.set(filaActual, ListUtils.sumDoubleListFromIToJ(chiCuadradoCalculado, filaActual, ultimaFilaASumar));

                limites.subList(filaActual + 1, ultimaFilaASumar + 1).clear();
                frecuenciasObservadas.subList(filaActual + 1, ultimaFilaASumar + 1).clear();
                frecuenciasEsperadas.subList(filaActual + 1, ultimaFilaASumar + 1).clear();
                chiCuadradoCalculado.subList(filaActual + 1, ultimaFilaASumar + 1).clear();
            }
        }
    }

    void showResults() {
        generateCsv();

        Label muestraLabel = new Label("Tamaño de la muestra: " + N);
        muestraLabel.setPadding(new Insets(0, 0, 10, 0));
        Label intervalosLabel = new Label("Cantidad de Intervalos Seleccionados: " + cantidadDeIntervalosK);
        intervalosLabel.setPadding(new Insets(0, 0, 10, 0));

        StringBuilder intervalosStr = new StringBuilder();
        for (Double[] intervalo : limites) {
            intervalosStr.append(Arrays.toString(intervalo)).append(" ");
        }

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER_LEFT); // Alineación a la izquierda
        vbox.setPadding(new Insets(20));

        // Crear la tabla para mostrar las frecuencias
        TableView<Frecuencia> tableView = new TableView<>();
        TableColumn<Frecuencia, String> intervaloColumn = new TableColumn<>("Intervalo");
        intervaloColumn.setCellValueFactory(new PropertyValueFactory<>("intervalo"));

        TableColumn<Frecuencia, Integer> frecuenciaObservadaColumn = new TableColumn<>("Frecuencia Observada");
        frecuenciaObservadaColumn.setCellValueFactory(new PropertyValueFactory<>("frecuenciaObservada"));

        TableColumn<Frecuencia, Double> frecuenciaEsperadaColumn = new TableColumn<>("Frecuencia Esperada");
        frecuenciaEsperadaColumn.setCellValueFactory(new PropertyValueFactory<>("frecuenciaEsperada"));

        tableView.getColumns().addAll(intervaloColumn, frecuenciaObservadaColumn, frecuenciaEsperadaColumn);

        ObservableList<Frecuencia> frecuenciasList = FXCollections.observableArrayList();
        for (int i = 0; i < cantidadDeIntervalosK; i++) {
            String intervalo = String.format("[%.4f; %.4f]", limites.get(i)[0], limites.get(i)[1]);
            int freqObservada = frecuenciasObservadas.get(i);
            double freqEsperada = frecuenciasEsperadas.get(i);
            frecuenciasList.add(new Frecuencia(intervalo, freqObservada, freqEsperada));
        }

        // Configura la tabla para mostrar solo 4 decimales en la columna de frecuencia esperada
        frecuenciaEsperadaColumn.setCellFactory(column -> {
            return new TableCell<Frecuencia, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(String.format("%.4f", item)); // Mostrar 4 decimales
                    }
                }
            };
        });

        tableView.setItems(frecuenciasList);

        // Crear el botón para mostrar el histograma
        Button histogramButton = new Button("Ver Histograma");
        histogramButton.setOnAction(e -> {
            Histogram.displayHistogram(limites, frecuenciasObservadas);
        });

        // Crear el botón para volver
        Button volverButton = new Button("Volver al Inicio");
        volverButton.setOnAction(e -> {
            Stage currentStage = (Stage) volverButton.getScene().getWindow();
            currentStage.close();
            try {
                new App().start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Agregar las etiquetas, la tabla y los botones al VBox
        vbox.getChildren().addAll(
                muestraLabel,
                intervalosLabel,
                new Label("Mínimo: " + String.format("%.4f", min)),
                new Label("Máximo: " + String.format("%.4f", max)),
                new Label("Rango: " + String.format("%.4f", rango)),
                new Label("Amplitud: " + String.format("%.4f", amplitud)),
                new Label("Media: " + String.format("%.4f", media)),
                new Label("Varianza: " + String.format("%.4f", varianza)),
                new Label("Chi Cuadrado: " + String.format("%.4f", ListUtils.sumList(chiCuadradoCalculado))),
                tableView,
                histogramButton,
                volverButton
        );

        // Crear y mostrar la escena
        Scene scene = new Scene(vbox, 800, 600);
        Stage window = new Stage();
        window.setScene(scene);
        window.setTitle("Resultados del Análisis");
        window.show();

        CSVUtils.abrirCSV((csvFilePath));
    }



    private void generateCsv() {
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath))) {
            writer.writeNext(new String[]{"Random (0;1)", "Random Distribucion"});

            for (int i = 0; i < rnd01.length; i++) {
                String formattedRnd01 = String.format("%.4f", rnd01[i]);
                String formattedRndDistribution = String.format("%.4f", rndDistribution[i]);
                writer.writeNext(new String[]{formattedRnd01, formattedRndDistribution});
            }
            System.out.println("Números aleatorios generados y guardados en el archivo CSV: " + csvFilePath);
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo CSV: " + e.getMessage());
        }
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Frecuencia {
        String intervalo;
        int frecuenciaObservada;
        double frecuenciaEsperada;

        public Frecuencia(String intervalo, int frecuenciaObservada, double frecuenciaEsperada) {
            this.intervalo = intervalo;
            this.frecuenciaObservada = frecuenciaObservada;
            this.frecuenciaEsperada = frecuenciaEsperada;
        }
    }
}