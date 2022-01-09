/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr1.rede.social;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Scanner;
import org.la4j.Matrix;
import org.la4j.decomposition.EigenDecompositor;
import org.la4j.matrix.dense.Basic2DMatrix;

public class LAPR1RedeSocial {

    /**
     * @param args the command line arguments
     */
    public static final int INDICE_VETORES_PROPRIOS = 0;
    public static final int INDICE_VALORES_PROPRIOS = 1;
    public static final int NUMERO_MAXIMO_NOS = 200;
    public static final int N_COLUNAS_FICHEIRO_NOS = 4;
    public static Formatter out;
    public static Scanner read = new Scanner(System.in);

    public static void main(String[] args) throws FileNotFoundException {
        if (args[0].equals("-n")) {
            out = new Formatter(System.out);
            String fileNodes = args[1];
            String fileBranches = args[2];
            String[][] nodesMatrix = new String[NUMERO_MAXIMO_NOS][N_COLUNAS_FICHEIRO_NOS];
            double[][] branchesMatrix = new double[NUMERO_MAXIMO_NOS][NUMERO_MAXIMO_NOS];
            try {
                boolean check = true;
                int nElem = 0;
                int nodes;
                check = checkFileFormat(fileNodes);
                if (check) {
                    check = checkFileFormat(fileBranches);
                    if (check) {
                        nElem = fileNodesMatrix(fileNodes, nElem, nodesMatrix);

                        if (nElem <= NUMERO_MAXIMO_NOS) {
                            nElem = fileBranchesMatrix(fileBranches, nElem, branchesMatrix);
                            if (nElem != -1) {

                                listStringMatrix(nodesMatrix, nElem);
                                out.format("%n");
                                listMatrixDouble(branchesMatrix, nElem);
                                out.format("%n");

                                double[] nodeDegreeArray = new double[nElem];
                                double[][] powerAdjacencyMatrixResult;
                                double[] eigenValueCentrality = new double[nElem];
                                double averageDegree, actualConnections;
                                double density = 0;
                                int exponent, linhaCentralidadeDoVetorProprio;
                                String op;
                                do {
                                    op = menu();
                                    switch (op) {
                                        case "1":
                                            out.format("%n");
                                            out.format("%s%n", "Graus dos nós:");
                                            nodes = nodeDegree(branchesMatrix, nodeDegreeArray, nElem);
                                            listArrayDouble(nodeDegreeArray, nElem, nodesMatrix);

                                            break;

                                        case "2":
                                            out.format("%n%s%n", "Centralidade dos nós:");
                                            determinarCentralidadeVetorProprio(branchesMatrix, nElem, eigenValueCentrality);
                                            listArrayDoubleCentralidade(eigenValueCentrality, nElem, nodesMatrix);
                                            double maior = getMax(eigenValueCentrality, nElem);
                                            int pos = getPosition(eigenValueCentrality, maior, nElem);
                                            out.format("%n%s%s%s%.3f", "O nó com maior centralidade é: ", nodesMatrix[pos][1], " : ", maior);
                                            break;

                                        case "3":
                                            out.format("%n");
                                            averageDegree = calculateAverageDegree(nodeDegreeArray);
                                            if (averageDegree == 0) {
                                                out.format("%s", "Ainda não foi determinado o número de nós para esta opção.");
                                            } else {

                                                out.format("%s%.3f", "Grau médio: ", averageDegree);
                                            }
                                            break;

                                        case "4":
                                            out.format("%n");
                                            actualConnections = calcActualConnections(branchesMatrix);
                                            density = calculateDensity(density, nElem, actualConnections);
                                            out.format("%s%.3f%s", "Densidade: ", density, "%");
                                            break;

                                        case "5":

                                            exponent = askExponent();
                                            powerAdjacencyMatrixResult = powerAdjacencyMatrix(branchesMatrix, exponent, nElem);

                                            break;

                                        case "0":
                                            op = terminar(op);
                                            break;

                                        default:
                                            out.format("%s", "Opção incorreta. Repita");
                                            break;
                                    }
                                } while (!op.equals("0"));
                            }
                        } else {

                            out.format("%s", "O número máximo de nós é 200");

                        }

                    }
                }
            } catch (FileNotFoundException exception) {
                System.out.println("Ficheiro não encontrado.");
            }
        } else {
            if (args[0].equals("-t") && args[1].equals("-k")) {
                try{
                String exponent = args[2];
                int convertedExponent = verifyExponent(exponent);
                if (convertedExponent != -1) {

                    String fileNodes = args[3];
                    String fileBranches = args[4];
                    String[][] nodesMatrix = new String[NUMERO_MAXIMO_NOS][N_COLUNAS_FICHEIRO_NOS];
                    double[][] branchesMatrix = new double[NUMERO_MAXIMO_NOS][NUMERO_MAXIMO_NOS];

                    boolean check = true;
                    int nElem = 0;
                    int nodes;
                    check = checkFileFormat(fileNodes);
                    if (check) {
                        check = checkFileFormat(fileBranches);
                        if (check) {
                            nElem = fileNodesMatrix(fileNodes, nElem, nodesMatrix);
                            if (nElem <= 200) {
                                nElem = fileBranchesMatrix(fileBranches, nElem, branchesMatrix);
                                if (nElem != -1) {

                                    String fileNameData = fileNameOut(fileNodes);

                                    out = new Formatter(new File("out_" + fileNameData + ".txt"));
                                    out.format("%n              Métricas de todo o programa             %n");

                                    listStringMatrix(nodesMatrix, nElem);
                                    out.format("%n");
                                    listMatrixDouble(branchesMatrix, nElem);
                                    out.format("%n");

                                    double[] nodeDegreeArray = new double[nElem];
                                    double[][] powerAdjacencyMatrixResult;
                                    double[] eigenValueCentrality = new double[nElem];
                                    double averageDegree, actualConnections;
                                    double density = 0;

                                    //Métrica1
                                    out.format("%n");
                                    out.format("%n%s%n", "Grau de nós:");

                                    nodes = nodeDegree(branchesMatrix, nodeDegreeArray, nElem);
                                    listArrayDoubleCentralidade(nodeDegreeArray, nElem, nodesMatrix);

                                    //Métrica2
                                    determinarCentralidadeVetorProprio(branchesMatrix, nElem, eigenValueCentrality);
                                    
                                    out.format("%n");
                                    out.format("%n%s%n", "Centralidade dos nós:");
                                    listArrayDoubleCentralidade(eigenValueCentrality, nElem, nodesMatrix);
                                    double maior = getMax(eigenValueCentrality, nElem);
                                    int pos = getPosition(eigenValueCentrality, maior, nElem);
                                    out.format("%n%s%s%s%.3f", "O nó com maior centralidade é: ", nodesMatrix[pos][1], " : ", maior);
                                
                                    //Métrica3
                                    out.format("%n");

                                    out.format("%n%s%n", "Grau médio de nós:");

                                    averageDegree = calculateAverageDegree(nodeDegreeArray);
                                    out.format("%.3f", averageDegree);

                                    //Métrica4
                                    out.format("%n");
                                    out.format("%n%s%n", "Densidade:\n");

                                    actualConnections = calcActualConnections(branchesMatrix);
                                    density = calculateDensity(density, nElem, actualConnections);
                                    out.format("%.3f%s", density, "%");

                                    //Métrica5
                                    out.format("%n");
                                    out.format("%n%s%n", "Potências da Matriz Adjacências:");

                                    powerAdjacencyMatrixResult = powerAdjacencyMatrix(branchesMatrix, convertedExponent, nElem);
                                    out.close();
                                    System.out.println("Ficheiro criado com sucesso.");
                                }
                            } else {
                                out.format("%n%s%n", "O número máximo de nós é 200");
                            }
                        }
                    }

                } else {
                    System.out.printf("%n%s%n", "Expoente inválido.");
                }
            } catch(FileNotFoundException exception){
                    System.out.println("Ficheiro não encontrado");
            }
        }
    }
    }

