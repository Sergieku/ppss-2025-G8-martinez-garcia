package llamadas;

public class CalendarioStub extends Calendario {
    int hora;
    @Override
    public int getHoraActual() {
        return hora;
    }

    public void setHoraActual(int hora) {
        this.hora = hora;
    }
}
