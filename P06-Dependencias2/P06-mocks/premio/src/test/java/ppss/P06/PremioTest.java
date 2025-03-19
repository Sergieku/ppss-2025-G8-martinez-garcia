package ppss.P06;

import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.*;

public class PremioTest {
    private Premio pre;

    @BeforeEach
    public void setUp() {
        pre = new Premio();
    }

    @Test
    public void C1_compruebaPremio_should_return_entrada_Champions_when_num_aleatorio_007() {
        String resultadoEsperado ="Premiado con entrada final Champions";
        float numeroAleatorio = 0.07f;

        ClienteWebService climock = EasyMock.mock(ClienteWebService.class);
        assertDoesNotThrow(
                () -> EasyMock.expect(climock.obtenerPremio())
                        .andReturn("entrada final Champions"));

        pre = partialMockBuilder(Premio.class)
                .addMockedMethod("generaNumero")
                .mock();

        expect(pre.generaNumero())
                .andReturn(numeroAleatorio);

        pre.cliente = climock;
        replay(pre,climock);

        String resultadoReal = pre.compruebaPremio();


        assertEquals(resultadoEsperado,resultadoReal);
        EasyMock.verify(pre,climock);
    }

    @Test
    public void C2_compruebaPremio_should_return_no_se_ha_podido_obtener_when_failed_consulta() {
        String resultadoEsperado ="No se ha podido obtener el premio";
        float numeroAleatorio = 0.05f;

        ClienteWebService climock = EasyMock.mock(ClienteWebService.class);

        EasyMock.expect(climock.obtenerPremio())
                .andThrow(new ClienteWebServiceException());

        pre = partialMockBuilder(Premio.class)
                .addMockedMethod("generaNumero")
                .mock();

        expect(pre.generaNumero())
                .andReturn(numeroAleatorio);

        pre.cliente = climock;
        replay(pre,climock);

        String resultadoReal = pre.compruebaPremio();


        assertEquals(resultadoEsperado,resultadoReal);
        EasyMock.verify(pre,climock);

    }

    @Test
    public void C3_compruebaPremio_should_return_no_premio_when_resultado_048() {
        String resultadoEsperado ="Sin premio";
        float numeroAleatorio = 0.48f;

        ClienteWebService climock = EasyMock.mock(ClienteWebService.class);

        pre = partialMockBuilder(Premio.class)
                .addMockedMethod("generaNumero")
                .mock();

        expect(pre.generaNumero())
                .andReturn(numeroAleatorio);

        pre.cliente = climock;
        replay(pre,climock);

        String resultadoReal = pre.compruebaPremio();


        assertEquals(resultadoEsperado,resultadoReal);
        EasyMock.verify(pre,climock);
    }
}
