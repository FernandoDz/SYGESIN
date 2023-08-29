
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="sygesin.entidadesdenegocio.Administrador"%>
<% Administrador administrador = (Administrador) request.getAttribute("administrador");%>

<!DOCTYPE html>
<html>
    <head>        
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Detalle de Administrador</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Detalle de Administrador</h5>
             <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtNombre" type="text" value="<%=administrador.getNombre()%>" disabled>
                        <label for="txtNombre">Nombre</label>
                    </div>                       
                    <div class="input-field col l4 s12">
                        <input  id="txtApellido" type="text" value="<%=administrador.getApellido()%>" disabled>
                        <label for="txtApellido">Apellido</label>
                    </div> 
                    <div class="input-field col l4 s12">
                        <input  id="txtLogin" type="text" value="<%=administrador.getLogin()%>" disabled>
                        <label for="txtLogin">Login</label>
                    </div>                     
                    <div class="input-field col l4 s12">   
                        <select id="slEstatus" name="estatus" disabled>
                            <option value="0" <%=(administrador.getEstatus() == 10) ? "selected" : ""%>>SELECCIONAR</option>
                            <option value="<%=Administrador.EstatusAdministrador.ACTIVO%>"  <%=(administrador.getEstatus() == Administrador.EstatusAdministrador.ACTIVO) ? "selected" : ""%>>ACTIVO</option>
                            <option value="<%=Administrador.EstatusAdministrador.INACTIVO%>"  <%=(administrador.getEstatus() == Administrador.EstatusAdministrador.INACTIVO) ? "selected" : ""%>>INACTIVO</option>
                        </select>       
                        <label for="slEstatus">Estatus</label>                       
                    </div>
                    <div class="input-field col l4 s12">
                        <input id="txtRol" type="text" value="<%=administrador.getRol().getNombre() %>" disabled>
                        <label for="txtRol">Rol</label>
                    </div> 
                </div>

                <div class="row">
                    <div class="col l12 s12">
                         <a href="Administrador?accion=edit&id=<%=administrador.getId()%>" class="waves-effect waves-light btn blue"><i class="material-icons right">edit</i>Ir modificar</a>            
                        <a href="Administrador" class="waves-effect waves-light btn blue"><i class="material-icons right">list</i>Cancelar</a>                          
                    </div>
                </div>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />         
    </body>
</html>
