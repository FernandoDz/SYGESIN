
package sygesin.entidadesdenegocio;




public class Estudiante {
    
    private int id;
    private int idRol;
    private String nombre;
    private String apellido;
    private String direccion;
    private String departamento;
    private String telefono;
    private String correo;
    private String encargado;
     private String seccion;
      private String fechanacimiento;
    private int top_aux;
    private Rol rol;
    
    public Estudiante() {
    }

    public Estudiante(int id, int idRol, String nombre, String apellido, String direccion, String departamento, String telefono, String correo, String encargado, String seccion, String fechanacimiento, int top_aux, Rol rol) {
        this.id = id;
        this.idRol = idRol;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.departamento = departamento;
        this.telefono = telefono;
        this.correo = correo;
        this.encargado = encargado;
        this.seccion = seccion;
        this.fechanacimiento = fechanacimiento;
        this.top_aux = top_aux;
        this.rol = rol;
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

    public String getDireccion() {
        return direccion;
    }

    public String getDepartamento() {
        return departamento;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public String getEncargado() {
        return encargado;
    }

    public String getSeccion() {
        return seccion;
    }

    public String getFechanacimiento() {
        return fechanacimiento;
    }

    public int getTop_aux() {
        return top_aux;
    }

    public Rol getRol() {
        return rol;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public void setFechanacimiento(String fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

   

    
    

    
   

   

    
    
}
