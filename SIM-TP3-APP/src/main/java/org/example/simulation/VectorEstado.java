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
    private static double getContYaComprado(int category,double rnd){
        int totalYaCompraron = 0;

        switch (category) {
            case 3: // probPersAdultaNoRecuerdaFlayer
                double randAdultaNoRecuerda = rnd;
                if (randAdultaNoRecuerda < 0.02) {
                    totalYaCompraron++;
                }
                break;
            case 4: // probPersAdultaRecuerdaFlayer
                double randAdultaRecuerda = rnd;
                if (randAdultaRecuerda < 0.20) {
                    totalYaCompraron++;
                }
                break;
            default:
                break;
        return totalYaCompraron;

    }
    private static double getContProbableCompra(int category, double rnd){
        int totalProbablesCompra = 0;

        switch (category) {
            case 3: // probPersAdultaNoRecuerdaFlayer
                double randAdultaNoRecuerda = rnd;
                if (randAdultaNoRecuerda < 0.20) {
                    totalProbablesCompra++;
                }
                break;
            case 4: // probPersAdultaRecuerdaFlayer
                double randAdultaRecuerda = rnd;
                if (randAdultaRecuerda < 0.70) {
                    totalProbablesCompra++;
                }
                break;
            default:
                break;
            return totalProbablesCompra;
        }
    }

    public static double[][] generadorVectoresParImpar(int N, int i, int j){
        Random random = new Random();

         int totalProbablesCompra = 0;
         int totalInteresadosProbarlo = 0;
         int totalSimulaciones = 0;
        // Creación de los vectores
        double[] vectorPar = new double[8];
        double[] vectorImpar = new double[8];

        // Creación de la matriz para almacenar la información de las columnas
        double[][] matriz = new double[j + i < N ? i + 1 : i][8];
        double[] ultimaFila = null;

        // Generación de valores aleatorios para los vectore
        int contadorYaComprado = 0;
        int contadorProbableCompra = 0;
        for (int muestras = 0; muestras < N; muestras++) {
           double rndGen;
           double rnd;
           int acumYaComprado;
           int acumProbableCompra;

           if(muestras == 0) {
               rndGen = calcularRandom();
               category = getcategory(rndGen);
               if (category == 3) {
                   int category3 = 3;
                   rnd = calcularRandom();
                   int contadorYaComprado = getContYaComprado(category3, rnd);
                   int contadorProbableCompra = getContProbableCompra(category3, rnd);

               } else if (category == 4) {
                   rnd = calcularRandom();
                   int category4 = 4;
                   int contadorYaComprado = getContYaComprado(category4, rnd);
                   int contadorProbableCompra = getContProbableCompra(category4, rnd);
               }
               acumYaComprado = contadorYaComprado;
               acumProbableCompra = contadorProbableCompra;
           }else {
               double[] vectorAnterior = muestras % 2 == 0 ? vectorImpar : vectorPar;

               rndGen = calcularRandom();
               category = getcategory(rndGen);
               if (category == 3) {
                   int category3 = 3;
                   rnd = calcularRandom();
                   int contadorYaComprado = getContYaComprado(category3, rnd);
                   int contadorProbableCompra = getContProbableCompra(category3, rnd);

               } else if (category == 4) {
                   rnd = calcularRandom();
                   int category4 = 4;
                   int contadorYaComprado = getContYaComprado(category4, rnd);
                   int contadorProbableCompra = getContProbableCompra(category4, rnd);
               }
               acumYaComprado = contadorYaComprado + vectorAnterior[6];
               acumProbableCompra = contadorProbableCompra + vectorAnterior[7];
           }
           double porcentajeYaComprado = calcularPorcentaje();
        }








    }



}
