/**
 * Interfaz que contiene todas las operaciones de las matrices
 * @author Jose Luis Obiang Ela Nanguang
 * @version 2.0
 */
public interface FuncionesMatrices {

    public static final int MOD = 27; //Modulo para la inversa modular

    /**
     *  Multiplica dos matrices y devuelve el resultado
     * @param matrizClave Matriz de clave
     * @param matrizTextoPlanoNumerico Matriz de texto plano numerico
     * @param matrizCifradoNumerico Matriz de cifrado numerico, es el resultado de la multiplicacion
     * @return matriz resultante de la multiplicacion
     */
    public static double[][] multiplicacionMatrices(double[][] matrizClave, double[][] matrizTextoPlanoNumerico, double[][] matrizCifradoNumerico) {
        //Realizar multiplicacion de matrices para obtener el texto cifrado
        if (matrizClave != null && matrizTextoPlanoNumerico != null && matrizCifradoNumerico != null) {
            for (int i = 0; i < matrizClave.length; i++) {
                for (int j = 0; j < matrizTextoPlanoNumerico[0].length; j++) {
                    for (int k = 0; k < matrizClave[0].length; k++) {
                        matrizCifradoNumerico[i][j] += matrizTextoPlanoNumerico[k][j] * matrizClave[i][k];
                    }
                    matrizCifradoNumerico[i][j] = matrizCifradoNumerico[i][j] % 27;
                }

            }
        }
        return matrizCifradoNumerico;
    }

    /**
     * Convierte una matriz numerica a una matriz de caracteres.
     * @param matrizTextoCifradoDescifrado recibe la matriz de texto cifrado/descifrado
     * @param matrizCifradoDescifradoNumerico recibe la matriz de cifrado/descifrado numerico
     * @return matriz de caracteres del texto cifrado/descifrado
     */

    public static char[][] getMatrizCifradoDescifrado(char[][] matrizTextoCifradoDescifrado, double[][] matrizCifradoDescifradoNumerico) {
        for (int i = 0; i < matrizCifradoDescifradoNumerico.length; i++) {
            for (int j = 0; j < matrizCifradoDescifradoNumerico[0].length; j++) {
                if ((int) matrizCifradoDescifradoNumerico[i][j] == 14) {
                    matrizTextoCifradoDescifrado[j][i] = 'Ñ';
                } else if ((int) matrizCifradoDescifradoNumerico[i][j] > 14) {
                    matrizTextoCifradoDescifrado[j][i] = (char) (matrizCifradoDescifradoNumerico[i][j] + 65 - 1);
                } else {
                    matrizTextoCifradoDescifrado[j][i] = (char) (matrizCifradoDescifradoNumerico[i][j] + 65);
                }
            }
        }
        return matrizTextoCifradoDescifrado;
    }

    /**
     * Muestra la matriz numerica y la almacena en un StringBuilder.
     * @param matrizCifradoDescifradoNumerico recibe la matriz de cifrado/descifrado numerico
     * @param cifradoDescifrado recibe una cadena de caracteres cifrado/descifrado
     * @return cadena de caracteres del texto cifrado/descifrado
     */
    public static StringBuilder showMatrizTextoCifradoDescifrado(double[][] matrizCifradoDescifradoNumerico, StringBuilder cifradoDescifrado) {
        System.out.println("Total de filas: " + matrizCifradoDescifradoNumerico.length);
        System.out.println("Total de columnas: " + matrizCifradoDescifradoNumerico[0].length);
        for (int i = 0; i < matrizCifradoDescifradoNumerico.length; i++) {
            for (int j = 0; j < matrizCifradoDescifradoNumerico[i].length; j++) {
                System.out.print(((int) (matrizCifradoDescifradoNumerico[i][j]) + "  "));
                cifradoDescifrado.append(matrizCifradoDescifradoNumerico[i][j]).append(" "); //Almacenar cada uno de los caracteres cifrados en una cadena
            }
            System.out.println();
        }
        return cifradoDescifrado;
    }

    /**
     * Muestra matrices numericas
     * @param matriz recibe la matriz numerica
     * @param estadoTraza recibe el estado de la traza
     */
    public static void showMatriz(double[][] matriz, boolean estadoTraza) {
        System.out.println();
        if(matriz != null)
        for (double[] doubles : matriz) {
            for (int j = 0; j < matriz[0].length; j++) {
                Procesar.imprimirTrazaSinSaltoLinea((int) doubles[j] + " ", estadoTraza);
            }
            System.out.println();
        }
    }

