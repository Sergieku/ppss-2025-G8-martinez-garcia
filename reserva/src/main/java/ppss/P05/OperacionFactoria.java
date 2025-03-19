package ppss.P05;

public class OperacionFactoria {
    private static IOperacionBO op = new Operacion();

    public static IOperacionBO create(){
        return op;
    }

    public static void setOperacion(IOperacionBO op){
        OperacionFactoria.op = op;
    }
}
