package org.example.simulation;

import java.util.Random;

public class VectorEstado {
    private int totalYaCompraron = 0;
    private int totalProbablesCompra = 0;
    private int totalInteresadosProbarlo = 0;
    private int totalSimulaciones = 0;

    // Método para calcular un valor RND entre 0 y 1
    private static double calcularRandom() {
        Random random = new Random();
        double rnd = random.nextDouble();
        String rndString = String.format("%.2f", rnd).replace(",", ".");
        return Double.parseDouble(rndString);
    }

    // Obtener el porcentaje de compradores sobre el total de simulaciones
    public double getPorcentajeCompradores() {
        return (double) totalYaCompraron / totalSimulaciones * 100;
    }

    // Obtener la probabilidad de que un adulto compre el perfume
    public double getProbabilidadCompraAdulto() {
        int totalPosiblesCompradores = totalYaCompraron + totalProbablesCompra + totalInteresadosProbarlo;
        return (double) totalYaCompraron / totalPosiblesCompradores;
    }

    // Método para determinar la categoría de la persona basada en el valor aleatorio generado
    private static int getcategory(double rndGen) {
        int category;
        if (rndGen < 0.30) {
            category = 1; // probPersNoResponde
        } else if (rndGen < 0.40) {
            category = 2; // probPersNoUsuariaInternet
        } else if (rndGen < 0.50) {
            category = 3; // probPersAdultaNoRecuerdaFlayer
        } else if (rndGen < 0.80) {
            category = 4; // probPersAdultaRecuerdaFlayer
        } else if (rndGen < 0.95) {
            category = 5; // probPersNoAdultaNoRecuerdaFlayer
        } else {
            category = 6; // probPersNoAdultaRecuerdaFlayer
        }
        return category;
    }

    // Método para contar las personas que ya compraron basado en la categoría
    private static int getContYaComprado(int category, double rnd) {
        int totalYaCompraron = 0;

        switch (category) {
            case 3: // probPersAdultaNoRecuerdaFlayer
                if (rnd < 0.02) {
                    totalYaCompraron++;
                }
                break;
            case 4: // probPersAdultaRecuerdaFlayer
                if (rnd < 0.20) {
                    totalYaCompraron++;
                }
                break;
            default:
                break;
        }
        return totalYaCompraron;
    }

    // Método para contar las personas que probablemente comprarán basado en la categoría
    private static int getContProbableCompra(int category, double rnd) {
        int totalProbablesCompra = 0;

        switch (category) {
            case 3: // probPersAdultaNoRecuerdaFlayer
                if (rnd < 0.20) {
                    totalProbablesCompra++;
                }
                break;
            case 4: // probPersAdultaRecuerdaFlayer
                if (rnd < 0.70) {
                    totalProbablesCompra++;
                }
                break;
            default:
                break;
        }
        return totalProbablesCompra;
    }

    // Método para calcular el porcentaje
    private static double calcularPorcentaje(int total, int muestras) {
        return (double) total / muestras * 100;
    }

    // Método para generar vectores según par o impar
    public static double[][] generadorVectoresParImpar(int N, int i, int j) {
        Random random = new Random();

        // Variables para almacenar los totales de cada tipo de compra
        int totalProbablesCompra = 0;
        int totalInteresadosProbarlo = 0;
        int totalSimulaciones = 0;

        // Creación de los vectores
        double[] vectorPar = new double[8];
        double[] vectorImpar = new double[8];

        // Creación de la matriz para almacenar la información de las columnas
        double[][] matriz = new double[j + i < N ? i + 1 : i][8];
        double[] ultimaFila = null;

        // Variables para contar los que ya compraron y los probables compradores
        int contadorYaComprado = 0;
        int contadorProbableCompra = 0;

        // Bucle para generar los valores aleatorios
        for (int muestras = 0; muestras < N; muestras++) {
            double rndGen;
            double rnd;
            int acumYaComprado;
            int acumProbableCompra;

            if (muestras == 0) {
                rndGen = calcularRandom();
                int category = getcategory(rndGen);
                if(category ==3){
                    rnd = calcularRandom();
                    contadorYaComprado = getContYaComprado(category, rnd);
                    contadorProbableCompra = getContProbableCompra(category, rnd);
                }else if(category == 4){
                    rnd = calcularRandom();
                    contadorYaComprado = getContYaComprado(category, rnd);
                    contadorProbableCompra = getContProbableCompra(category, rnd);
                }else {
                    contadorYaComprado = 0;
                    contadorProbableCompra = 0;
                }

                acumYaComprado = contadorYaComprado;
                acumProbableCompra = contadorProbableCompra;
            } else {
                double[] vectorAnterior = muestras % 2 == 0 ? vectorImpar : vectorPar;

                rndGen = calcularRandom();
                int category = getcategory(rndGen);
                if(category ==3){
                    rnd = calcularRandom();
                    contadorYaComprado = getContYaComprado(category, rnd);
                    contadorProbableCompra = getContProbableCompra(category, rnd);
                }else if(category == 4){
                    rnd = calcularRandom();
                    contadorYaComprado = getContYaComprado(category, rnd);
                    contadorProbableCompra = getContProbableCompra(category, rnd);
                }else {
                    contadorYaComprado = 0;
                    contadorProbableCompra = 0;
                }
                acumYaComprado = contadorYaComprado + (int) vectorAnterior[6];
                acumProbableCompra = contadorProbableCompra + (int) vectorAnterior[7];
                contadorYaComprado = 0;
                contadorProbableCompra = 0;
            }

            // Calcular el porcentaje de los que ya compraron
            double porcentajeYaComprado = calcularPorcentaje(acumYaComprado, muestras + 1);

            //calcular la probabilidad de los que probablemente compraran
            double promedioProbableCompra = calcularPorcentaje(acumProbableCompra, muestras + 1);


            double[] vectorActual = {
                    muestras,
                    rndGen,
                    contadorYaComprado,
                    contadorProbableCompra,
                    porcentajeYaComprado,
                    promedioProbableCompra,
                    acumYaComprado,
                    acumProbableCompra
            };



            // Asignación de los valores a los vectores según la muestra
            if (muestras % 2 == 0) {
                vectorPar[6] = acumYaComprado;
                vectorPar[7] = acumProbableCompra;
                matriz[muestras / 2] = vectorPar.clone();
            } else {
                vectorImpar[6] = acumYaComprado;
                vectorImpar[7] = acumProbableCompra;
                matriz[muestras / 2] = vectorImpar.clone();
            }
        }

        return matriz;
    }
}
