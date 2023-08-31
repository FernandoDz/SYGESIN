<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="sygesin.entidadesdenegocio.Estudiante"%>
<% Estudiante estudiante = (Estudiante) request.getAttribute("estudiante");%>

<!DOCTYPE html>
<html>
    <head>        
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Editar Estudiante</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Editar Estudiante</h5>
            <form action="Estudiante" method="post" onsubmit="return  validarFormulario()">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>"> 
                <input type="hidden" name="id" value="<%=estudiante.getId()%>">  
                <div class="row">
                    <div class="row">
                    <div class="input-field col l6 s12">
                        <input  id="txtIdRol" type="text" name="idrol">
                        <label for="txtIdRol">Rol</label>
                    </div>
                    <div class="input-field col l6 s12">
                        <input  id="txtNombre" type="text" name="nombre">
                        <label for="txtNombre">Nombre</label>
                    </div>
                    
                    <div class="input-field col l6 s12">
                        <input  id="txtApellido" type="text" name="apellido">
                        <label for="txtApellido">Apellido</label>
                    </div>
                     <div class="input-field col l6 s12">
                        <input  id="txtDireccion" type="text" name="direccion">
                        <label for="txtDireccion">Direccion</label>
                    </div>
                    <div class="input-field col l6 s12">
                        <input  id="txtDepartamento" type="text" name="departamento">
                        <label for="txtDepartamento">Departamento</label>
                    </div>
                    <div class="input-field col l6 s12">
                        <input  id="txtTelefono" type="text" name="telefono">
                        <label for="txtTelefono">Telefono</label>
                    </div>
                     <div class="input-field col l6 s12">
                        <input  id="txtCorreo" type="text" name="correo">
                        <label for="txtCorreo">Correo</label>
                    </div>
                    <div class="input-field col l6 s12">
                        <input  id="txtEncargado" type="text" name="encargado">
                        <label for="txtEncargado">Encargado</label>
                    </div>
                     <div class="input-field col l6 s12">
                        <input  id="txtSeccion" type="text" name="seccion">
                        <label for="txtSeccion">Seccion</label>
                    </div>
                    
                     <div class="input-field col l6 s12">
                        <input  id="txtfechanacimiento" type="text" name="fechanacimiento">
                        <label for="txtfechanacimiento">Fecha de nacimiento</label>
                    </div>
                    <div class="input-field col l4 s12">   
                        <jsp:include page="/Views/Rol/select.jsp">                           
                            <jsp:param name="id" value="<%=estudiante.getIdRol() %>" />  
                        </jsp:include>  
                        <span id="slRol_error" style="color:red" class="helper-text"></span>
                    </div>
                </div>

                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">save</i>Guardar</button>
                        <a href="Estudiante" class="waves-effect waves-light btn blue"><i class="material-icons right">list</i>Cancelar</a>                          
                    </div>
                </div>
            </form>          
        </main>
                        
        <jsp:include page="/Views/Shared/footerBody.jsp" />   
        <script>
            function validarFormulario() {
                var result = true;
                var slEstatus = document.getElementById("slEstatus");
                var slEstatus_error = document.getElementById("slEstatus_error");
                var slRol = document.getElementById("slRol");
                var slRol_error = document.getElementById("slRol_error");
                if (slEstatus.value == 0) {
                    slEstatus_error.innerHTML = "El estatus es obligatorio";
                    result = false;
                } else {
                    slEstatus_error.innerHTML = "";
                }
                if (slRol.value == 0) {
                    slRol_error.innerHTML = "El Rol es obligatorio";
                    result = false;
                } else {
                    slRol_error.innerHTML = "";
                }

                return result;
            }
        </script>
    </body>
</html>

