
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="sygesin.entidadesdenegocio.Administrador"%>
<% Administrador administrador = (Administrador) request.getAttribute("administrador");%>

<!DOCTYPE html>
<html>
    <head>        
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Eliminar Administrador</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5 class="font-semibold">Eliminar Administrador</h5>
            <form action="Administrador" method="post">  
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>"> 
                <input type="hidden" name="id" value="<%=administrador.getId()%>">  
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
                        <select id="slEstatus" name="estatusAdministrador" disabled>
                            <option value="0" <%=(administrador.getEstatus() == 10) ? "selected" : ""%>>SELECCIONAR</option>
                            <option value="<%=Administrador.EstatusAdministrador.ACTIVO%>"  <%=(administrador.getEstatus() == Administrador.EstatusAdministrador.ACTIVO) ? "selected" : ""%>>ACTIVO</option>
                            <option value="<%=Administrador.EstatusAdministrador.INACTIVO%>"  <%=(administrador.getEstatus() == Administrador.EstatusAdministrador.INACTIVO) ? "selected" : ""%>>INACTIVO</option>
                        </select>       
                        <label for="slEstatus">Estatus</label>                       
                    </div>
                    <div class="input-field col l4 s12">
                        <input id="txtRol" type="text" value="<%=administrador.getRol().getNombre()%>" disabled>
                        <label for="txtRol">Rol</label>
                    </div> 
                </div>
                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">delete</i>Eliminar</button>
                        <a href="Administrador" class="waves-effect waves-light btn blue"><i class="material-icons right">list</i>Cancelar</a>                          
                    </div>
                </div>
            </form>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />         
    </body>
</html>
