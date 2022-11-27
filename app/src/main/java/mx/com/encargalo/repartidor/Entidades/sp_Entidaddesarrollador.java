package mx.com.encargalo.repartidor.Entidades;

public class sp_Entidaddesarrollador {

    private int iddesarrollador;
    private String nombre;
    private String apellido;
    private String cargo;
    private String correo;

    public sp_Entidaddesarrollador() {
    }

    public int getIddesarrollador() {
        return iddesarrollador;
    }

    public void setIddesarrollador(int iddesarrollador) {
        this.iddesarrollador = iddesarrollador;
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

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
