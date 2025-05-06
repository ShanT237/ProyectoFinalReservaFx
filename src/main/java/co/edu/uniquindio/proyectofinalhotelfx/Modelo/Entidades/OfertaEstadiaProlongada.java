package co.edu.uniquindio.proyectofinalhotelfx.Modelo.Entidades;

public class OfertaEstadiaProlongada implements Oferta {
    @Override
    public boolean esValida() {
        return false;
    }

    @Override
    public double aplicarDescuento(double precioOriginal) {
        return 0;
    }
}
