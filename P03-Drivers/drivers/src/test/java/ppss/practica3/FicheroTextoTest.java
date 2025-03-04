package ppss.practica3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import static org.junit.jupiter.api.Assertions.*;

public class FicheroTextoTest {
    String nombreFichero;
    FicheroTexto fi;

    @BeforeEach
    void setup() {
        fi = new FicheroTexto();
    }

    @Test
    void C1_contarCaracteres_should_return_Exception_when_file_does_not_exist(){
        nombreFichero = "ficheroC1.txt";

        FicheroException exception = assertThrows(FicheroException.class, () -> fi.contarCaracteres(nombreFichero));
    }

    @Test
    void C2_contarCaracteres_should_return_3_when_file_has_3_chars(){
        nombreFichero = "src/test/resources/ficheroCorrecto.txt";

        int resultadoReal = assertDoesNotThrow(() -> fi.contarCaracteres(nombreFichero));

        Assertions.assertEquals(3, resultadoReal);
    }

    @Test
    @Tag("excluido")
    void C3_contarCaracters_should_return_Exception_when_file_cannot_be_read(){
        nombreFichero = "src/test/resources/ficheroC3.txt";
        Assertions.fail();

    }

    @Test
    @Tag("excluido")
    void C4_contarCaracteres_should_return_Exception_when_file_cannot_be_closed(){
        nombreFichero = "src/test/resources/ficheroC4.txt";
        Assertions.fail();
    }

}
