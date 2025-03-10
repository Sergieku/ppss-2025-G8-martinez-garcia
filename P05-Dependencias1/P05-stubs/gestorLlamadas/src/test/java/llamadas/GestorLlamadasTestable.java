package llamadas;

public class GestorLlamadasTestable extends GestorLlamadas{
    private Calendario cal = null;

    @Override
    public Calendario getCalendario() {
        if ( cal != null){
            return cal;
        }
        else{
            return new Calendario();
        }
    }

    public void setCal (Calendario cal){
        this.cal = cal;
    }
}
