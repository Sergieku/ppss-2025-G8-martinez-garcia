package llamadas;

public class Factoria {
    private static Calendario cal = null;
    public static Calendario create() {
        if ( cal != null){
            return cal;
        }
        else{
            return new Calendario();
        }
    }

    static void setCalendario(Calendario cal){
        Factoria.cal = cal;
    }
}
