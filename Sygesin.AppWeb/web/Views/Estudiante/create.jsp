<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="sygesin.entidadesdenegocio.Estudiante"%>

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Crear Estudiante</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Crear Estudiante</h5>
            <form action="Estudiante" method="post" onsubmit="return  validarFormulario()">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>">                
                <div class="row">
                    
                     <div class="input-field col l4 s12">
                        <input  id="txtNombre" type="text" name="nombre" required class="validate" maxlength="50">
                        <label for="txtNombre">Nombre</label>
                    </div>
                    
                    <div class="input-field col l4 s12">
                        <input  id="txtApellido" type="text" name="apellido" required class="validate" maxlength="50">
                        <label for="txtApellido">Apellido</label>
                    </div
                    
                    <div class="input-field col l4 s12">
                        <input  id="txtDireccion" type="text" name="direccion" required class="validate" maxlength="50">
                        <label for="txtDireccion">Direccion</label>
                    </div>  
                    <div class="input-field col l4 s12">
                        <input  id="txtDepartamento" type="text" name="departamento" required class="validate" maxlength="50">
                        <label for="txtDepartamento">Departamento</label>
                    </div>  
                    <div class="input-field col l4 s12">
                        <input  id="txtTelefono" type="text" name="telefono" required class="validate" maxlength="50">
                        <label for="txtTelefono">Telefono</label>
                    </div> 
                    
                       <div class="input-field col l4 s12">
                        <input  id="txtCorreo" type="text" name="correo" required class="validate" maxlength="50">
                        <label for="txtCorreo">Correo</label>
                    </div>
                    
                     <div class="input-field col l4 s12">
                        <input  id="txtEncargado" type="text" name="encargado" required class="validate" maxlength="50">
                        <label for="txtEncargado">Encargado</label>
                    </div>
                    <div class="input-field col l4 s12">
                        <input  id="txtSeccion" type="text" name="seccion" required class="validate" maxlength="50">
                        <label for="txtSeccion">Seccion</label>
                    </div>
                    <div class="input-field col l4 s12">
                        <input  id="txtFechanacimiento" type="text" name="fechanacimiento" required class="validate" maxlength="50">
                        <label for="txtFechanacimiento">Fecha de nacimiento</label>
                    </div>
                    <div class="input-field col l4 s12">   
                        <jsp:include page="/Views/Rol/select.jsp">                           
                            <jsp:param name="id" value="0" />  
                        </jsp:include>  
                        <span id="slRol_error" style="color:red" class="helper-text"></span>
                    </div>
                </div>
                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">save</i>Guardar</button>
                        <a href="Estudiante" class="waves-effect waves-light btn blue"><i class="material-icons right">cancel</i>Cancelar</a>                          
                    </div>
                </div>
            </form>          
        </main>
                        
        <jsp:include page="/Views/Shared/footerBody.jsp" />   
        <script>
            function validarFormulario() {
                var result = true;
                var slEstudiante = document.getElementById("slEstudiante");
                var slEstudiante_error = document.getElementById("slEstudiante_error");
                if (slEstudiante.value == 0) {
                    slEstudiante_error.innerHTML = "El Estudiante es obligatorio";
                    result = false;
                } else {
                    slEstudiante_error.innerHTML = "";
                }

                return result;
            }
        </script>
    </body>
</html>