    /**
     * Muestra el criptograma dada la matriz de cifrado
     * @param matriz recibe la matriz de cifrado
     * @param estadoTraza recibe el estado de la traza
     */
    public static String showCriptograma(char[][] matriz, boolean estadoTraza) {
        StringBuilder criptograma = new StringBuilder();
        for (char[] element : matriz) {
            for (int j = 0; j < matriz[0].length; j++) {
                criptograma.append(element[j]);
                Procesar.imprimirTrazaSinSaltoLinea(element[j] + "", estadoTraza);
            }
        }
        System.out.println();
        return criptograma.toString();
    }

    /**
     * Calcula el determinante de una matriz
     * @param matrix recibe la matriz numerica
     * @return el determinante de la matriz
     */
    public static double determinant(double[][] matrix) {
        double det = 0;
        if (matrix != null) {
            int n = matrix.length;
            if (n == 1) {
                return matrix[0][0];
            }
            if (n == 2) {
                return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
            }


            for (int j = 0; j < n; j++) {
                det += Math.pow(-1, j) * matrix[0][j] * determinant(minor(matrix, 0, j));
            }

        }

        return det;

    }

    /**
     * Devuelve la matriz menor al eliminar una fila y columna especficas.
     * @param matrix recibe la matriz numerica
     * @param row recibe la fila a eliminar
     * @param col recibe la columna a eliminar
     * @return la matriz menor al eliminar la fila y columna especficas
     */
    public static double[][] minor(double[][] matrix, int row, int col) {
        int n = matrix.length;
        double[][] minor = new double[n - 1][n - 1];
        int r = 0;
        for (int i = 0; i < n; i++) {
            if (i == row) continue;
            int c = 0;
            for (int j = 0; j < n; j++) {
                if (j == col) continue;
                minor[r][c] = matrix[i][j];
                c++;
            }
            r++;
        }
        return minor;
    }

    /**
     *  Calcula la matriz adjunta
     * @param matrix recibe la matriz numerica
     * @return la matriz adjunta
     */
    public static double[][] adjoint(double[][] matrix) {
        int n = matrix.length;
        if (n == 1) {
            return new double[][]{{1}};
        }

        double[][] cofactors = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                cofactors[i][j] = Math.pow(-1, i + j) * determinant(minor(matrix, i, j));
            }
        }

        return transpose(cofactors);
    }

    /**
     * Transpone una matriz.
     * @param matrix recibe la matriz numerica
     * @return la matriz transpuesta
     */
    public static double[][] transpose(double[][] matrix) {
        int n = matrix.length;
        double[][] transposed = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                transposed[j][i] = matrix[i][j];
            }
        }
        return transposed;
    }

    /**
     * Calcula la inversa modular de una matriz con respecto al modulo 27.
     * @param matrix recibe la matriz numerica
     * @return la matriz inversa modular de la matriz
     */
    public static double[][] inverseMod27(double[][] matrix) {
        double[][] inverse = null;
        if (matrix != null) {
            int n = matrix.length;
            double det = determinant(matrix);
            if (det == 0) return null;

            int detInv = modInverse((int) det, MOD);
            if (detInv == -1) return null;

            double[][] adjugate = adjoint(matrix);
            inverse = new double[n][n];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {

                    if (((adjugate[i][j] * detInv) % MOD) < 0) {
                        inverse[i][j] = 27 + (adjugate[i][j] * detInv) % MOD;
                    } else {
                        inverse[i][j] = (adjugate[i][j] * detInv) % MOD;
                    }

                }
            }
        }

        return inverse;
    }

    /**
     * Calcula el inverso modular de un numero con respecto a un modulo dado.
     * @param a recibe el numero a invertir
     * @param m recibe el modulo
     * @return el inverso modular de un numero con respecto a un modulo dado
     */
    public static int modInverse(int a, int m) {
        int m0 = m;
        int y = 0, x = 1;
        if (m == 1) return 0;
        while (a > 1) {
            int q = a / m;
            int t = m;

            m = a % m;
            a = t;
            t = y;

            y = x - q * y;
            x = t;
        }

        if (x < 0) x += m0;

        return x;
    }
}