    public static boolean checkFileFormat(String fileName) {
        boolean check = true;
        String checkFileName[] = fileName.trim().split("\\.");
        if (!checkFileName[1].equals("csv")) {
            out.format("%n%s%s%n", fileName, " não se encontra no formato pertendido");
            check = false;
        }
        return check;
    }

    public static int fileNodesMatrix(String fileNodes, int nElem, String[][] nodesMatrix) throws FileNotFoundException {
        Scanner fileInput = new Scanner(new File(fileNodes));
        String[] vAuxiliar = new String[NUMERO_MAXIMO_NOS];
        int count = 0;

        while (fileInput.hasNext() && nElem < NUMERO_MAXIMO_NOS) {
            String line = fileInput.nextLine();
            if (line.trim().length() > 0) { //Verifica se a linha está em branco
                String temp[] = line.trim().split(",");
                vAuxiliar[count] = temp[0].trim();
                count++;
                if (!temp[0].equals("id") && !compareLines(vAuxiliar, temp[0], count - 1)) { // Ignora o cabeçalho e verifica se a linha se repete
                    for (int column = 0; column < N_COLUNAS_FICHEIRO_NOS; column++) {
                        nodesMatrix[nElem][column] = temp[column].trim(); //Preenche a matriz com as informações de cada nó   
                    }
                    nElem++;
                } else {
                    if (!temp[0].equals("id")) {
                        boolean answer = askLine(count + 1, fileNodes);
                        if (!temp[0].equals("id") && !answer) {
                            System.exit(0);
                        }
                    }
                }

            }
        }
        return nElem;
    }

