import java.io.*;
import java.util.List;
import java.util.Random;

/**
 * Interfaz que se encarga de las operaciones de procesado de ficheros
 * @author Jose Luis Obiang Ela Nanguang
 * @version 2.0
*/

public interface Procesar {

    /**
     * Lee un fichero y muestra su contenido
     * @param fichero recibe el fichero a leer
     * @param estadoTraza recibe el estado de la traza
     * @throws IOException
     */
    public static int leerFichero(File fichero, boolean estadoTraza) throws IOException {
        String linea = "";
        FileReader readme = null;
        BufferedReader lectorReadme = null;
        int totalLineas = 0;
        try {

            readme = new FileReader(fichero);
            lectorReadme = new BufferedReader(readme);

            while ((linea = lectorReadme.readLine()) != null) {
                if(fichero.getName().equalsIgnoreCase("logs.txt")){
                    if(linea.toLowerCase().contains("texto en claro formateado:")){
                            Procesar.imprimirTrazaConSaltoLinea("\t\t\t\t" + (totalLineas + 1), estadoTraza);
                        Procesar.imprimirTrazaConSaltoLinea("Texto en claro Formateado: " + linea.substring(linea.indexOf(":") + 1), estadoTraza);
                        totalLineas++;
                    }else if(linea.toLowerCase().contains("criptograma:")){
                        Procesar.imprimirTrazaSinSaltoLinea("CRIPTOGRAMA: " + linea.substring(linea.indexOf(":") + 1) + "\n", estadoTraza);
                    }
                }else{
                    Procesar.imprimirTrazaConSaltoLinea(linea, estadoTraza);
                }

            }
        } catch (FileNotFoundException e) {
            Procesar.imprimirTrazaSinSaltoLinea("No existe el fichero: " + fichero.getPath(), estadoTraza);
        } finally {
            if (lectorReadme != null) lectorReadme.close();
            if (readme != null) readme.close();
        }

        return totalLineas;
    }

    /**
     * Comprueba si existe un fichero
     * @param fichero Objeto de tipo File, recibe el fichero a comprobar
     * @return
     * <ul>
     *     <li>tru: si existe el fichero</li>
     *     <li>false: si no existe el fichero</li>
     * </ul>
     */
    public static boolean comprobarFichero(File fichero, String compare, boolean estadoTraza) {
        boolean fileExist = false;
        if (!fichero.exists()) { // Comprobar si existe el fichero
            Procesar.imprimirTrazaSinSaltoLinea("No existe el fichero " + fichero.getPath(), estadoTraza);
            if (fichero.getPath().toLowerCase().equalsIgnoreCase("ejecutable/config.txt")) {
                Procesar.imprimirTrazaConSaltoLinea("\nFIN DEL PROGRAMA POR AUSENCIA DEL FICHERO DE CONFIGURACION", estadoTraza);
            } else if (compare.toLowerCase().equalsIgnoreCase("ficheroentrada")) {
                Procesar.imprimirTrazaConSaltoLinea("\nError, no existe el fichero de entrada " + fichero.getPath() + "\nNO HAY NADA QUE CIFRAR O DESCIFRAR", estadoTraza);
            } else if (compare.toLowerCase().equalsIgnoreCase("ficherosalida")) {
                Procesar.imprimirTrazaConSaltoLinea("\nError, no existe el fichero de salida " + fichero.getPath(), estadoTraza);
            }
        } else if (fichero.exists() && fichero.length() < 0) { //Si existe y no contiene nada
            Procesar.imprimirTrazaConSaltoLinea("El fichero " + fichero.getPath() + " existe pero esta vacio +\nContinua el programa", estadoTraza);
            if (fichero.getPath().toLowerCase().equalsIgnoreCase("ejecutable/config.txt")) {
                Procesar.imprimirTrazaConSaltoLinea("FIN DEL PROGRAMA POR AUSENCIA DE CONTENIDO EN EL FICHERO DE CONFIGURACION", estadoTraza);
            }else if (compare.toLowerCase().equalsIgnoreCase("ficheroentrada")) {
                Procesar.imprimirTrazaConSaltoLinea("EL FICHERO DE ENTRADA NO CONTIENE NADA, TERMINAMOS EL PROGRAMA PORQUE NO HAY NADA QUE CIFRAR O DESCIFRAR ", estadoTraza);
            }

        }else{
            fileExist = true; //Si existe el fichero
        }

        return fileExist;
    }

