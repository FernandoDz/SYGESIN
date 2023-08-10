package sygesin.entidadesdenegocio;
import java.time.LocalDate;
public class Usuario {

     private int id;
    private int rolId;
    private String nombre;
    private String apellido;
    private String login;
    private String password;
    private byte estatus;
    private LocalDate fechaRegistro;
    private int top_aux;
    private String confirmPassword_aux;
    private Rol rol;

    public Usuario() {
    }

    public Usuario(int id, int rolId, String nombre, String apellido, String login, String password, byte estatus, LocalDate fechaRegistro, int top_aux, String confirmPassword_aux, Rol rol) {
        this.id = id;
        this.rolId = rolId;
        this.nombre = nombre;
        this.apellido = apellido;
        this.login = login;
        this.password = password;
        this.estatus = estatus;
        this.fechaRegistro = fechaRegistro;
        this.top_aux = top_aux;
        this.confirmPassword_aux = confirmPassword_aux;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public int getRolId() {
        return rolId;
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
        return estatus;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setRolId(int rolId) {
        this.rolId = rolId;
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
        this.estatus = estatus;
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
    
    

}
