package ppss.P05;

import java.util.ArrayList;

public class OperacionStub extends Operacion {
    ArrayList<String> errores = new ArrayList<>();
    ArrayList<String> cursadas = new ArrayList<>();

    public void compruebaMatricula(String dni, String asignatura) throws AsignaturaIncorrectaException, AsignaturaCursadaException{
        if(cursadas.contains(asignatura)){
            throw new AsignaturaCursadaException(asignatura);
        }
        else if (errores.contains(asignatura)){
            throw new AsignaturaIncorrectaException(asignatura);
        }
    }

    public void setErrores (ArrayList<String> errores){
        this.errores = errores;
    }

    public void setCursadas(ArrayList<String> cursadas){
        this.cursadas = cursadas;
    }
}