    /**
     * Verifica si una lnea esta vaca.
     * @param linea Variable de tipo cadena, recibe la linea del contenido de un fichero
     * @return booleano
     * <ul>
     *     <li>true si la linea esta vacia</li>
     *     <li>false si la linea no esta vacia</li>
     * </ul>
     */
    public static boolean isEmptyLine(String linea) {
        return linea.trim().isEmpty();
    }

    /**
     *Verifica si el primer caracter de la linea es un comentario.
     * @param linea Variable de tipo cadena, recibe la linea del contenido de un fichero
     * @return
     * <ul>
     *     <li>true: si el primer caracter de la linea es un comentario</li>
     *     <li>false: si el primer caracter de la linea no es un comentario</li>
     * </ul>
     */
    public static boolean isComment(String linea) {
        return linea.charAt(0) == '#';
    }

    /**
     * Verifica si el primer caracter de la linea es una bandera.
     * @param linea Variable de tipo cadena, recibe la linea del contenido de un fichero
     * @return
     * <ul>
     *     <li>true: si el primer caracter de la linea es un comentario</li>
     *     <li>false: si el primer caracter de la linea no es un comentario</li>
     * </ul>
     */
    public static boolean isFlag(String linea, boolean estadoTraza) {
        if (linea.charAt(0) == '@') {
            Procesar.imprimirTrazaConSaltoLinea("Bandera", estadoTraza);
            return true;
        }
        return false;
    }

    /**
     * Verifica si el primer caracter de la linea es un comando.
     * @param linea Variable de tipo cadena, recibe la linea del contenido de un fichero
     * @return
     * <ul>
     *     <li>true: si el primer caracter de la linea es un comando</li>
     *     <li>false: si el primer caracter de la linea no es un comando</li>
     * </ul>
     */
    public static boolean isCommand(String linea, boolean estadoTraza) {
        if (linea.charAt(0) == '&') {
            Procesar.imprimirTrazaConSaltoLinea("Comando", estadoTraza);
            return true;
        }
        return false;
    }


    /**
     * Devuelve un fichero de entrada o salida.
     *
     * @param linea    Variable de tipo cadena, recibe la linea del contenido de un fichero
     * @param compare  Variable de tipo cadena, recibe el texto a comparar para saber si se trata de un fichero de entrada o de salida
     * @param pathInit Variable de tipo cadena, recibe el path
     * @return fichero
     */

    public static File getFile(String linea, String compare, String pathInit, boolean estadoTraza) {

        int ultimoIndice = 0;
        String path = "";
        File fichero = null;
        if (compare.equalsIgnoreCase("ficheroentrada")) {
            path = getPath(linea, pathInit, "ficheroentrada");
        } else if (compare.equalsIgnoreCase("ficherosalida")) {
            path = getPath(linea, pathInit, "ficherosalida");
        } else if (compare.equalsIgnoreCase("clave")) {
            path = getPath(linea, pathInit, "clave");
        }

        fichero = new File(path); //Obtener fichero de E/S, formateado o fichero clave
        comprobarFichero(fichero, compare, estadoTraza); //Comprobar existencia del fichero

        return fichero;

    }

    /**
     * Comprueba si una lnea contiene un texto especfico.
     *
     * @param linea   Variable de tipo cadena, recibe la linea del contenido de un fichero
     * @param compare Variable de tipo cadena, recibe el texto a comparar
     * @return Devuelve
     * <ul>
     *     <li style="color: green">true: contiene la cadena pasada como parametro </li>
     *     <li style="color: red">false: NO contiene la cadena pasada como parametro </li>
     * </ul>
     */
    public static boolean containComparator(String linea, String compare) {
        return linea.toLowerCase().contains(compare);
    }

    /**
     * Obtiene la ruta de un fichero.
     * @param linea  Variable de tipo cadena, recibe la linea del contenido de un fichero
     * @param pathInit Variable de tipo cadena, recibe el path de inicial
     * @param compare Variable de tipo cadena, recibe el texto a comparar para saber si se traata de un fichero de entrada, salida o fichero clave
     * @return
     */
    public static String getPath(String linea, String pathInit, String compare) {
        int ultimoIndice = linea.toLowerCase().indexOf(compare) + compare.length() - 1; //Obtener el ultimo indice de compare
        return pathInit + linea.substring(ultimoIndice + 1).trim();
    }


