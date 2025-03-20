package ppss.P06;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.*;

public class FicheroTextoTest {
    private IMocksControl ctrl;
    private FicheroTexto fichTestable;
    private FileReader fileReadermock;


    @BeforeEach
    public void setUp() {
        ctrl = EasyMock.createStrictControl();
        fileReadermock = ctrl.mock(FileReader.class);
        fichTestable = EasyMock.partialMockBuilder(FicheroTexto.class)
                .addMockedMethod("getFileReader")
                .mock(ctrl);
    }

    @Test
    void C1_contarCaracteres_should_FicheroException_when_fail_reading_char(){
        // ARRANGE
        String fichero = "src/test/resources/ficheroC1.txt";
        String mensajeExpected = fichero + " (Error al leer el archivo)";

        //ACT
        assertDoesNotThrow(
                () -> EasyMock.expect(fichTestable.getFileReader(fichero)).andReturn(fileReadermock));
        assertDoesNotThrow(
                () -> EasyMock.expect(fileReadermock.read())
                    .andReturn((int)'a')
                    .andReturn((int)'b')
                    .andThrow(new IOException()));

        ctrl.replay();
        FicheroException exceptionCaptured = assertThrows(FicheroException.class,
                () -> fichTestable.contarCaracteres(fichero));

        // ASSERT
        assertEquals(mensajeExpected, exceptionCaptured.getMessage());
        ctrl.verify();
    }

    @Test
    void C2_contarCaracteres_should_FicheroException_when_fail_closing_file(){
        // ARRANGE
        String fichero = "src/test/resources/ficheroC2.txt";
        String mensajeExpected = fichero + " (Error al cerrar el archivo)";

        //ACT
        assertDoesNotThrow(
                () -> EasyMock.expect(fichTestable.getFileReader(fichero)).andReturn(fileReadermock));
        assertDoesNotThrow(
                () -> EasyMock.expect(fileReadermock.read())
                            .andReturn((int) 'a')
                            .andReturn((int) 'b')
                            .andReturn((int) 'c')
                            .andReturn(-1));

        assertDoesNotThrow(()-> fileReadermock.close());
        EasyMock.expectLastCall().andThrow(new IOException()); // En la última llamada al mock hacemos que lance una IOException

        ctrl.replay();
        FicheroException exceptionCaptured = assertThrows(FicheroException.class,
                () -> fichTestable.contarCaracteres(fichero));

        // ASSERT
        assertEquals(mensajeExpected, exceptionCaptured.getMessage());
        ctrl.verify();
    }
}