    public static boolean compareLines(String[] allNodes, String node, int count) {
        for (int i = 0; i < count; i++) {
            if (allNodes[i].equals(node.trim())) {
                return true;
            }
        }
        return false;
    }

    public static boolean askLine(int i, String fileName) {
        Scanner readString = new Scanner(System.in);
        System.out.println();
        System.out.println("A linha " + (i) + " do ficheiro de texto " + fileName + " já se encontra inserida, pretende continuar mesmo assim?(s/n)");
        String answer = readString.next();
        if (answer.equalsIgnoreCase("s")) {
            return true;
        }
        return false;
    }

    public static int fileBranchesMatrix(String fileBranches, int nElem, double[][] branchesMatrix) throws FileNotFoundException {
        Scanner fileInput = new Scanner(new File(fileBranches));
        String[] vAuxiliar = new String[NUMERO_MAXIMO_NOS];
        boolean check = true;
        int row = 0;
        int count = 0;
        while (fileInput.hasNext()) {
            String line = fileInput.nextLine();
            if (line.trim().length() > 0) { //Verifica se a linha está em branco
                String temp[] = line.trim().split(",");
                vAuxiliar[count] = line;
                count++;
                if (!temp[0].equals("from") && !compareLines(vAuxiliar, line, count - 1)) { //Ignora o cabeçalho e verifica se a linha se repete
                    check = checkBranches(temp);
                    if (check) {
                        filledMatrix(temp, branchesMatrix);
                    } else {
                        out.format("%n%s%n", "Os nós não podem ter relações entre si");
                        return -1;
                    }
                } else {
                    if (!temp[0].equals("from")) {
                        boolean answer = askLine(count + 1, fileBranches);
                        if (!answer && !temp[0].trim().equals("from")) {
                            System.exit(0);
                        }
                    }
                }

            }
        }

        return nElem;
    }
//Métodos que verificam o conteúdo dos ficheiros

    public static boolean checkBranches(String[] temp) {
        boolean check = true;
        if (temp[0].equals(temp[1])) { //Compara o primeiro e segundo elemento da matriz temporária e verifica se são iguais;
            check = false;
        }
        return check;
    }

    public static void filledMatrix(String[] temp, double[][] branchesMatrix) {

        int index1 = Integer.parseInt(temp[0].substring(1, 3));
        int index2 = Integer.parseInt(temp[1].substring(1, 3));

        branchesMatrix[index1 - 1][index2 - 1] = 1;
        branchesMatrix[index2 - 1][index1 - 1] = 1;

    }
// Opcões de menu

    public static String menu() {
        Scanner ler = new Scanner(System.in);

        String texto = "\nMENU:"
                + "\n 1- Determinar grau dos nós"
                + "\n 2- Centralidade de vetor próprio"
                + "\n 3- Grau médio"
                + "\n 4- Densidade"
                + "\n 5- Potências da Matriz de Adjacências"
                + "\n 0- Fim"
                + "\nQual a sua opção?";
        out.format("%n%s%n", texto);

        String op = ler.nextLine();


        return op;
    }

    public static String terminar(String op) {
        out.format("%n%s%n", "Já fez todas as gravações necessárias? Confirma terminar(s/n)?");

        char resp = (read.next()).charAt(0);

        if (resp != 's' && resp != 'S') {
            op = "1";

        }
        return op;
    }

