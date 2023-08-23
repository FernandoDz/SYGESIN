package sygesin.entidadesdenegocio;

import java.time.LocalDate;
import java.util.ArrayList;

public class Empleado {

    private int id;
    private int idRol;
    private String nombre;
    private String apellido;
    private String login;
    private String password;
    private byte estatus;
    private LocalDate fechaRegistro;
    private int top_aux;
    private String confirmPassword_aux;
    private Rol rol;
    private ArrayList<Empleado> empleados;

    public Empleado() {
    }

    public Empleado(int id, int idRol, String nombre, String apellido, String login, String password, byte estatus, LocalDate fechaRegistro, int top_aux, String confirmPassword_aux, Rol rol, ArrayList<Empleado> empleados) {
        this.id = id;
        this.idRol = idRol;
        this.nombre = nombre;
        this.apellido = apellido;
        this.login = login;
        this.password = password;
        this.estatus = estatus;
        this.fechaRegistro = fechaRegistro;
        this.top_aux = top_aux;
        this.confirmPassword_aux = confirmPassword_aux;
        this.rol = rol;
        this.empleados = empleados;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte getEstatus() {
        return estatus;
    }

    public void setEstatus(byte estatus) {
        this.estatus = estatus;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }

    public String getConfirmPassword_aux() {
        return confirmPassword_aux;
    }

    public void setConfirmPassword_aux(String confirmPassword_aux) {
        this.confirmPassword_aux = confirmPassword_aux;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public ArrayList<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(ArrayList<Empleado> empleados) {
        this.empleados = empleados;
    }

    public class EstatusEmpleado {

        public static final byte ACTIVO = 1;
        public static final byte INACTIVO = 2;
    }
}
