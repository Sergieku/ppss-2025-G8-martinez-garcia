package ppss.P05;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;


public class ReservaTest {
    ReservaTestable reserva = new ReservaTestable();
    ArrayList<String> ISBNinvalido;
    ArrayList<String> excepcion;
    ArrayList<String> SocioInvalidos;
    OperacionStub stub;

    @BeforeEach
    void setUp() {
        ISBNinvalido = new ArrayList<>();
        excepcion = new ArrayList<>();
        SocioInvalidos = new ArrayList<>();
        stub = new OperacionStub();
    }


    @Test
    void C1_realizaReserva_should_return_ReservaException1_when_login_wrong(){
        String login = "xxxx";
        String password = "xxxx";
        String socio = "Luis";
        String [] isbns = {"11111"};
        String MessageExceptionExpected = "ERROR de permisos; ";

        OperacionFactoria.setOperacion(stub);


        ReservaException real = assertThrows(ReservaException.class,
                ()-> reserva.realizaReserva(login, password,socio, isbns));

        assertEquals(MessageExceptionExpected,real.getMessage());
    }

    @Test
    void C2_realizaReserva_should_return_noexcept_when_login_and_ISBNs_ok(){
        String login = "ppss";
        String password = "ppss";
        String socio = "Luis";
        String [] isbns = {"11111","22222"};

        OperacionFactoria.setOperacion(stub);


        assertDoesNotThrow(
                ()-> reserva.realizaReserva(login, password,socio, isbns));
    }

    @Test
    void C3_realizaReserva_should_return_ReservaException2_when_ISBN_wrong(){
        String login = "ppss";
        String password = "ppss";
        String socio = "Luis";
        String [] isbns = {"11111","33333","44444"};
        String MessageExceptionExpected = "ISBN invalido:33333; ISBN invalido:44444; ";
        ArrayList<String> ISBNinvalidos = new ArrayList<>();
        ISBNinvalidos.add("33333");
        ISBNinvalidos.add("44444");

        stub.setISBNinvalidos(ISBNinvalidos);

        OperacionFactoria.setOperacion(stub);


        ReservaException real = assertThrows(ReservaException.class,
                ()-> reserva.realizaReserva(login, password,socio, isbns));

        assertEquals(MessageExceptionExpected,real.getMessage());
    }

    @Test
    void C4_realizaReserva_should_return_ReservaException3_when_not_socio(){
        String login = "ppss";
        String password = "ppss";
        String socio = "Pepe";
        String [] isbns = {"11111"};
        String MessageExceptionExpected = "SOCIO invalido; ";
        ArrayList<String> SociosInvalidos = new ArrayList<>();
        SociosInvalidos.add("Pepe");

        stub.setSociosInvalidos(SociosInvalidos);

        OperacionFactoria.setOperacion(stub);

        ReservaException real = assertThrows(ReservaException.class,
                ()-> reserva.realizaReserva(login, password,socio, isbns));

        assertEquals(MessageExceptionExpected,real.getMessage());
    }
    @Test
    void C5_realizaReserva_should_return_ReservaException4_when_connection_exception(){
        String login = "ppss";
        String password = "ppss";
        String socio = "Luis";
        String [] isbns = {"11111","22222"};
        String MessageExceptionExpected = "CONEXION invalida; ";
        ArrayList<String> Excepciones = new ArrayList<>();
        Excepciones.add("22222");

        stub.setExcepciones(Excepciones);

        OperacionFactoria.setOperacion(stub);

        ReservaException expected = assertThrows(ReservaException.class,
                ()-> reserva.realizaReserva(login, password,socio, isbns));

        assertEquals(MessageExceptionExpected,expected.getMessage());

    }
}
