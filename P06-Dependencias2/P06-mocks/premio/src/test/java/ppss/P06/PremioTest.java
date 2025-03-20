package ppss.P06;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.*;

public class PremioTest {
    private Premio pre;
    String resultadoEsperado;
    float numeroAleatorio;
    ClienteWebService climock;
    String resultadoReal;
    IMocksControl ctrl;


    @BeforeEach
    public void setUp() {
        ctrl = EasyMock.createStrictControl();
        pre = partialMockBuilder(Premio.class)
                .addMockedMethod("generaNumero")
                .mock(ctrl);
        climock = ctrl.mock(ClienteWebService.class);
    }

    @Test
    public void C1_compruebaPremio_should_return_entrada_Champions_when_num_aleatorio_007() {
        resultadoEsperado ="Premiado con entrada final Champions";
        numeroAleatorio = 0.07f;

        expect(pre.generaNumero())
                .andReturn(numeroAleatorio);

        assertDoesNotThrow(
                () -> EasyMock.expect(climock.obtenerPremio())
                        .andReturn("entrada final Champions"));

        pre.cliente = climock;
        ctrl.replay();

        resultadoReal = pre.compruebaPremio();

        assertEquals(resultadoEsperado,resultadoReal);
        EasyMock.verify(pre,climock);
    }

    @Test
    public void C2_compruebaPremio_should_return_no_se_ha_podido_obtener_when_failed_consulta() {
        resultadoEsperado ="No se ha podido obtener el premio";
        numeroAleatorio = 0.05f;

        expect(pre.generaNumero())
                .andReturn(numeroAleatorio);

        EasyMock.expect(climock.obtenerPremio())
                .andThrow(new ClienteWebServiceException());

        pre.cliente = climock;
        ctrl.replay();

        resultadoReal = pre.compruebaPremio();

        assertEquals(resultadoEsperado,resultadoReal);
        EasyMock.verify(pre,climock);

    }

    @Test
    public void C3_compruebaPremio_should_return_no_premio_when_resultado_048() {
        resultadoEsperado ="Sin premio";
        numeroAleatorio = 0.48f;

        expect(pre.generaNumero())
                .andReturn(numeroAleatorio);

        pre.cliente = climock;
        ctrl.replay();

        resultadoReal = pre.compruebaPremio();

        assertEquals(resultadoEsperado,resultadoReal);
        EasyMock.verify(pre,climock);
    }
}
