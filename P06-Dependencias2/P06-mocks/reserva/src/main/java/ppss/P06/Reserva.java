package ppss.P06;

import ppss.excepciones.*;

import java.util.ArrayList;

public class Reserva {
    private FactoriaBOs factoriaBOs;

    public boolean compruebaPermisos(String login, String password, Usuario tipoUsu) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // Refactorización por metodo Setter
    public void setFactoriaBOs(FactoriaBOs factoriaBOs) {
        this.factoriaBOs = factoriaBOs;
    }

    public FactoriaBOs getFactoriaBOs() {
        return factoriaBOs;
    }

    public void realizaReserva(String login, String password,
                               String socio, String [] isbns) throws ReservaException {
        ArrayList<String> errores = new ArrayList<String>();
        if(!compruebaPermisos(login, password, Usuario.BIBLIOTECARIO)) {
            errores.add("ERROR de permisos");
        } else {
            FactoriaBOs fd = getFactoriaBOs();
            IOperacionBO io = fd.getOperacionBO();
            try {
                for(String isbn: isbns) {
                    try {
                        io.operacionReserva(socio, isbn);
                    } catch (IsbnInvalidoException iie) {
                        errores.add("ISBN invalido" + ":" + isbn);
                    }
                }
            } catch (SocioInvalidoException sie) {
                errores.add("SOCIO invalido");
            } catch (JDBCException je) {
                errores.add("CONEXION invalida");
            }
        }
        if (errores.size() > 0) {
            String mensajeError = "";
            for(String error: errores) {
                mensajeError += error + "; ";
            }
            throw new ReservaException(mensajeError);
        }
    }
}
