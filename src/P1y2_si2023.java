import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Clase principal para el cifrado y descifrado Hill.
 *
 * @author Jose Luis Obiang Ela Nanguang
 * @version 2.0
 */
public class P1y2_si2023 {
    private static Scanner input = new Scanner(System.in);
    private static final int MOD = 27; //modulo para la inversa modular
    private static final int totalCaracteresPermitidos = 1000; //Numero de caracteres permitidos para la lectura del fichero de entrada

    public static void main(String[] args) throws IOException {

        //Variables que almacenan la url de los ficheros
        String urlFileIn = ""; //Almacena url del fichero de entrada
        String urlFileOut = ""; //Almacena url del fichero de salida
        String urlFileFormat = ""; //Almacena url del fichero formateado
        String urlFileKey = ""; //Almacena url del fichero clave
        String pathInit = "C:\\06_P1y2SI2023_ela_nanguang\\src\\"; //path de concatenacion
        String urlReadme = pathInit + "help.txt"; //path de la ruta del fichero de ayuda del programa

        //Variables que almacenan los ficheros
        File fileIn = null; //Variable que almacena el fichero de entrada
        File fileOut = null; //Variable que almacena el fichero de salida
        File fileFormat = null; //Variable que almacena el fichero formateado
        File fileKey = null; //Variable que almacena el fichero clave
        File fileLogs = new File(pathInit + "logs.txt"); //Variable que almacena el fichero logs
        File fileReadme = new File(urlReadme); //Variable que almacena el fichero de ayuda del programa

        FileWriter fileOutputWrite = null; //Objeto de tipo FileWriter para escribir en el fichero de salida
        FileWriter fileLogsWriter = new FileWriter(fileLogs, true); //true: Permite añadir contenido a un fichero de logs

        String textoPlanoFormateado = ""; //Se trata del texto plano formateado
        String linea = ""; //Variable de lectura del contenido de los ficheros
        String caracteresPermitidos = ""; //Variable que almacena las letras permitidas para la lectura del fichero
        StringBuilder cifrado = new StringBuilder(); //cifrado->Variable que almacena el texto cifrado
        StringBuilder descifradoText = new StringBuilder(); //descifrado->Variable que almacena el texto descifrado


        int totalCharactersMultipleOfNumber = 0;
        int multiple = 0; //Variable que indica el orden de la matriz clave y que la cantidad de caracteres a descifrar debe ser multiplo de ese numero
        int totalTokensKeyRead = 0; //Variable que almacena la cantidad de tokens de la clave
        int indice = 0; //Variable que almacena el indice de la clave

        List<String> tokensKey = null; //Array que almacena los tokens de la clave

        int[] arrayTextoPlanoFormateadoNumerico = null; //Array que almacena el texto plano numerico

        //Matrices para realizar el cifrado/descifrado HILL
        double[][] matrizTextoPlanoNumerico = null; //Matriz que almacena el texto plano numerico
        double[][] matrizClave = null;  //Matriz que almacena la clave
        double[][] matrizCifradoNumerico = null; //Matriz que almacena el cifrado numerico
        double[][] matrizDescifradoNumerico = null; //Matriz que almacena el cifrado numerico
        char[][] matrizTextoCifrado = null; //Matriz que almacena el texto cifrado

        //Array que almacena las letras permitidas para el cifrado o descifrado
        char[] caracteresCifrado = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        String commandCodDes = ""; //Variable que almacena el comando para el cifrado o descifrado

        boolean estadoTraza = true; //true->traza ON; false->traza OFF

        //Obtener nombre del fichero de configuracion
        // Comprobar que hayamos introducido al menos dos parametros de entrada
        Parametros parametros = new Parametros(args);
        if (parametros.isLessTwoParameters()) { //Si hay menos de dos parametros
            if (parametros.isOneParameter()) { //Si solo hay un parametro
                //Leer fichero de configuracion
                if (parametros.ParameterH(fileReadme, estadoTraza)) { //Si el primer parametro es -h
                    Procesar.leerFichero(fileReadme, estadoTraza); //Leer fichero de ayuda
                } else if (parametros.isParameterF(estadoTraza)) { //Si el unico parametro es -f
                    Procesar.imprimirTrazaConSaltoLinea("Falta el archivo de configuracion", estadoTraza);
                    Procesar.imprimirTrazaConSaltoLinea("FIN DEL PROGRAMA", estadoTraza);
                } else if (parametros.isParameterL(estadoTraza)) { //Si el unico parametro es -l
                    Procesar.imprimirTrazaConSaltoLinea("LOGS:", estadoTraza);
                    if(!Procesar.comprobarFichero(fileLogs, "", estadoTraza)){
                        if(fileLogs.createNewFile()) { //Crear fichero si no existe
                            Procesar.imprimirTrazaConSaltoLinea("Fichero de logs no encontrado\n" + "FICHERO CREADO", estadoTraza);
                        }else{
                            Procesar.imprimirTrazaConSaltoLinea("ERROR en la creacion del fichero de logs", estadoTraza);
                        }
                    }else{
                        Procesar.leerFichero(fileLogs, estadoTraza); //Leer fichero de logs
                    }

                } else {
                    Procesar.imprimirTrazaConSaltoLinea("Error, parametro incorrecto", estadoTraza);
                    Procesar.imprimirTrazaConSaltoLinea("La sintaxis correcta es: P1_SI.2023 [-f config.txt] | [-h]", estadoTraza);
                }
            } else {
                parametros.isEmpty(estadoTraza); //Si no hay parametros
            }

        } else { //Si hay 2 o mas parametros

            if (parametros.isParameterF(estadoTraza)) {

                //Fichero de configuracion
                File fileConfig = null;
                fileConfig = new File(pathInit + parametros.getParametros()[1]); //Instanciamos el fichero de configuracion
                if (Procesar.comprobarFichero(fileConfig, "", estadoTraza)) { //Comprobar si existe el fichero de configuracion

                    FileReader archivo = null;
                    BufferedReader lector = null;

                    //Comprobar si existe el fichero de logs para almacenar los texto en claro formateados y los criptogramas
                    if(!Procesar.comprobarFichero(fileLogs, "", estadoTraza)){
                        if(fileLogs.createNewFile()) { //Crear fichero de logs si no existe
                            Procesar.imprimirTrazaConSaltoLinea("Fichero de logs no encontrado\n" + "FICHERO CREADO", estadoTraza);
                        }else{
                            Procesar.imprimirTrazaConSaltoLinea("ERROR en la creacion del fichero de logs", estadoTraza);
                        }
                    }

                    try {
                        archivo = new FileReader(fileConfig);
                        lector = new BufferedReader(archivo);

                        //Leer fichero de configuracion,
                        while ((linea = lector.readLine()) != null) { // Leer el archivo
                            if (!Procesar.isEmptyLine(linea)) { //Si la linea no esta vacia.
                    /*Capturamos el primer caracter de cada linea de texto para saber
                    si estamos ante un comentario, una bandera, comando o fichero E/S
                    */
                                if (Procesar.isComment(linea)) {
                                    Procesar.imprimirTrazaConSaltoLinea("Comentario", estadoTraza);
                                } else if (Procesar.isCommand(linea, estadoTraza)) {
                                    //Fichero clave
                                    if (linea.substring(1, 2).equals(" ")) { //Si es un comando valido, el & debe ir seguido de un espacio
                                        if (Procesar.containComparator(linea, "clave")) {
                                            fileKey = Procesar.getFile(linea, "clave", pathInit, estadoTraza); //Obtener fichero clave
                                            FileReader keyReader = null;
                                            BufferedReader keyLector = null;
                                            try {
                                                keyReader = new FileReader(fileKey);
                                                keyLector = new BufferedReader(keyReader);

                                                Procesar.imprimirTrazaConSaltoLinea("Leyendo fichero clave", estadoTraza);
                                                String keyLinea;
                                                //Leer fichero clave
                                                while ((keyLinea = keyLector.readLine()) != null) {

                                                    //Dividir la cadena en un array de cadenas usando el espacio como separador
                                                    tokensKey = Arrays.asList(keyLinea.split("\\s+")); //Usar el patron "\\s+" para divdir por uno o mas espacios
                                                    // Obtener el total de tokens
                                                    int totalTokensKey = tokensKey.size(); //Total de elementos que contiene el fichero clave

                                                    if (totalTokensKey >= 25) { //El fichero clave debe contener al menos 9 tokens
                                                        tokensKey.forEach(s -> System.out.print(s + " "));
                                                        totalTokensKeyRead = 25;
                                                        tokensKey = Procesar.getTokensKeyRead(totalTokensKeyRead, tokensKey);
                                                        multiple = (int) Math.sqrt(tokensKey.size());
                                                        Procesar.imprimirTrazaConSaltoLinea("\nMultiplo: " + multiple, estadoTraza);
                                                        tokensKey.forEach(s -> System.out.print(s + " "));
                                                    } else if (totalTokensKey >= 16) {
                                                        tokensKey.forEach(s -> System.out.print(s + " "));
                                                        totalTokensKeyRead = 16;
                                                        tokensKey = Procesar.getTokensKeyRead(totalTokensKeyRead, tokensKey);
                                                        multiple = (int) Math.sqrt(tokensKey.size());
                                                        Procesar.imprimirTrazaConSaltoLinea("\nMultiplo: " + multiple, estadoTraza);
                                                        tokensKey.forEach(s -> System.out.print(s + " "));
                                                    } else if (totalTokensKey >= 9) {
                                                        tokensKey.forEach(s -> System.out.print(s + " "));
                                                        totalTokensKeyRead = 9;
                                                        tokensKey = Procesar.getTokensKeyRead(totalTokensKeyRead, tokensKey);
                                                        multiple = (int) Math.sqrt(tokensKey.size());
                                                        Procesar.imprimirTrazaConSaltoLinea("\nMultiplo: " + multiple, estadoTraza);
                                                        tokensKey.forEach(s -> System.out.print(s + " "));
                                                    }

                                                    if (totalTokensKey >= 9) { //Si hay al menos 9 elementos en la clave
                                                        //Obtener matriz clave
                                                        Procesar.imprimirTrazaConSaltoLinea("\nTotal tokens a leer: " + totalTokensKeyRead, estadoTraza);
                                                        matrizClave = new double[multiple][multiple]; //La dimension de la matriz clave dependera de la cantidad de elementos que haya
                                                        for (int i = 0; i < matrizClave.length; i++) {
                                                            for (int j = 0; j < matrizClave[0].length; j++) {
                                                                matrizClave[i][j] = Integer.parseInt(tokensKey.get(indice)) % 27; //Llenar la matriz con los valores de la clave
                                                                indice++;
                                                            }
                                                        }

                                                        Procesar.imprimirTrazaConSaltoLinea("Mostrando matriz clave: ", estadoTraza);
                                                        for (int i = 0; i < matrizClave.length; i++) {
                                                            for (int j = 0; j < matrizClave[0].length; j++) {
                                                                Procesar.imprimirTrazaSinSaltoLinea((int) matrizClave[i][j] + "  ", estadoTraza);
                                                            }
                                                            System.out.println();
                                                        }
                                                    }
                                                }
                                            } catch (IOException e) {
                                                Procesar.imprimirTrazaConSaltoLinea("Error al leer el fichero clave: " + e.getMessage(), estadoTraza);
                                            }

                                        } else if (Procesar.containComparator(linea, "ficheroentrada")) {
                                            Procesar.imprimirTrazaConSaltoLinea("Leyendo fichero de entrada", estadoTraza);
                                            fileIn = Procesar.getFile(linea, "ficheroentrada", pathInit, estadoTraza); //Obtener fichero de entrada
                                            //Leer fichero de entrada
                                            if (fileIn != null) {
                                                FileReader fileReaderIn = null;
                                                BufferedReader lectorIn = null;
                                                try {

                                                    fileReaderIn = new FileReader(fileIn);
                                                    lectorIn = new BufferedReader(fileReaderIn);

                                                    //Leemos los 1000 primeros caracteres del fichero de entrada
                                                    char[] buffer = new char[totalCaracteresPermitidos]; // Crear un buffer para almacenar los primeros 1000 caracteres
                                                    int caracteresLeidos = Procesar.getCharacteresReadFileIn(lectorIn, totalCaracteresPermitidos, buffer); //Obtener el numero de caracteres permitidos para la lectura del fichero

                                                    if (caracteresLeidos != -1) { //Si el fichero de entrada contiene caracteres
                                                        //Nos quedamos con los primeros 1000 caracteres

                                                        //Obtener texto plano formateado y la cantidad de caracteres permitidos
                                                        textoPlanoFormateado = Procesar.getTextFormated(textoPlanoFormateado, caracteresLeidos, buffer);
                                                        textoPlanoFormateado = Procesar.getFinalFormattedText(textoPlanoFormateado, caracteresCifrado, multiple, estadoTraza);
                                                        fileLogsWriter.write("Texto en claro Formateado: " + textoPlanoFormateado); //Escribe el texto claro formateado en el fichero logs
                                                    }
                                                } catch (IOException e) {
                                                    if (fileReaderIn != null) {
                                                        fileReaderIn.close();
                                                    }
                                                    if (lectorIn != null) {
                                                        lectorIn.close();
                                                    }
                                                }
                                            }

                                            //Fichero de salida
                                        } else if (Procesar.containComparator(linea, "ficherosalida")) {
                                            Procesar.imprimirTrazaConSaltoLinea("Fichero de salida", estadoTraza);

                                            fileOut = Procesar.getFile(linea, "ficherosalida", pathInit, estadoTraza);
                                            if (fileOut != null) {
                                                Procesar.imprimirTrazaConSaltoLinea("Existe el fichero de salida " + fileOut.getName(), estadoTraza);
                                                //Abre el fichero de salida en modo de escritura y sobrescribe si existe
                                                fileOutputWrite = new FileWriter(fileOut, false);
                                            } else { //Si no existe el fichero de salida
                                                urlFileOut = Procesar.getPath(linea, pathInit, "ficherosalida");
                                                fileOut = new File(urlFileOut);
                                                if(fileOut.createNewFile()){ //Crear fichero de salida
                                                    Procesar.imprimirTrazaConSaltoLinea("Fichero de salida no encontrado\n" + "FICHERO DE SALIDA CREADO", estadoTraza);
                                                }else{
                                                    Procesar.imprimirTrazaConSaltoLinea("ERROR en la creacion del fichero de salida", estadoTraza);
                                                }

                                                //Abre el fichero en modo de escritura
                                                fileOutputWrite = new FileWriter(fileOut);
                                            }


                                        } else if (Procesar.containComparator(linea, "formateaentrada")) {

                                            fileOutputWrite.write(textoPlanoFormateado); //Escribe en el fichero formateado el texto formateado del fichero de entrada
                                            //Cerrar archivo
                                            fileOutputWrite.close();

                                        } else if (Procesar.containComparator(linea, "hill")) {
                                            if (commandCodDes.equalsIgnoreCase("codifica on")) {
                                                Procesar.imprimirTrazaConSaltoLinea("Se produce la ejecucion del cifrado", estadoTraza);
                                                fileOutputWrite = new FileWriter(fileOut, false);

                                                //Escribir en el fichero de salida el cifrado del texto
                                                for (char[] val : matrizTextoCifrado) {
                                                    fileOutputWrite.write(val);
                                                }

                                                Procesar.imprimirTrazaSinSaltoLinea("CRIPTOGRAMA: ", estadoTraza);
                                                fileLogsWriter.write("\nCRIPTOGRAMA: " + FuncionesMatrices.showCriptograma(matrizTextoCifrado, estadoTraza) + "\n\n");

                                            } else if (commandCodDes.equalsIgnoreCase("codifica off")) {
                                                Procesar.imprimirTrazaConSaltoLinea("Se produce la ejecucion del descifrado", estadoTraza);
                                                fileOutputWrite = new FileWriter(fileOut, false);
                                                fileOutputWrite.write(descifradoText.toString());
                                                Procesar.imprimirTrazaConSaltoLinea("Texto Descifrado: " + descifradoText.toString(), estadoTraza);
                                            }

                                            //Cerrar fichero de salida y fichero Logs
                                            fileOutputWrite.close();
                                            fileLogsWriter.close();
                                        } else {
                                            Procesar.imprimirTrazaConSaltoLinea("Error, comando no reconocido", estadoTraza);
                                        }
                                    } else {
                                        Procesar.imprimirTrazaConSaltoLinea("Error de sintaxis de comando", estadoTraza);
                                    }
                                } else if (Procesar.isFlag(linea, estadoTraza)) {

                                    if (linea.substring(1, 2).equals(" ")) { //Si es una comando valida, ya que @ debe ir seguido de un espacio
                                        //Fichero de salida.
                                        if (linea.toLowerCase().contains("codifica on")) { //Codificamos el contenido del fichero de entrada
                                            commandCodDes = "codifica on";
                                            Procesar.imprimirTrazaConSaltoLinea("Codificando", estadoTraza);

                                            if (fileOut != null) { //Si existe el fichero de salida
                                                arrayTextoPlanoFormateadoNumerico = new int[textoPlanoFormateado.length()];
                                                arrayTextoPlanoFormateadoNumerico = Procesar.getArrayTextoPlanoNumerico(arrayTextoPlanoFormateadoNumerico, textoPlanoFormateado); //Devuelve el array del texto plano con sus correspondientes valores numericos

                                                totalCharactersMultipleOfNumber = textoPlanoFormateado.length();
                                                Procesar.imprimirTrazaConSaltoLinea(totalCharactersMultipleOfNumber + "", estadoTraza);
                                                matrizTextoPlanoNumerico = new double[multiple][totalCharactersMultipleOfNumber / multiple]; //Dividimos el total de caracteres que tiene el contenido del fichero formateado entre 3, el cual sera el numero de filas de la matriz

                                                indice = 0;
                                                for (int j = 0; j < matrizTextoPlanoNumerico[0].length; j++) {
                                                    for (int i = 0; i < matrizTextoPlanoNumerico.length; i++) {
                                                        matrizTextoPlanoNumerico[i][j] = arrayTextoPlanoFormateadoNumerico[indice];
                                                        indice++;
                                                    }
                                                }


                                                Procesar.imprimirTrazaConSaltoLinea( "Mostrando valores numericos de la matriz de texto: ", estadoTraza);
                                                for (int i = 0; i < matrizTextoPlanoNumerico.length; i++) {
                                                    for (int j = 0; j < matrizTextoPlanoNumerico[0].length; j++) {
                                                        Procesar.imprimirTrazaSinSaltoLinea((int) matrizTextoPlanoNumerico[i][j] + "  ", estadoTraza);
                                                    }
                                                    System.out.println();
                                                }

                                                //Multiplicar matriz clave por la matriz de texto plano numerico
                                                matrizCifradoNumerico = new double[multiple][totalCharactersMultipleOfNumber / multiple];
                                                matrizCifradoNumerico = FuncionesMatrices.multiplicacionMatrices(matrizClave, matrizTextoPlanoNumerico, matrizCifradoNumerico); //Multiplicar la matriz de cifrado por la matriz de valores numericos del texto plano

                                                Procesar.imprimirTrazaConSaltoLinea("Mostrando la matriz de texto cifrado: ", estadoTraza);
                                                cifrado = FuncionesMatrices.showMatrizTextoCifradoDescifrado(matrizCifradoNumerico, cifrado); //Mostrar la matriz producto

                                                //Convertimos los elementos de la matriz producto en sus correspondientes caracteres
                                                matrizTextoCifrado = new char[totalCharactersMultipleOfNumber / multiple][multiple];
                                                matrizTextoCifrado = FuncionesMatrices.getMatrizCifradoDescifrado(matrizTextoCifrado, matrizCifradoNumerico);


                                            }
                                        } else if (linea.toLowerCase().contains("codifica off")) {
                                            //Decodificar
                                            commandCodDes = "codifica off";
                                            Procesar.imprimirTrazaConSaltoLinea("Decodificacion", estadoTraza);
                                            int determinante = (int) FuncionesMatrices.determinant(matrizClave);
                                            Procesar.imprimirTrazaSinSaltoLinea("\nPaso 1: Determinante de la matriz clave -> " + determinante, estadoTraza);

                                            double[][] inverse = FuncionesMatrices.inverseMod27(matrizClave);
                                            if (inverse == null) {
                                                Procesar.imprimirTrazaConSaltoLinea("\nLa matriz no tiene inversa modulo 27.", estadoTraza);
                                            } else {
                                                Procesar.imprimirTrazaConSaltoLinea("\nMostrando la matriz inversa modulo 27 de la matriz clave: ", estadoTraza);
                                                FuncionesMatrices.showMatriz(inverse, estadoTraza);
                                            }

                                            StringBuilder descifrado = new StringBuilder();

                                            if (multiple != 0)
                                                matrizDescifradoNumerico = new double[multiple][totalCharactersMultipleOfNumber / multiple];

                                            matrizDescifradoNumerico = FuncionesMatrices.multiplicacionMatrices(inverse, matrizCifradoNumerico, matrizDescifradoNumerico);

                                            if (matrizDescifradoNumerico != null)
                                                for (int j = 0; j < matrizDescifradoNumerico[0].length; j++) {
                                                    for (int i = 0; i < matrizDescifradoNumerico.length; i++) {
                                                        descifrado.append(matrizDescifradoNumerico[i][j]).append(" ");
                                                    }
                                                }

                                            Procesar.imprimirTrazaConSaltoLinea("Mostrando matriz producto de inversa x matriz cifrado", estadoTraza);
                                            FuncionesMatrices.showMatriz(matrizDescifradoNumerico, estadoTraza);

                                            String[] descifradoNumber = descifrado.toString().split(" ");
                                            if (multiple != 0) {
                                                double[] arrayDescifrado = new double[multiple * (totalCharactersMultipleOfNumber / multiple)];


                                                for (int i = 0; i < descifradoNumber.length; i++) {
                                                    if (!descifradoNumber[i].isEmpty()) {
                                                        arrayDescifrado[i] = Double.parseDouble(descifradoNumber[i]);
                                                    }
                                                }


                                                for (int i = 0; i < arrayDescifrado.length; i++) {
                                                    if ((int) arrayDescifrado[i] == 14) {
                                                        descifradoText.append('Ñ');
                                                    } else if ((int) arrayDescifrado[i] > 14) {
                                                        descifradoText.append((char) (arrayDescifrado[i] + 65 - 1));
                                                    } else {
                                                        descifradoText.append((char) (arrayDescifrado[i] + 65));
                                                    }
                                                }
                                            }

                                        } else if (linea.toLowerCase().contains("traza on")) {
                                            estadoTraza = true;
                                            System.out.println("La traza esta encendida");
                                        } else if (linea.toLowerCase().contains("traza off")) {
                                            estadoTraza = false;
                                            System.out.println("La traza esta apagada");
                                        } else {
                                            Procesar.imprimirTrazaConSaltoLinea("Error, bandera no reconocida", estadoTraza);
                                        }
                                    } else {
                                        Procesar.imprimirTrazaConSaltoLinea("Error de sintaxis de bandera", estadoTraza);
                                    }
                                }

                            } else {
                                Procesar.imprimirTrazaConSaltoLinea("Linea vacia", estadoTraza);
                            }
                        }
                    } catch (IOException e) {
                        Procesar.imprimirTrazaConSaltoLinea("Error al leer el archivo: " + e.getMessage(), estadoTraza);
                    } finally {
                        try {
                            if (lector != null) {
                                lector.close();
                            }
                            if (archivo != null) {
                                archivo.close();

                            }
                        } catch (IOException e) {
                            Procesar.imprimirTrazaConSaltoLinea("Error al cerrar el archivo: " + e.getMessage(), estadoTraza);
                        }
                    }

                    Procesar.imprimirTrazaConSaltoLinea("___________________________________________\n" +
                            "Hasta una proxima ejecucion\n" +
                            "___________________________________________\n", estadoTraza);
                } else {
                    System.out.println("FIN DEL PROGRAMA POR AUSENCIA DEL FICHERO DE CONFIGURACION");
                }

            } else {
                Procesar.imprimirTrazaConSaltoLinea("Error, archivo de configuracion incorrecto", estadoTraza);
            }
        }
    }


}