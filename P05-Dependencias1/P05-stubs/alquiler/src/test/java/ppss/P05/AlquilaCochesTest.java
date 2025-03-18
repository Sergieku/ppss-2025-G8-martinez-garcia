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
}
