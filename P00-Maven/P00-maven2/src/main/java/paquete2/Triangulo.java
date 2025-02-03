package paquete2;


public class Triangulo {

    public String tipo_triangulo(int a, int b, int c) {
       String result=null;

        if ((a <1) && (a > 200)) {
            return "Valor a sobrepasa el rango permitido";
        }
        if ((b <1) && (b > 200)) {
            return "Valor b sobrepasa el rango permitido";
        }
        if ((c <1) && (c > 200)) {
            return "Valor c sobrepasa el rango permitido";
        }

        if (a >= (b+c)) {
            return "No es un triangulo";
        }

        if (b >= (a+c)) {
            return "No es un triangulo";
        }

        if (c >= (a+b)) {
            return "No es un triangulo";
        }

        if ((a != b) && (a!= c) && (b!= c)) {
            result = "Escaleno";
        } else {
            if (a==b) {
                if (a==c) {
                    result = "Equilatero";
                } else result = "Isosceles"; //si no ponemos esta línea C8 da error
            } else {
                result = "Isosceles";
            }
        }
        return result;
    }

}
