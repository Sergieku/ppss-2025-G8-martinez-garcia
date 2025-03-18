package ppss.P05;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class MatriculaAlumnoTest {
    private OperacionStub op;
    private ArrayList<String> cursadas = new ArrayList<>();
    private ArrayList<String> errores = new ArrayList<>();
    private MatriculaAlumnoTestable mat;
    private JustificanteMatricula expected;

    @BeforeEach
    public void setUp(){
        mat = new MatriculaAlumnoTestable();
        op = new OperacionStub();
        cursadas.add("P1");
        cursadas.add("FC");
        cursadas.add("FFI");
        op.setCursadas(cursadas);

        errores.add("YYY");
        errores.add("ZZ");
        op.setErrores(errores);

        mat.setOp(op);
    }

    @Test
    void C1_compruebaMatricula_should_return_ZZ_no_existe_P1_cursada(){
        String dni = "00000000T";
        String[] asignaturasSolicitadas = {"MD", "ZZ", "FBD", "P1"};

        expected = new JustificanteMatricula();
        expected.setDni(dni);

        ArrayList<String> matriculadasExpected = new ArrayList<>();
        matriculadasExpected.add("MD");
        matriculadasExpected.add("FBD");
        expected.setAsignaturas(matriculadasExpected);

        ArrayList<String> erroresExpected = new ArrayList<>();
        erroresExpected.add("Asignatura ZZ no existe");
        erroresExpected.add("Asignatura P1 ya cursada");
        expected.setErrores(erroresExpected);

        JustificanteMatricula justificanteReal = assertDoesNotThrow(
                () -> mat.validaAsignaturas(dni,asignaturasSolicitadas));

        assertAll(
                () -> assertEquals(expected.getDni(), justificanteReal.getDni()),
                () -> assertEquals(expected.getAsignaturas(), justificanteReal.getAsignaturas()),
                () -> assertEquals(expected.getErrores(),justificanteReal.getErrores()));
    }

    @Test
    void C2_compruebaMatricula_should_return_asignaturas_todas_matriculadas(){
        String dni = "00000000T";
        String[] asignaturasSolicitadas = {"PPSS", "ADA", "P3"};

        expected = new JustificanteMatricula();
        expected.setDni(dni);

        ArrayList<String> matriculadasExpected = new ArrayList<>();
        matriculadasExpected.add("PPSS");
        matriculadasExpected.add("ADA");
        matriculadasExpected.add("P3");
        expected.setAsignaturas(matriculadasExpected);

        ArrayList<String> erroresExpected = new ArrayList<>();
        expected.setErrores(erroresExpected);

        JustificanteMatricula justificanteReal = assertDoesNotThrow(
                () -> mat.validaAsignaturas(dni,asignaturasSolicitadas));

        assertAll(
                () -> assertEquals(expected.getDni(), justificanteReal.getDni()),
                () -> assertEquals(expected.getAsignaturas(), justificanteReal.getAsignaturas()),
                () -> assertEquals(expected.getErrores(),justificanteReal.getErrores()));
    }
}
