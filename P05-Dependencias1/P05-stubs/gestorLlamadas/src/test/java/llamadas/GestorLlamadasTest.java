package llamadas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class GestorLlamadasTest {
    GestorLlamadasTestable ges;
    CalendarioStub stub;

    @BeforeEach
    public void setup() {
        ges = new GestorLlamadasTestable();
    }

    @Test
    public void C1_GestorLlamadas_should_return_207_when_hora_diurna_and_minutes_10(){
        // Arrange
        int hora = 12;
        int minutos = 10;
        double resultadoEsperado= 207;

        stub = new CalendarioStub();
        stub.setHoraActual(hora);
        ges.setCal(stub);

        //Act
        double resultadoReal=ges.calculaConsumo(minutos);

        //Assert
        assertEquals(resultadoEsperado,resultadoReal,0.001f);
    }

    @Test
    public void C2_GestorLlamadas_should_return_122_when_hora_nocturna_and_minutes_10(){
        //Arrange
        int hora = 21;
        int minutos = 10;
        double resultadoEsperado= 122;

        stub = new CalendarioStub();
        stub.setHoraActual(hora);
        ges.setCal(stub);

        // Act
        double resultadoReal=ges.calculaConsumo(minutos);

        // Assert
        assertEquals(resultadoEsperado,resultadoReal,0.001f);
    }
}
