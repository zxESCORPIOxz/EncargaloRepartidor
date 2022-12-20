//Creado por Duran Llamacuri Kevin - Universidad Continental - 2022
package mx.com.encargalo.repartidor.Entidades;

public class pe_claseorden_lista_hto {
    String idorden;
    String fecha,hora,estado,nombre,apellido;

    public pe_claseorden_lista_hto(String idorden, String fecha, String hora, String estado, String nombre, String apellido) {
        this.idorden = idorden;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getIdorden() {
        return idorden;
    }

    public void setIdorden(String idorden) {
        this.idorden = idorden;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
