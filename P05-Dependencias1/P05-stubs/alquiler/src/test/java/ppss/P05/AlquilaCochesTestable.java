package ppss.P05;

public class AlquilaCochesTestable extends AlquilaCoches {
    public IService servicio = new Servicio();

    @Override
    public IService getServicio(){
        return servicio;
    }

    public void setServicio(IService servicio) {
        this.servicio = servicio;
    }
}
