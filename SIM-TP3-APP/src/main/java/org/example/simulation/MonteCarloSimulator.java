package org.example.simulation;

import java.util.Random;

public class MonteCarloSimulator {
    private double probPersNoResponde;
    private double probPersNoUsuariaInernet;
    private double probPersAdultaNoRecuerdaFlayer;
    private double probPersAdultaRecuerdaFlayer;
    private double probPersNoAdultaNoRecuerdaFlayer;
    private double probPersNoAdultaRecuerdaFlayer;

    private double probYaCompraron;
    private double probProbableCompra;
    private double probSoloProbarlo;


    private int totalYaCompraron = 0;
    private int totalProbablesCompra = 0;
    private int totalInteresadosProbarlo = 0;
    private int totalSimulaciones = 0;

    public MonteCarloSimulator(double probPersNoResponde,
                               double probPersNoUsuariaInernet,
                               double probPersAdultaNoRecuerdaFlayer,
                               double probPersAdultaRecuerdaFlayer,
                               double probPersNoAdultaNoRecuerdaFlayer,
                               double probPersNoAdultaRecuerdaFlayer,
                               double probYaCompraron,
                               double probProbableCompra,
                               double probSoloProbarlo) {
        this.probPersNoResponde = probPersNoResponde;
        this.probPersNoUsuariaInernet = probPersNoUsuariaInernet;
        this.probPersAdultaNoRecuerdaFlayer = probPersAdultaNoRecuerdaFlayer;
        this.probPersAdultaRecuerdaFlayer = probPersAdultaRecuerdaFlayer;
        this.probPersNoAdultaNoRecuerdaFlayer = probPersNoAdultaNoRecuerdaFlayer;
        this.probPersNoAdultaRecuerdaFlayer = probPersNoAdultaRecuerdaFlayer;
        this.probYaCompraron = probYaCompraron;
        this.probProbableCompra = probProbableCompra;
        this.probSoloProbarlo = probSoloProbarlo;

    }

    public void ejecutarSimulacion1(int N) {
        Random random = new Random();
        totalSimulaciones = N;

        for (int n = 0; n < N; n++) {
            double rndGen = random.nextDouble();
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
            // Manejo de la categoría con switch
            switch (category) {
                case 3: // probPersAdultaNoRecuerdaFlayer
                    double randAdultaNoRecuerda = random.nextDouble();
                    if (randAdultaNoRecuerda < 0.02) {
                        totalYaCompraron++;
                    } else if (randAdultaNoRecuerda < 0.20) {
                        totalProbablesCompra++;
                    } else {
                        totalInteresadosProbarlo++;
                    }
                    break;
                case 4: // probPersAdultaRecuerdaFlayer
                    double randAdultaRecuerda = random.nextDouble();
                    if (randAdultaRecuerda < 0.20) {
                        totalYaCompraron++;
                    } else if (randAdultaRecuerda < 0.70) {
                        totalProbablesCompra++;
                    } else {
                        totalInteresadosProbarlo++;
                    }
                    break;
                default:
                    break;

            }
        }
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

    public int getTotalCompradores() {
        return totalYaCompraron;
    }

    public int getTotalProbablesCompradores() {
        return totalProbablesCompra;
    }

    public int getTotalInteresadosPrueba() {
        return totalInteresadosProbarlo;
    }
}
