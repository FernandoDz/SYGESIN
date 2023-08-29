<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="sygesin.entidadesdenegocio.Estudiante"%>
<% Estudiante estudiante = (Estudiante) request.getAttribute("estudiante");%>

<!DOCTYPE html>
<html>
    <head>        
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Detalle de Estudiante</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Detalle de Estudiante</h5>
             <div class="row">
                     <div class="input-field col l4 s12">
                        <input  id="txtSeccionId" type="text" value="<%=estudiante.getSeccionId()%>" disabled>
                        <label for="txtSeccionId">SeccionId</label>
                    </div> 
                    <div class="input-field col l4 s12">
                        <input  id="txtNombre" type="text" value="<%=estudiante.getNombre()%>" disabled>
                        <label for="txtNombre">Nombre</label>
                    </div>                       
                    <div class="input-field col l4 s12">
                        <input  id="txtApellido" type="text" value="<%=estudiante.getApellido()%>" disabled>
                        <label for="txtApellido">Apellido</label>
                    </div> 
                    <div class="input-field col l4 s12">
                        <input  id="txtDireccion" type="text" value="<%=estudiante.getDireccion()%>" disabled>
                        <label for="txtDireccion">Direccion</label>
                    </div> 
                    <div class="input-field col l4 s12">
                        <input  id="txtDepartamento" type="text" value="<%=estudiante.getDepartamento()%>" disabled>
                        <label for="txtDepartamento">Departamento</label>
                    </div>
                    <div class="input-field col l4 s12">
                        <input  id="txtTelefono" type="text" value="<%=estudiante.getTelefono()%>" disabled>
                        <label for="txtTelefono">Telefono</label>
                    </div> 
                     <div class="input-field col l4 s12">
                        <input  id="txtCorreo" type="text" value="<%=estudiante.getCorreo()%>" disabled>
                        <label for="txtCorreo">Correo</label>
                    </div>
                     <div class="input-field col l4 s12">
                        <input  id="txtEncargado" type="text" value="<%=estudiante.getEncargado()%>" disabled>
                        <label for="txtEncargado">Encargado</label>
                    </div>
                     <div class="input-field col l4 s12">
                        <input  id="txtSeccion" type="text" value="<%=estudiante.getSeccion()%>" disabled>
                        <label for="txtSeccion">Seccion</label>
                    </div>
                     <div class="input-field col l4 s12">
                        <input  id="txtFechanacimiento" type="text" value="<%=estudiante.getFechanacimiento()%>" disabled>
                        <label for="txtFechanacimiento">Fechanacimiento</label>
                    </div>
                    <div class="input-field col l4 s12">
                        <input  id="txtLogin" type="text" value="<%=estudiante.getLogin()%>" disabled>
                        <label for="txtLogin">Login</label>
                    </div>    
                     <div class="input-field col l4 s12">
                        <input  id="txtPassword" type="text" value="<%=estudiante.getPassword()%>" disabled>
                        <label for="txtPassword">Password</label>
                    </div> 
                    <div class="input-field col l4 s12">   
                        <select id="slEstatus" name="estatus" disabled>
                            <option value="0" <%=(estudiante.getEstatus() == 10) ? "selected" : ""%>>SELECCIONAR</option>
                            <option value="<%=Estudiante.EstatusEstudiante.ACTIVO%>"  <%=(estudiante.getEstatus() == EstudianteEstatusEstudiante.ACTIVO) ? "selected" : ""%>>ACTIVO</option>
                            <option value="<%=Estudiante.EstatusEstudiante.INACTIVO%>"  <%=(estudiante.getEstatus() == Estudiante.EstatusEstudiante.INACTIVO) ? "selected" : ""%>>INACTIVO</option>
                        </select>       
                        <label for="slEstatus">Estatus</label>                       
                    </div>
                    <div class="input-field col l4 s12">
                        <input id="txtRol" type="text" value="<%=estudiante.getRol().getNombre() %>" disabled>
                        <label for="txtRol">Rol</label>
                    </div> 
                </div>

                <div class="row">
                    <div class="col l12 s12">
                         <a href="Estudiante?accion=edit&id=<%=estudiante.getId()%>" class="waves-effect waves-light btn blue"><i class="material-icons right">edit</i>Ir modificar</a>            
                        <a href="Estudiante" class="waves-effect waves-light btn blue"><i class="material-icons right">list</i>Cancelar</a>                          
                    </div>
                </div>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />         
    </body>
</html>