    //Métrica 1
    public static int nodeDegree(double[][] branchesMatrix, double[] nodeDegreeArray, int nElem) {
        int indexArray = 0;

        for (int node = 0; node < nElem; node++) {
            int nodeDegree = 0;

            for (int neighbour = 0; neighbour < branchesMatrix[0].length; neighbour++) {
                if (branchesMatrix[node][neighbour] != 0) {
                    nodeDegree++;

                }
            }

            nodeDegreeArray[indexArray] = nodeDegree;
            indexArray++;

        }

        return nElem;
    }

    //Métrica 2
    public static void determinarCentralidadeVetorProprio(double[][] branchesMatrix, int nElem, double[] eigenValueCentrality) {
        Matrix matrixA = new Basic2DMatrix(branchesMatrix);

        int colunaMaiorValorProprio;

        EigenDecompositor decompositor = new EigenDecompositor(matrixA);
        Matrix[] eigen;
        eigen = decompositor.decompose();

        double[][] valoresProprios = valoresProprios = eigen[INDICE_VALORES_PROPRIOS].toDenseMatrix().toArray();
        double[][] vetoresProprios = eigen[INDICE_VETORES_PROPRIOS].toDenseMatrix().toArray();
        double[] arrayVetorProprio = new double[nElem]; // Criar vetor só com os valores do vetor correspondente ao maior valor próprio
        colunaMaiorValorProprio = obterColunaMaiorValorProprio(valoresProprios);

        for (int i = 0; i < nElem; i++) {
            arrayVetorProprio[i] = vetoresProprios[i][colunaMaiorValorProprio];
        }
        for (int i = 0; i < nElem; i++) {
            double soma = 0;
            for (int j = 0; j < nElem; j++) {
                soma = soma + (arrayVetorProprio[j] * branchesMatrix[i][j]);
            }
            eigenValueCentrality[i] = (double) Math.round(1 / valoresProprios[colunaMaiorValorProprio][colunaMaiorValorProprio] * soma * 1000) / 1000;
        }

        
    }

    public static int obterColunaMaiorValorProprio(double[][] valoresProprios) {
        double maior = valoresProprios[0][0];

        int colunaMaior = 0;
        for (int index = 0; index < valoresProprios.length; index++) {
            if (valoresProprios[index][index] > maior) {
                maior = valoresProprios[index][index];
                colunaMaior = index;
            }
        }
        return colunaMaior;

    }
//Métrica 3

    public static double calculateAverageDegree(double[] nodeDegreeArray) {
        int sum = 0;

        double averageDegree;

        for (int i = 0; i < nodeDegreeArray.length; i++) {
            sum += nodeDegreeArray[i];
        }

        averageDegree = (double) sum / nodeDegreeArray.length;

        return averageDegree;
    }
//Métrica 4

    public static double calculateDensity(double density, int nElem, double actualConnections) {
        double potencialConnections = 0;

        potencialConnections = calcPotencialConnections(nElem, potencialConnections);

        density = calcDensity(actualConnections, potencialConnections);

        return density;
    }

    public static double calcDensity(double actualConnections, double potencialConnections) {
        double density;

        density = (double) (actualConnections / potencialConnections) * 100;

        return density;
    }

    public static double calcPotencialConnections(int nElem, double potencialConnections) {
        potencialConnections = (nElem * (nElem - 1)) / 2;

        return potencialConnections;
    }

    public static double calcActualConnections(double[][] branchesMatrix) {
        double actualConnections = 0;
        double num = (double) 1;

        for (int lin = 0; lin < branchesMatrix.length; lin++) {
            for (int col = 0; col < branchesMatrix[0].length; col++) {
                double value = branchesMatrix[lin][col];

                if (value == num) {
                    actualConnections++;
                }
            }
        }

        return (double) actualConnections / 2;
    }
//Métrica 5

    public static double[][] powerAdjacencyMatrix(double[][] branchesMatrix, int exponent, int nElem) {
        double[][] powerAdjacencyMatrixResult = branchesMatrix.clone();

        showHeader(1);
        listMatrixDouble(powerAdjacencyMatrixResult, nElem);

        out.format("%n");

        for (int i = 0; i < exponent - 1; i++) {
            out.format("%n");
            showHeader(i + 2);
            powerAdjacencyMatrixResult = multiplyMatrices(powerAdjacencyMatrixResult, branchesMatrix, nElem);
            listMatrixDouble(powerAdjacencyMatrixResult, nElem);
            out.format("%n");
        }

        return powerAdjacencyMatrixResult;
    }

