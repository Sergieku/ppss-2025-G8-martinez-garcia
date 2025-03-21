package ppss.P06;

import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ppss.excepciones.*;

import static org.junit.jupiter.api.Assertions.*;

public class ReservaStubTest {
    private Reserva res;
    private FactoriaBOs fact;
    private IOperacionBO iop;
    private String login;
    private String password;
    private String socio;
    private String[] isbns;


    @BeforeEach
    public void setup(){
        res = EasyMock.partialMockBuilder(Reserva.class)
                .addMockedMethod("compruebaPermisos")
                .niceMock();
        fact = EasyMock.niceMock(FactoriaBOs.class);
        iop = EasyMock.niceMock(IOperacionBO.class);
    }

    @Test
    public void C1_realizaReserva_should_return_ReservaException1_when_login_wrong(){
        login = "xxxx";
        password = "xxxx";
        socio = "Pepe";
        isbns = new String[]{"33333"};

        String messageExceptionExpected = "ERROR de permisos; ";
        EasyMock.expect(res.compruebaPermisos(login,password,Usuario.BIBLIOTECARIO))
                .andReturn(false);

        EasyMock.replay(res);

        ReservaException excepcion = assertThrows(ReservaException.class,
                ()-> res.realizaReserva(login,password,socio,isbns));

        assertEquals(messageExceptionExpected, excepcion.getMessage());
    }

    @Test
    void C2_realizaReserva_should_return_noexcept_when_login_and_ISBNs_ok(){
        login = "ppss";
        password = "ppss";
        socio = "Pepe";
        isbns = new String[]{"22222","33333"};

        EasyMock.expect(res.compruebaPermisos(login,password,Usuario.BIBLIOTECARIO))
                .andReturn(true);

        res.setFactoriaBOs(fact);

        EasyMock.expect(fact.getOperacionBO()).andReturn(iop);
        assertDoesNotThrow(()->{
            iop.operacionReserva(socio, isbns[0]);
            iop.operacionReserva(socio, isbns[1]);
        });

        EasyMock.replay(res,fact,iop);

        assertDoesNotThrow(()-> res.realizaReserva(login,password,socio,isbns));
    }

    @Test
    void C3_realizaReserva_should_return_ReservaException2_when_ISBN_wrong(){
        login = "ppss";
        password = "ppss";
        socio = "Pepe";
        isbns = new String[]{"11111","22222","55555"};

        String messageExceptionExpected = "ISBN invalido:"+isbns[0]+"; " +
                "ISBN invalido:"+isbns[2]+"; ";

        EasyMock.expect(res.compruebaPermisos(login,password,Usuario.BIBLIOTECARIO))
                .andReturn(true);

        res.setFactoriaBOs(fact);

        EasyMock.expect(fact.getOperacionBO()).andReturn(iop);

        assertDoesNotThrow(()->{
            iop.operacionReserva(socio, isbns[0]);
            EasyMock.expectLastCall().andThrow(new IsbnInvalidoException());
            iop.operacionReserva(socio, isbns[1]);
            iop.operacionReserva(socio, isbns[2]);
            EasyMock.expectLastCall().andThrow(new IsbnInvalidoException());
        });

        EasyMock.replay(res,fact,iop);

        ReservaException exception = assertThrows(ReservaException.class,()->{
            res.realizaReserva(login,password,socio,isbns);
        });

        assertEquals(messageExceptionExpected, exception.getMessage());
    }

    @Test
    void C4_realizaReserva_should_return_ReservaException3_when_not_socio(){
        login = "ppss";
        password = "ppss";
        socio = "Luis";
        isbns = new String[]{"22222"};

        String messageExceptionExpected = "SOCIO invalido; ";

        EasyMock.expect(res.compruebaPermisos(login,password,Usuario.BIBLIOTECARIO))
                .andReturn(true);

        res.setFactoriaBOs(fact);

        EasyMock.expect(fact.getOperacionBO()).andReturn(iop);

        assertDoesNotThrow(()->{
            iop.operacionReserva(socio,isbns[0]);
            EasyMock.expectLastCall().andThrow(new SocioInvalidoException());
        });

        EasyMock.replay(res,fact,iop);

        ReservaException exception = assertThrows(ReservaException.class,()->{
            res.realizaReserva(login,password,socio,isbns);
        });

        assertEquals(messageExceptionExpected, exception.getMessage());;
    }

    @Test
    void C5_realizaReserva_should_return_ReservaException4_when_connection_exception(){
        login = "ppss";
        password = "ppss";
        socio = "Pepe";
        isbns = new String[]{"11111","22222","33333"};

        String messageExceptionExpected = "ISBN invalido:"+isbns[0]+"; " +
                "CONEXION invalida; ";

        EasyMock.expect(res.compruebaPermisos(login,password,Usuario.BIBLIOTECARIO))
                .andReturn(true);

        res.setFactoriaBOs(fact);

        EasyMock.expect(fact.getOperacionBO()).andReturn(iop);

        assertDoesNotThrow(()->{
            iop.operacionReserva(socio,isbns[0]);
            EasyMock.expectLastCall().andThrow(new IsbnInvalidoException());
            iop.operacionReserva(socio,isbns[1]);
            iop.operacionReserva(socio,isbns[2]);
            EasyMock.expectLastCall().andThrow(new JDBCException());
        });

        EasyMock.replay(res,fact,iop);

        ReservaException exception = assertThrows(ReservaException.class,()->{
            res.realizaReserva(login,password,socio,isbns);
        });

        assertEquals(messageExceptionExpected, exception.getMessage());
    }
}

