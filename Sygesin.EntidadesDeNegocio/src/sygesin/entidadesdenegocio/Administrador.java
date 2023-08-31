
package sygesin.entidadesdenegocio;
import java.time.LocalDate;
import java.util.ArrayList;
public class Administrador {
    private int id;
    private int idRol;
    private String nombre;
    private String apellido;
    private String login;
    private String password;
    private byte estatusAdministrador;
    private LocalDate fechaRegistro;
    private int top_aux;
    private String confirmPassword_aux;
    private Rol rol;
    private ArrayList<Administrador> administradores;

    public Administrador() {
    }

    public Administrador(int id, int idRol, String nombre, String apellido, String login, String password, byte estatusAdministrador, LocalDate fechaRegistro, int top_aux, String confirmPassword_aux, Rol rol, ArrayList<Administrador> administradores) {
        this.id = id;
        this.idRol = idRol;
        this.nombre = nombre;
        this.apellido = apellido;
        this.login = login;
        this.password = password;
        this.estatusAdministrador = estatusAdministrador;
        this.fechaRegistro = fechaRegistro;
        this.top_aux = top_aux;
        this.confirmPassword_aux = confirmPassword_aux;
        this.rol = rol;
        this.administradores = administradores;
    }

    public int getId() {
        return id;
    }

    public int getIdRol() {
        return idRol;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public byte getEstatus() {
        return estatusAdministrador;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public int getTop_aux() {
        return top_aux;
    }

    public String getConfirmPassword_aux() {
        return confirmPassword_aux;
    }

    public Rol getRol() {
        return rol;
    }

    public ArrayList<Administrador> getAdministradores() {
        return administradores;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdRol(int rolId) {
        this.idRol = rolId;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEstatus(byte estatus) {
        this.estatusAdministrador = estatus;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }

    public void setConfirmPassword_aux(String confirmPassword_aux) {
        this.confirmPassword_aux = confirmPassword_aux;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public void setAdministradores(ArrayList<Administrador> administradores) {
        this.administradores = administradores;
    }

   public class EstatusAdministrador{
 public static final byte ACTIVO=1;
 public static final byte INACTIVO=2;
}

    
    
}