    public static int askExponent() {
        Scanner read = new Scanner(System.in);
        System.out.printf("%n%s%n", "Qual o expoente a ser introduzido?");
        String exponent = read.next();
        read.nextLine();
        while (verifyExponent(exponent) == -1) {
            System.out.printf("%s%n", "Por favor, introduza um expoente válido!");
            exponent = read.next();
        }
        return Integer.parseInt(exponent);
    }

    private static int verifyExponent(String exponent) {
        int convertedExponent;

        while (true) {
            try {
                convertedExponent = Integer.parseInt(exponent);
                if (convertedExponent >= 0) {
                    return convertedExponent;
                } else {
                    return -1;
                }
            } catch (NumberFormatException e) {
                return -1;
            }
        }
    }

    public static double[][] multiplyMatrices(double[][] branchesMatrix, double[][] powerAdjacencyMatrixResult, int nElem) {
        double[][] vAuxiliar = new double[branchesMatrix.length][branchesMatrix[0].length];

// Percorrer as linhas da matriz;
        for (int i = 0; i < nElem; i++) {

            // Percorrer as colunas da matriz;
            for (int j = 0; j < nElem; j++) {

                // Fazer a adição e a multiplicação correspondente;
                for (int n = 0; n < nElem; n++) {
                    vAuxiliar[i][j] += branchesMatrix[i][n] * powerAdjacencyMatrixResult[n][j];
                }
            }
        }

        return vAuxiliar;
    }

    public static void showHeader(int actualExponent) {

        out.format("%n%s%d%n", " Matriz de expoente (k): ", actualExponent);
    }
//Utilitários

    public static void listArrayDouble(double[] m, int nElem, String[][] nodesMatrix) {
        out.format(" ");
        for (int i = 0; i < nElem; i++) {
            out.format("%s%s%.0f%n", nodesMatrix[i][0], " ", m[i]);
            out.format(" ");
        }
    }

    public static void listMatrixDouble(double[][] m, int nElem) {
        double element;

        for (int i = 0; i < nElem; i++) {
            out.format("%n");

            for (int j = 0; j < nElem; j++) {
                element = m[i][j];
                out.format("%.0f%s", element, " ");
            }
        }
    }

    public static void listStringMatrix(String[][] m, int nElem) {
        String element;

        for (int i = 0; i < nElem; i++) {
            out.format("%n");

            for (int j = 0; j < N_COLUNAS_FICHEIRO_NOS; j++) {
                element = m[i][j];
                out.format("%s%s", element, " ");
            }
        }
    }

    public static double getMax(double[] eigenValueCentrality, int nElem) {
        /**
         * Obter o indice do participante com maior numero de pontos
         */
        double maior = eigenValueCentrality[0];
        for (int i = 1; i < nElem; i++) {
            if (eigenValueCentrality[i] > maior) {
                maior = eigenValueCentrality[i];
            }
        }
        return maior;
    }

    public static int getPosition(double[] eigenValueCentrality, double value, int nElem) {
        /**
         * Obter o indice do participante com maior numero de pontos
         */
        int pos = 0;
        for (int i = 1; i < nElem; i++) {
            if (eigenValueCentrality[i] == value) {
                pos = i;
            }
        }
        return pos;
    }

    public static void listArrayDoubleCentralidade(double[] m, int nElem, String[][] nodesMatrix) {
        out.format(" ");
        for (int i = 0; i < nElem; i++) {
            out.format("%s%s%.3f%n", nodesMatrix[i][0], " ", m[i]);
            out.format(" ");
        }
    }

    public static void listMatrixDoubleCentralidade(double[][] m, int nElem) {
        double element;

        for (int i = 0; i < nElem; i++) {
            out.format("%n");

            for (int j = 0; j < nElem; j++) {
                element = m[i][j];
                out.format("%.3f%s", element, " ");
            }
        }
    }

    public static String fileNameOut(String fileNodes) {
        String temp[] = fileNodes.trim().split("_");
        String fileNameData;
        SimpleDateFormat dateFormatter = new SimpleDateFormat("ddMMyyyy");
        Date date = new Date();
        fileNameData = temp[1] + "_" + dateFormatter.format(date);
        return fileNameData;
    }

}
