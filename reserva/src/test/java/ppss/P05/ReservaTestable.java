package ppss.P05;

public class ReservaTestable extends Reserva {
    @Override
    public boolean compruebaPermisos(String login, String password, Usuario tipoUsu) {
        if(!tipoUsu.equals(Usuario.BIBLIOTECARIO)) {
            return false;
        }

        return login.equals("ppss") && password.equals("ppss");
    }
}
