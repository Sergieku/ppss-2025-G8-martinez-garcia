package ppss.P05;

import java.util.ArrayList;

public class OperacionStub implements IOperacionBO{
    private ArrayList<String> ISBNinvalidos = new ArrayList<String>();
    private ArrayList<String> sociosInvalidos = new ArrayList<String>();
    private ArrayList<String> excepciones = new ArrayList<String>();

    public void operacionReserva(String socio, String isbn)
            throws IsbnInvalidoException, JDBCException, SocioInvalidoException{

        if(sociosInvalidos.contains(socio)){
            throw new SocioInvalidoException(socio);
        }
        if(ISBNinvalidos.contains(isbn)){
            throw new IsbnInvalidoException(isbn);
        }
        else if(excepciones.contains(isbn)){
            throw new JDBCException();
        }
    }

    public void setISBNinvalidos(ArrayList<String> ISBNinvalidos){
        this.ISBNinvalidos = ISBNinvalidos;
    }

    public void setSociosInvalidos(ArrayList<String> sociosInvalidos){
        this.sociosInvalidos = sociosInvalidos;
    }

    public void setExcepciones(ArrayList<String> excepciones){
        this.excepciones = excepciones;
    }
}
