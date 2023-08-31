
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="sygesin.entidadesdenegocio.Estudiante"%>
<% Estudiante estudiante = (Estudiante) request.getAttribute("estudiante");%>

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Eliminar Estudiante</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Eliminar Estudiante</h5>
            <form action="Estudiante" method="post">  
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>"> 
                <input type="hidden" name="id" value="<%=empresa.getId()%>">  
                <div class="row">
                   <div class="input-field col l4 s12">
                        <input  id="txtIdRold" type="text" value="<%=estudiante.getIdrol()%>" disabled>
                        <label for="txtIdRold">Rol</label>
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
                </div>
                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">delete</i>Eliminar</button>
                        <a href="Estudiante" class="waves-effect waves-light btn blue"><i class="material-icons right">cancel</i>Cancelar</a>                          
                    </div>
                </div>
            </form>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />
    </body>
</html>