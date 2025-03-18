package ppss.P05;

public class MatriculaAlumnoTestable extends MatriculaAlumno {
    private Operacion op = null;
    @Override
    protected Operacion getOperacion(){
        if (op == null) {
            return new Operacion();
        }
        else{
            return op;
        }
    }

    public void setOp(Operacion op) {
        this.op = op;
    }

}

