package ppss.P05;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.ArrayList;


public class AlquilaCochesTest {
    private AlquilaCochesTestable alq;
    private Ticket ticketExpected;
    private ServicioStub serviciostub;

    @BeforeEach
    void setUp() {
        alq = new AlquilaCochesTestable();
        ticketExpected = new Ticket();
        serviciostub = new ServicioStub();
    }

    @Test
    public void C1_calculaPrecio_should_return_75_when_10_days_and_no_bank_holidays(){

        LocalDate fechaInicio = LocalDate.of(2024,05,18);
        int dias = 10;
        alq.calendario = new CalendarioStub();
        alq.setServicio(serviciostub);
        ticketExpected.setPrecio_final(75);

        Ticket result = assertDoesNotThrow(
                () -> alq.calculaPrecio(TipoCoche.TURISMO, fechaInicio, dias)
        );

        assertEquals(ticketExpected.getPrecio_final(), result.getPrecio_final());
    }

    @Test
    public void C2_calculaPrecio_should_return_625_when_7_days_and_2_bank_holidays(){

        LocalDate fechaInicio = LocalDate.of(2024,06,19);
        int dias = 7;

        ticketExpected.setPrecio_final(62.5f);

        ArrayList<LocalDate> festivos = new ArrayList<>();
        festivos.add(LocalDate.of(2024,06,20));
        festivos.add(LocalDate.of(2024,06,24));

        CalendarioStub stub = new CalendarioStub();
        stub.setFestivos(festivos);
        alq.calendario = stub;
        alq.setServicio(serviciostub);

        Ticket result = assertDoesNotThrow(
                () -> alq.calculaPrecio(TipoCoche.CARAVANA, fechaInicio, dias)
        );

        assertEquals(ticketExpected.getPrecio_final(), result.getPrecio_final());
    }

    @Test
    public void C3_calculaPrecio_should_return_exception_when_3_days_fail(){

        LocalDate fechaInicio = LocalDate.of(2024,04,17);
        int dias = 8;
        String messageExceptionExpected = "Error en dia: 2024-04-18; "
                +"Error en dia: 2024-04-21; " +
                "Error en dia: 2024-04-22; ";

        ticketExpected.setPrecio_final(62.5f);

        ArrayList<LocalDate> errores = new ArrayList<>();
        errores.add(LocalDate.of(2024,04,18));
        errores.add(LocalDate.of(2024,04,21));
        errores.add(LocalDate.of(2024,04,22));

        CalendarioStub stub = new CalendarioStub();
        stub.setErrores(errores);
        alq.calendario = stub;
        alq.setServicio(serviciostub);

        MensajeException exception = assertThrows(MensajeException.class,
                () ->  alq.calculaPrecio(TipoCoche.CARAVANA, fechaInicio, dias));

        assertEquals(messageExceptionExpected, exception.getMessage());
    }

}