    /**
     * Devuelve el numero de caracteres ledos de un fichero.
     *
     * @param lectorBuffer              Objeto de tipo {@link BufferedReader}, recibe un objeto de tipo {@link BufferedReader}
     * @param totalCaracteresPermitidos variable de tipo entero, recibe el total de caracteres permitidos para la lectura
     * @param buffer                    variable de tipo char[], recibe el buffer del fichero
     * @return total de caracteres que contiene un fichero
     */
    public static int getCharacteresReadFileIn(BufferedReader lectorBuffer, int totalCaracteresPermitidos, char[] buffer) {
        int caracteresLeidos = 0;
        //Leemos los x primeros caracteres de un fichero
        try {
            caracteresLeidos = lectorBuffer.read(buffer, 0, totalCaracteresPermitidos); // Devuelve el numero de caracteres leidos
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return caracteresLeidos;
    }

    /**
     * Devuelve el texto plano formateado y con la cantidad de caracteres permitidos
     * <ul>Condiciones del texto Plano Formateado
     *  <li>Debe contener unicamente caracteres de la a-z incluida la ñ</li>
     *  <li>Los carateres permitidos deben estar en mayuscula</li>
     * </ul>
     *
     * @param textoPlanoFormateado Variable de tipo cadena, recibe el texto plano formateado
     * @param caracteresPermitidos Variable de tipo cadena, recibe los caracteres permitidos de un fichero
     * @return
     */
    public static String getTextFormated(String textoPlanoFormateado, int caracteresLeidos, char[] buffer) {
        String caracteresPermitidos = new String(buffer, 0, caracteresLeidos); //Limitar el numero de caracteres que debe tener un fichero
        textoPlanoFormateado = caracteresPermitidos.replaceAll("[^a-zA-Z]", ""); //Eliminar todos los caracteres que no sean A-Z
        textoPlanoFormateado = textoPlanoFormateado.toUpperCase(); //Convertir a mayusculas todos los caracteres permitidos
        return textoPlanoFormateado;
    }

    /**
     * Comprueba si el total de caracteres del texto plano formteado es multiplo de x
     *
     * @param textoPlanoFormateado Variable de tipo cadena, recibe el texto plano formateado
     * @param multiple             Variable de tipo entero, recibe el multiplo
     * @return booleano que indica si el texto plano formateado es multiplo de un numero o no
     */
    public static boolean isFormattedTextMultiplyOfNumber(String textoPlanoFormateado, int multiple) {
        if(multiple != 0){
            return textoPlanoFormateado.length() % multiple == 0;
        }
        return false;

    }

    /**
     * Devuelve el numero total de caracteres que debera tener el texto formateado para ser multiplo de un numero.
     * @param textoPlanoFormateado Variable de tipo cadena, recibe el texto plano formateado
     * @param multiple             Variable de tipo entero, recibe el multiplo del texto plano formateado
     * @param estadoTraza Variable de tipo entero, recibe el estado de la traza
     * @return El numero total de caracteres que debera tener el texto formateado para ser multiplo de un numero
     */
    public static int getTotalCharactersMultipleOfNumberFormattedText(String textoPlanoFormateado, int multiple, boolean estadoTraza) {
        int totalCharactersMultipleOfNumber = 0;
        Procesar.imprimirTrazaConSaltoLinea("El texto claro sin formatear tiene: "+textoPlanoFormateado.length() + " caracteres", estadoTraza);
        if (!isFormattedTextMultiplyOfNumber(textoPlanoFormateado, multiple)) {
            while (totalCharactersMultipleOfNumber <= textoPlanoFormateado.length()) {
                totalCharactersMultipleOfNumber += multiple;
            }
            Procesar.imprimirTrazaConSaltoLinea("Texto formateado sin verificar la cantidad de caracteres: " + textoPlanoFormateado, estadoTraza);
            Procesar.imprimirTrazaConSaltoLinea("El texto plano formateado debe tener " + totalCharactersMultipleOfNumber + " caracteres multiplo de "+multiple, estadoTraza);
        }

        return totalCharactersMultipleOfNumber;


    }

    /**
     * Devuelve el texto formateado final.
     * @param textoPlanoFormateado Variable de tipo cadena, recibe el texto plano formateado
     * @param caracteresCifrado         Variable de tipo char[], recibe los caracteres cifrados
     * @param multiple                  Variable de tipo entero, recibe el multiplo del texto plano formateado
     * @param estadoTraza                  Variable de tipo entero, recibe el estado de la traza
     * @return El texto formateado final.
     */
    public static String getFinalFormattedText(String textoPlanoFormateado, char[] caracteresCifrado, int multiple, boolean estadoTraza) {
        //Añadir letras aleatoriamente al texto formateado hasta completar el numero de caracteres faltantes para cumplir la multiplicidad de un numero especificado
        int totalCaracteresFaltantes = getTotalCharactersMultipleOfNumberFormattedText(textoPlanoFormateado, multiple, estadoTraza) - textoPlanoFormateado.length();
        for (int i = 0; i < totalCaracteresFaltantes; i++) {
            textoPlanoFormateado += caracteresCifrado[new Random().nextInt(caracteresCifrado.length)];
        }

        Procesar.imprimirTrazaConSaltoLinea("Texto en claro Formateado: " + textoPlanoFormateado, estadoTraza);
        return textoPlanoFormateado;
    }

    /**
     * Devuelve el array del texto plano formateado cuyos elementos son sus correspondientes valores numericos
     *
     * @param matrizTextoPlanoFormateadoNumerico Array de tipo entero, recibe la matriz del texto plano formateado
     * @param textoPlanoFormateado               Variable de tipo cadena, recibe el texto plano formateado
     * @return array de tipo entero que devuelve el array de texto plano formateado cuyos elementos son sus correspondientes valores numericos
     */
    public static int[] getArrayTextoPlanoNumerico(int[] arrayTextoPlanoFormateadoNumerico, String textoPlanoFormateado) {

        for (int i = 0; i < arrayTextoPlanoFormateadoNumerico.length; i++) {
            if (textoPlanoFormateado.charAt(i) == 'Ñ') {
                arrayTextoPlanoFormateadoNumerico[i] = 14;
            } else {
                if ((textoPlanoFormateado.charAt(i) - 65) > 13) {
                    arrayTextoPlanoFormateadoNumerico[i] = (int) (textoPlanoFormateado.charAt(i) - 65) + 1; //Toma el caracter correspondiente del texto formateado, lo convierte a ASCII y lo resta menos 65 para obtener su correspondiente clave numerico
                } else {
                    arrayTextoPlanoFormateadoNumerico[i] = (int) textoPlanoFormateado.charAt(i) - 65; //Toma el caracter correspondiente del texto formateado, lo convierte a ASCII y lo resta menos 65 para obtener su correspondiente clave numerico
                }

            }

        }

        return arrayTextoPlanoFormateadoNumerico;
    }


    /**
     * Devuelve una sublista de tokens a leer del fichero clave, segun si el total de tokens validos es multiplo de 3, 4 o 5
     * @param totalTokensKeyRead Variable de tipo entero, recibe el total de tokens a leer del fichero clave
     * @param tokensKey recibe el array de tokens a leer del fichero clave
     * @return Una sublista de tokens a leer del fichero clave, segun si el total de tokens validos es multiplo de:
     * <ul>
     *     <li>3</li>
     *     <li>4</li>
     *     <li>5</li>
     * </ul>
     */
    public static List<String> getTokensKeyRead(int totalTokensKeyRead, List<String> tokensKey) {
        return tokensKey.subList(0, totalTokensKeyRead);
    }

    /**
     * Imprime mensajes en la consola con salto de linea segun este el estado de la traza.
     * <ul>estadoTraza
     *     <li>true: Imprime mensajes por consola</li>
     *     <li>false: NO imprime mensajes por consola</li>
     * </ul>
     * @param salida Variable de tipo cadena, recibe el mensaje a mostrar por consola
     * @param estadoTraza Variable de tipo boolean, recibe el estado de la traza
     */
    public static void imprimirTrazaConSaltoLinea(String salida, boolean estadoTraza){
        if(estadoTraza){
            System.out.println(salida);
        }
    }

    /**
     * Imprime mensajes en la consola sin salto de linea segun este el estado de la traza.
     * <ul>estadoTraza
     *     <li>true: Imprime mensajes por consola</li>
     *     <li>false: NO imprime mensajes por consola</li>
     * </ul>
     * @param salida Variable de tipo cadena, recibe el mensaje a mostrar por consola
     * @param estadoTraza Variable de tipo boolean, recibe el estado de la traza
     */
    public static void imprimirTrazaSinSaltoLinea(String salida, boolean estadoTraza){
        if(estadoTraza){
            System.out.print(salida);
        }
    }

}
