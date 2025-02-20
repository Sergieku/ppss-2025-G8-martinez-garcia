package ppss.practica3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Test asociados a la clase Cine")
public class CineTest {
    Cine cine;

    @BeforeEach
    public void setup(){
        cine = new Cine();
    }

    @Test
    void C1_reservaButacas_should_return_Exception_when_fila_empty_and_want_3() {

        // Arrange
        boolean[] asientos = {};
        int solicitados = 3;

        // Act & Assert

        ButacasException exception = assertThrows(ButacasException.class, () -> cine.reservaButacas(asientos, solicitados));

        assertEquals("No se puede procesar la solcitud",exception.getMessage());
    }

    @Test
    void C2_reservaButacas_should_return_false_when_fila_empty_and_want_zero() {

        // Arrange
        boolean[] asientos = {};
        int solicitados = 0;
        boolean[] arrayEsperado = {};
        boolean resultadoEsperado = false;

        // Act

        boolean resultadoReal = assertDoesNotThrow(
                () -> cine.reservaButacas(asientos, solicitados));

        // Assert
        
        assertAll(
                () -> assertArrayEquals(arrayEsperado,asientos),
                () -> assertEquals(resultadoEsperado,resultadoReal)
        );

    }


    @Test
    void C3_reservaButacas_should_return_true_when_fila_has_3_seats_free_and_want_2(){

        // Arrange
        boolean[] asientos = {false,false,false,true,true};
        int solicitados = 2;
        boolean[] arrayEsperado = {true,true,false,true,true};
        boolean resultadoEsperado = true;

        // Act

        boolean resultadoReal = assertDoesNotThrow(
                () -> cine.reservaButacas(asientos, solicitados));

        // Assert

        assertAll(
                () -> assertArrayEquals(arrayEsperado,asientos),
                () -> assertEquals(resultadoEsperado,resultadoReal)
        );
    }

    @Test
    void C4_reservaButacas_should_return_false_when_no_free_seats_and_want_1(){

        // Arrange
        boolean[] asientos = {true,true,true};
        int solicitados = 1;
        boolean[] arrayEsperado = {true,true,true};
        boolean resultadoEsperado = false;

        // Act

        boolean resultadoReal = assertDoesNotThrow(
                () -> cine.reservaButacas(asientos, solicitados));

        // Assert

        assertAll(
                () -> assertArrayEquals(arrayEsperado,asientos),
                () -> assertEquals(resultadoEsperado,resultadoReal)
        );
    }
}
