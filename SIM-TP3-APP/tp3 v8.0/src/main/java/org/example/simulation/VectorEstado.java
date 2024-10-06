package org.example.simulation;

import java.util.Random;
import static org.example.simulation.Excel.crearExcel;

public class VectorEstado {
    private int totalYaCompraron = 0;
    private int totalProbablesCompra = 0;
    private int totalInteresadosProbarlo = 0;
    private int totalSimulaciones = 0;
    private double probNegarse;
    private double probNoInternet;
    private double probAdultoNoRecuerda;
    private double probAdultoRecuerda;
    private double probNoAdultoRecuerda;
    private double probNoAdultoNoRecuerda;
    private double probCompraRecuerda;
    private double probProbableRecuerda;
    private double probPruebaRecuerda;
    private double probCompraNoRecuerda;
    private double probProbableNoRecuerda;
    private double probPruebaNoRecuerda;

    public VectorEstado(double probNegarse, double probNoInternet, double probAdultoNoRecuerda,
                        double probAdultoRecuerda, double probNoAdultoRecuerda,
                        double probNoAdultoNoRecuerda, double probCompraRecuerda,
                        double probProbableRecuerda, double probPruebaRecuerda,
                        double probCompraNoRecuerda, double probProbableNoRecuerda,
                        double probPruebaNoRecuerda) {
        this.probNegarse = probNegarse;
        this.probNoInternet = probNoInternet;
        this.probAdultoNoRecuerda = probAdultoNoRecuerda;
        this.probAdultoRecuerda = probAdultoRecuerda;
        this.probNoAdultoRecuerda = probNoAdultoRecuerda;
        this.probNoAdultoNoRecuerda = probNoAdultoNoRecuerda;
        this.probCompraRecuerda = probCompraRecuerda;
        this.probProbableRecuerda = probProbableRecuerda;
        this.probPruebaRecuerda = probPruebaRecuerda;
        this.probCompraNoRecuerda = probCompraNoRecuerda;
        this.probProbableNoRecuerda = probProbableNoRecuerda;
        this.probPruebaNoRecuerda = probPruebaNoRecuerda;

        // para ver si estan llegando bien las probabilidades de la interfaz
        System.out.println("Probabilidades inicializadas:");
        System.out.println("Negarse: " + probNegarse);
        System.out.println("No usar internet: " + probNoInternet);
        System.out.println("Adulto no recuerda: " + probAdultoNoRecuerda);
        System.out.println("Adulto recuerda: " + probAdultoRecuerda);
        System.out.println("No adulto recuerda: " + probNoAdultoRecuerda);
        System.out.println("No adulto no recuerda: " + probNoAdultoNoRecuerda);
        System.out.println("Compra recuerda: " + probCompraRecuerda);
        System.out.println("Probable compra recuerda: " + probProbableRecuerda);
        System.out.println("Prueba recuerda: " + probPruebaRecuerda);
        System.out.println("Compra no recuerda: " + probCompraNoRecuerda);
        System.out.println("Probable compra no recuerda: " + probProbableNoRecuerda);
        System.out.println("Prueba no recuerda: " + probPruebaNoRecuerda);
    }

    // Método para calcular un valor RND entre 0 y 1
    private static double calcularRandom() {
        Random random = new Random();
        return random.nextDouble();
    }



    // Método para determinar la categoría de la persona basada en el valor aleatorio generado
    private int getCategory(double rndGen) {
        int category;
        if (rndGen < probNegarse) {
            category = 1; // probPersNoResponde
        } else if ( rndGen < (probNoInternet + probNegarse)) {
            category = 2; // probPersNoUsuariaInternet
        } else if (rndGen < (probNoInternet + probNegarse + probAdultoNoRecuerda)) {
            category = 3; // probPersAdultaNoRecuerdaFlayer
        } else if ( rndGen < (probNegarse + probNoInternet + probAdultoNoRecuerda + probAdultoRecuerda)) {
            category = 4; // probPersAdultaRecuerdaFlayer
        } else if (rndGen < (probNegarse + probNoInternet + probAdultoNoRecuerda + probAdultoRecuerda + probNoAdultoRecuerda)) {
            category = 5; // probPersNoAdultaNoRecuerdaFlayer
        } else {
            category = 6; // probPersNoAdultaRecuerdaFlayer
        }
        return category;
    }

    // Método para contar las personas que ya compraron basado en la categoría
    private int getContYaComprado(int category, double rnd1, double rnd2) {
        int totalYaCompraron = 0;

        switch (category) {
            case 3: // probPersAdultaNoRecuerdaFlayer
                if (rnd1 < probCompraNoRecuerda) {
                    totalYaCompraron++;
                }
                break;
            case 4: // probPersAdultaRecuerdaFlayer
                if (rnd2 < probCompraRecuerda) {
                    totalYaCompraron++;
                }
                break;
            default:
                break;
        }
        return totalYaCompraron;
    }

