package org.example.simulation;
import java.util.Random;
public class montecarlo {
    public static double simuladorMontecarlo(double rndGen){
        double rndGen =
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
