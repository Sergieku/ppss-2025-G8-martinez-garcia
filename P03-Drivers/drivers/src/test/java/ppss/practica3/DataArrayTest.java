package ppss.practica3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class DataArrayTest {
    DataArray dt;

    @Test
    void C1_delete_should_return_3_elements_array_when_passed_4_elements_array_and_element_present(){

        // Arrange
        // Datos de Entrada
        int[] coleccion = {1,3,4,7};
        int elemento = 4;

        int[] arrayEsperado = {1,3,7};
        int numElemEsperado = 3;

        dt = new DataArray(coleccion);

        // Act

        assertDoesNotThrow(
                ()->dt.delete(elemento));

        // Assert
        assertAll(
                ()->assertArrayEquals(arrayEsperado,dt.getColeccion()),
                ()->assertEquals(numElemEsperado,dt.size()));
    }

    @Test
    void C2_delete_should_return_4_elements_array_when_passed_5_elements_array_and_element_present_twice(){
        // Arrange
        // Datos de Entrada
        int[] coleccion = {1,3,3,5,7};
        int elemento = 3;

        int[] arrayEsperado = {1,3,5,7};
        int numElemEsperado = 4;

        dt = new DataArray(coleccion);

        // Act

        assertDoesNotThrow(
                ()->dt.delete(elemento));

        // Assert

        assertAll(
                ()->assertArrayEquals(arrayEsperado,dt.getColeccion()),
                ()->assertEquals(numElemEsperado,dt.size()));
    }

    @Test
    void C3_delete_should_return_9_elements_array_when_passed_10_elements_array_and_element_present(){
        // Arrange
        // Datos de Entrada
        int[] coleccion = {1,2,3,4,5,6,7,8,9,10};
        int elemento = 4;

        int[] arrayEsperado = {1,2,3,5,6,7,8,9,10};
        int numElemEsperado = 9;

        dt = new DataArray(coleccion);

        // Act

        assertDoesNotThrow(
                ()->dt.delete(elemento));

        // Assert

        assertAll(
                ()->assertArrayEquals(arrayEsperado,dt.getColeccion()),
                ()->assertEquals(numElemEsperado,dt.size()));
    }

    @Test
    void C4_delete_should_return_DataException_when_passed_empty_array_and_element_is_provided(){
        // Arrange
        // Datos de Entrada
        int[] coleccion = {};
        int elemento = 8;
        String exceptMsgExpected = "No hay elementos en la colección";

        dt = new DataArray(coleccion);

        // Act & Assert

        DataException expectedException = assertThrows(DataException.class, () -> dt.delete(elemento));

        assertEquals(exceptMsgExpected,expectedException.getMessage(),"Mensaje Excepcion inválido");
    }

    @Test
    void C5_delete_should_return_DataException_when_passed_valid_array_and_element_is_invalid(){
        // Arrange
        // Datos de Entrada
        int[] coleccion = {1,3,5,7};
        int elemento = -5;
        String exceptMsgExpected = "El valor a borrar debe ser > 0";

        dt = new DataArray(coleccion);

        // Act & Assert
        DataException expectedException= assertThrows(DataException.class, () -> dt.delete(elemento));

        assertEquals(exceptMsgExpected,expectedException.getMessage(),"Mensaje Excepcion inválido");
    }

    @Test
    void C6_delete_should_return_DataException_when_passed_empty_array_and_element_is_invalid(){
        // Arrange
        // Datos de Entrada
        int[] coleccion = {};
        int elemento = 0;
        String exceptMsgExpected = "Colección vacía. "+
                "Y el valor a borrar debe ser > 0";

        dt = new DataArray(coleccion);

        // Act & Assert
        DataException expectedException= assertThrows(DataException.class, () -> dt.delete(elemento));

        assertEquals(exceptMsgExpected,expectedException.getMessage(),"Mensaje Excepcion inválido");
    }

    @Test
    void C7_delete_should_return_DataException_when_passed_valid_array_and_element_is_not_found(){
        // Arrange
        // Datos de Entrada
        int[] coleccion = {1,3,5,7};
        int elemento = 8;
        String exceptMsgExpected = "Elemento no encontrado";

        dt = new DataArray(coleccion);

        // Act & Assert
        DataException expectedException= assertThrows(DataException.class, () -> dt.delete(elemento));

        assertEquals(exceptMsgExpected,expectedException.getMessage(),"Mensaje Excepcion inválido");
    }

    @DisplayName("delete_With_Exceptions_")
    @ParameterizedTest(name = "[{index}] Message exception should be \"{2}\" when we want delete {1}")
    @MethodSource("casosDelete")
    @Tag("parametrizado")
    @Tag("conExcepciones")
    void C8_deleteWithExceptions(DataArray dt, int elemento, String expectedMsg){
        DataException expectedException = assertThrows(DataException.class, () -> dt.delete(elemento));
        assertEquals(expectedMsg, expectedException.getMessage());
    }

    private static Stream<Arguments> casosDelete(){
        return Stream.of(
                Arguments.of(new DataArray(new int[]{}),8, "No hay elementos en la colección"),
                Arguments.of(new DataArray(new int[]{1,3,5,7}),-5, "El valor a borrar debe ser > 0" ),
                Arguments.of(new DataArray(new int[]{}),0,"Colección vacía. " + "Y el valor a borrar debe ser > 0"),
                Arguments.of(new DataArray(new int[]{1,3,5,7}),8, "Elemento no encontrado" )
        );
    }


    @DisplayName("delete_Without_Exceptions_")
    @ParameterizedTest(name = "[{index}] should be {2} when we want delete {1}")
    @MethodSource("casosDeleteWE")
    @Tag("parametrizado")
    void C9_deleteWithoutExceptions(DataArray dt, int elemento, int[] expectedArr){
        assertDoesNotThrow(() -> dt.delete(elemento));

        assertAll(
                ()->assertArrayEquals(expectedArr,dt.getColeccion()),
                ()->assertEquals(expectedArr.length,dt.size())
        );
    }

    private static Stream<Arguments> casosDeleteWE(){
        return Stream.of(
                Arguments.of(new DataArray(new int[]{1,3,5,7}),5,new int[]{1,3,7}),
                Arguments.of(new DataArray(new int[]{1,3,3,5,7}),3,new int[]{1,3,5,7}),
                Arguments.of(new DataArray(new int[]{1,2,3,4,5,6,7,8,9,10}),4,new int[]{1,2,3,5,6,7,8,9,10})
        );
    }
}