    // Método para contar las personas que probablemente comprarán basado en la categoría
    private int getContProbableCompra(int category, double rnd1, double rnd2) {
        int totalProbablesCompra = 0;

        switch (category) {
            case 3: // probPersAdultaNoRecuerdaFlayer
                if (rnd1 >= probCompraNoRecuerda & rnd1 < (probCompraNoRecuerda + probProbableNoRecuerda)) {
                    totalProbablesCompra++;
                }
                break;
            case 4: // probPersAdultaRecuerdaFlayer
                if (rnd2 >= probCompraRecuerda & rnd2 < (probCompraRecuerda + probProbableRecuerda)) {
                    totalProbablesCompra++;
                }
                break;
            default:
                break;
        }
        return totalProbablesCompra;
    }

    // Método para calcular el porcentaje
    private double calcularPorcentaje(int total, int muestras) {
        return (double) total / muestras * 100;
    }

    // Método para calcular el promedio
    private double calcularPromedio(int total, int muestras) {
        return (double) total / muestras;
    }

    public double[][] generadorVectoresParImpar(int N, int i, int j) {
        // Asegurarse de que 'j + i' no sea mayor que 'N'
        if (j + i > N) {
            throw new IllegalArgumentException("La suma de i y j no puede ser mayor que N");
        }

        // Inicializar la matriz con el tamaño correcto
        double[][] matriz = new double[i + 1][10]; // `i + 1` para incluir la fila de ceros

        matriz[0] = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // Fila de inicialización

        int acumuladorYaComprado = 0;
        int acumuladorProbableCompra = 0;

        // Bucle para generar valores aleatorios
        for (int muestras = 0; muestras < N; muestras++) {
            double rndGen = calcularRandom();
            int category = getCategory(rndGen);
            double rnd1 = calcularRandom();
            double rnd2 = calcularRandom();
            int contadorYaComprado = getContYaComprado(category, rnd1, rnd2);
            int contadorProbableCompra = getContProbableCompra(category, rnd1, rnd2);

            acumuladorYaComprado += contadorYaComprado;
            acumuladorProbableCompra += contadorProbableCompra;

            // Calcular porcentajes
            double porcentajeYaComprado = calcularPorcentaje(acumuladorYaComprado, muestras );
            double promedioProbableCompra = (calcularPorcentaje(acumuladorProbableCompra, muestras )/100);

            double[] vectorActual = {
                    muestras,
                    rndGen,
                    rnd1,
                    rnd2,
                    contadorYaComprado,
                    acumuladorYaComprado,
                    contadorProbableCompra,
                    acumuladorProbableCompra,
                    porcentajeYaComprado,
                    promedioProbableCompra
            };

            // Guardar los valores a partir de la segunda fila, dejando la primera para la fila de inicialización
            if (muestras >= j && muestras < j + i) {
                matriz[muestras - j + 1 ] = vectorActual; // +1 para dejar la fila de ceros en la primera posición
            }
        }

        return matriz;
}


//aca generas matriz sin limitaciones
    public double[][] generadorVectoresCompleto(int N) {
            // Inicializar la matriz con el tamaño N
            double[][] matriz = new double[N][10]; // Cada fila tiene 8 valores

            int acumuladorYaComprado = 0;
            int acumuladorProbableCompra = 0;

            // Bucle para generar valores aleatorios
            for (int muestras = 0; muestras < N; muestras++) {
                double rndGen = calcularRandom();
                int category = getCategory(rndGen);
                double rnd1 = calcularRandom();
                double rnd2 = calcularRandom();
                int contadorYaComprado = getContYaComprado(category, rnd1,rnd2);
                int contadorProbableCompra = getContProbableCompra(category, rnd1,rnd2);

                acumuladorYaComprado += contadorYaComprado;
                acumuladorProbableCompra += contadorProbableCompra;

                // Calcular  porcentaje y promedio
                double porcentajeYaComprado = calcularPorcentaje(acumuladorYaComprado, muestras);
                double promedioProbableCompra = calcularPromedio(acumuladorProbableCompra, muestras);

                // Llenar la matriz con los datos generados
                matriz[muestras] = new double[]{
                        muestras,
                        rndGen,
                        rnd1,
                        rnd2,
                        contadorYaComprado,
                        acumuladorYaComprado,
                        contadorProbableCompra,
                        acumuladorProbableCompra,
                        porcentajeYaComprado,
                        promedioProbableCompra
                };
            }

            return matriz;
    }

    // Función para limitar la matriz generada
    public double[][] limitarMatriz(double[][] matrizCompleta, int i, int j) {
        // Asegurarse de que 'j + i' no sea mayor que el tamaño de la matriz completa
        if (j + i > matrizCompleta.length) {
            throw new IllegalArgumentException("La suma de i y j no puede ser mayor que el tamaño de la matriz completa");
        }

        // Crear una nueva matriz limitada, con espacio para 'i + 1' filas (incluyendo la fila de ceros)
        double[][] matrizLimitada = new double[i + 1][10];

        // Fila de inicialización de ceros
        matrizLimitada[0] = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // Fila de inicialización

        // Copiar las filas de la matriz completa que cumplen la condición (entre j y j+i)
        for (int muestras = j; muestras < j + i; muestras++) {
            matrizLimitada[muestras - j + 1] = matrizCompleta[muestras];
        }

        return matrizLimitada;
    }



}