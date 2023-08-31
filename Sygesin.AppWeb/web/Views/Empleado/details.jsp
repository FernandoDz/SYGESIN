<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="sygesin.entidadesdenegocio.Empleado"%>
<% Empleado empleado = (Empleado) request.getAttribute("empleado");%>

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Detalle de Empleado</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Detalle de Empleado</h5>
            <div class="row">
                <div class="input-field col l4 s12">
                    <input  id="txtNombre" type="text" value="<%=empleado.getNombre()%>" disabled>
                    <label for="txtNombre">Nombre</label>
                </div>                       
                <div class="input-field col l4 s12">
                    <input  id="txtApellido" type="text" value="<%=empleado.getApellido()%>" disabled>
                    <label for="txtApellido">Rubro</label>
                </div> 
                <div class="input-field col l4 s12">
                    <input  id="txtCargo" type="text" value="<%=empleado.getCargo()%>" disabled>
                    <label for="txtCargo">Cargo</label>
                </div>      
                <div class="input-field col l4 s12">
                    <input  id="txtTelefono" type="text" value="<%=empleado.getTelefono()%>" disabled>
                    <label for="txtTelefono">Telefono</label>
                </div>
                <div class="input-field col l4 s12">
                    <input  id="txtDUI" type="text" value="<%=empleado.getDUI()%>" disabled>
                    <label for="txtDUI">DUI</label>
                </div>
                <div class="input-field col l4 s12">
                    <input id="txtRol" type="text" value="<%=empleado.getRol().getNombre()%>" disabled>
                    <label for="txtRol">Rol</label>
                </div> 
            </div>

            <div class="row">
                <div class="col l12 s12">
                    <a href="Empleado?accion=edit&id=<%=empleado.getId()%>" class="waves-effect waves-light btn blue"><i class="material-icons right">edit</i>Ir modificar</a>            
                    <a href="Empleado" class="waves-effect waves-light btn blue"><i class="material-icons right">cancel</i>Cancelar</a>                          
                </div>
            </div>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />
    </body>
</html>