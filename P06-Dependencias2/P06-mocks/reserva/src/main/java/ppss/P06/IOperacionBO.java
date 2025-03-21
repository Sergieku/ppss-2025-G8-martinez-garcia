package ppss.P06;

import ppss.excepciones.IsbnInvalidoException;
import ppss.excepciones.JDBCException;
import ppss.excepciones.SocioInvalidoException;

public interface IOperacionBO {
    public void operacionReserva(String socio, String isbn)
            throws IsbnInvalidoException, JDBCException, SocioInvalidoException;
}
