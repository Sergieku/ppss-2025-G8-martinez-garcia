package ppss.P06;

import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class FicheroTexto {

    // Factoría Local es la única posibilidad para refactorizar
    public FileReader getFileReader(String nombreFichero) throws FileNotFoundException {
        return new FileReader(nombreFichero);
    }

    public int contarCaracteres(String nombreFichero) throws FicheroException {
        int contador = 0;
        FileReader fichero = null;
        try {
            fichero = getFileReader(nombreFichero);
            int i=0;
            while (i != -1) {
                i = fichero.read();
                contador++;
            }
            contador--;
        } catch (FileNotFoundException e1) {
            throw new FicheroException(nombreFichero +
                    " (No existe el archivo o el directorio)");
        } catch (IOException e2) {
            throw new FicheroException(nombreFichero +
                    " (Error al leer el archivo)");
        }
        try {
            fichero.close();
        } catch (IOException e) {
            throw new FicheroException(nombreFichero +
                    " (Error al cerrar el archivo)");
        }
        return contador;
    }
}
