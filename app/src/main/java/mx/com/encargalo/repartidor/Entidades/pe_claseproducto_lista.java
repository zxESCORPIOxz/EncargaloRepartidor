package mx.com.encargalo.repartidor.Entidades;

public class pe_claseproducto_lista {
    String Descripcion, Cantidad, SubTotal;

    public pe_claseproducto_lista(String descripcion, String cantidad, String subTotal) {
        Descripcion = descripcion;
        Cantidad = cantidad;
        SubTotal = subTotal;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getCantidad() {
        return Cantidad;
    }

    public void setCantidad(String cantidad) {
        Cantidad = cantidad;
    }

    public String getSubTotal() {
        return SubTotal;
    }

    public void setSubTotal(String subTotal) {
        SubTotal = subTotal;
    }
}
