/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr1.rede.social;

import java.text.DecimalFormat;
import lapr1.rede.social.LAPR1RedeSocial;
import static lapr1.rede.social.LAPR1RedeSocial.calcActualConnections;
import static lapr1.rede.social.LAPR1RedeSocial.calculateAverageDegree;
import static lapr1.rede.social.LAPR1RedeSocial.calculateDensity;
import static lapr1.rede.social.LAPR1RedeSocial.determinarCentralidadeVetorProprio;
import static lapr1.rede.social.LAPR1RedeSocial.multiplyMatrices;
import static lapr1.rede.social.LAPR1RedeSocial.nodeDegree;
import static lapr1.rede.social.LAPR1RedeSocial.powerAdjacencyMatrix;


public class Testes_Unitarios {

    public static final double[][] BRANCHESMATRIX = {
        {0, 1, 0, 1},
        {1, 0, 1, 1},
        {0, 1, 0, 0},
        {1, 1, 0, 0}
    };
    public static final double[] NODEDEGREERESAULT = new double[BRANCHESMATRIX.length];
    public static final double[] EIGENVALUECENTRALITYEXPECTED = {0.523, 0.612, 0.282, 0.523};
    public static final double[] EIGENVALUECENTRALITY = new double[BRANCHESMATRIX.length];
    public static final double[] EXPECTEDNODEDEGREE = {2, 3, 1, 2};
    public static final double DENSITYEXPECTED = 66.667;
    public static final double AVERAGEDEGREEEXPECTED = 2.000;
    public static final int EXPONENT = 2;
    public static final double[][] POWERADJACENCYMATRIX = new double[BRANCHESMATRIX.length][BRANCHESMATRIX.length];
    public static final double[][] POWERADJACENCYMATRIXEXPECTED = {
        {2, 1, 1, 1},
        {1, 3, 0, 1},
        {1, 0, 1, 1},
        {1, 1, 1, 2}
    };
    public static DecimalFormat df = new DecimalFormat("#.###");

    public static void main(String[] args) {
        

        //Teste para Grau do Nó
        System.out.println("Grau do Nó:\n");
        boolean[] testNodeDegree = test_nodeDegree(EXPECTEDNODEDEGREE, BRANCHESMATRIX, NODEDEGREERESAULT);
        listArrayBoolean(testNodeDegree);
        System.out.println();

        //Teste para Centralidade do Vetor
        System.out.println("Centralidade do Vetor:\n");
        boolean[] testEigenValueCentrality = test_EigenValueCentrality(BRANCHESMATRIX, BRANCHESMATRIX.length, EIGENVALUECENTRALITY, EIGENVALUECENTRALITYEXPECTED);
        listArrayBoolean(testEigenValueCentrality);
        System.out.println();

        //Teste para Grau Médio
        System.out.println("Grau Médio:\n");
        boolean testAverageDegree = test_calculateAverageDegree(NODEDEGREERESAULT, AVERAGEDEGREEEXPECTED);
        System.out.println(testAverageDegree);
        System.out.println();
        
        //Teste para Densidade
        System.out.println("Densidade:\n");
        double actualConnections = calcActualConnections(BRANCHESMATRIX);
        double density = 0;
        boolean testDensity = test_Density(DENSITYEXPECTED, BRANCHESMATRIX.length, actualConnections, density);
        System.out.println(testDensity);
        System.out.println();
        
        //Teste para Potência de Matrix
        System.out.println("Potência de Matrix " + EXPONENT+ ":\n");
        boolean testPowerAdjacency[][];
        testPowerAdjacency = test_powerAdjacencyMatrix(BRANCHESMATRIX, EXPONENT, POWERADJACENCYMATRIXEXPECTED, POWERADJACENCYMATRIXEXPECTED, BRANCHESMATRIX.length);
        listMatrixBoolean(testPowerAdjacency);
    }
     //Teste para grau do nó
    public static boolean[] test_nodeDegree(double[] expectedMatrix, double[][] branchesMatrix, double[] nodeDegreeResult) {
        int nElem = nodeDegree(branchesMatrix, nodeDegreeResult, branchesMatrix.length);
        boolean test[] = new boolean[nElem];
        for (int i = 0; i < nElem; i++) {
            if (expectedMatrix[i] == nodeDegreeResult[i]) {
                test[i] = true;
            }
        }
        return test;
    }
    //Teste para centralidade do vetor próprio
    public static boolean[] test_EigenValueCentrality(double[][] branchesMatrix, int nElem, double[] eigenValueCentrality, double[] centralityValuesExpected) {
        determinarCentralidadeVetorProprio(BRANCHESMATRIX, nElem, EIGENVALUECENTRALITY);
        boolean test[] = new boolean[nElem];
        for (int i = 0; i < nElem; i++) {
            if (centralityValuesExpected[i] == eigenValueCentrality[i]) {
                test[i] = true;
            }
        }
        return test;
    }

    // Teste para grau médio
    public static boolean test_calculateAverageDegree(double[] degreesGroup, double averageDegreeExpected) {
        double averageDegree = calculateAverageDegree(degreesGroup);
        if (averageDegree == averageDegreeExpected) {
            return true;
        }
        return false;
    }

    //Teste para densidade
    public static boolean test_Density(double expectedValue, int nElem, double actualConnections, double density) {
        boolean test = false;
        density = calculateDensity(density, nElem, actualConnections);
        String densityValue = df.format(density);
        double x = Double.parseDouble(densityValue.replace(",", "."));
        if (x == expectedValue) {
            test = true;
        }
        return test;
    }

    //Teste para potência de matriz de Adjacências
    public static boolean[][] test_powerAdjacencyMatrix(double[][] adjacencyMatrix, int exponent, double[][] powerAdjacencyMatrix, double[][] powerAdjacencyMatrixExpected, int nElem) {
        boolean[][] test = new boolean[nElem][nElem];

        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix[0].length; j++) {
                if (powerAdjacencyMatrix[i][j] == powerAdjacencyMatrixExpected[i][j]) {
                    test[i][j] = true;
                }
            }
        }
        return test;
    }

    public static boolean test_multiplyMatrices(double[][] testOneMatrix, double[][] testTwoMatrix, double[][] multipliedMatrixExpected, int nElem) {
        double[][] testResult = multiplyMatrices(testOneMatrix, testTwoMatrix, nElem);
        if (compareMatrices(testResult, multipliedMatrixExpected)) {
            return true;
        }
        return false;
    }

    private static boolean compareMatrices(double[][] adjacencyMatrix, double[][] powerAdjacencyMatrixResult) {
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix[0].length; j++) {
                if (adjacencyMatrix[i][j] != powerAdjacencyMatrixResult[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    public static void listArrayBoolean(boolean[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }

    public static void listMatrixBoolean(boolean[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.println(matrix[i][j] + " ");
            }
        }

    }
}
