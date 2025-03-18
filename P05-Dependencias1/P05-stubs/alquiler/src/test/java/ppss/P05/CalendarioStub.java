package ppss.P05;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarioStub extends Calendario{
    private ArrayList<LocalDate> festivos = new ArrayList<LocalDate>();
    private ArrayList<LocalDate> errores = new ArrayList<LocalDate>();

    @Override
    public boolean es_festivo(LocalDate dia) throws CalendarioException {
        if(festivos.contains(dia)){
            return true;
        }
        else if(errores.contains(dia)){
            throw new CalendarioException();
        }
        else{
            return false;
        }
    }

    public void setFestivos(ArrayList<LocalDate> festivos) {
        this.festivos = festivos;
    }

    public void setErrores(ArrayList<LocalDate> errores) {
        this.errores = errores;
    }
}
