<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="sygesin.entidadesdenegocio.Empleado"%>
<% Empleado empleado = (Empleado) request.getAttribute("empleado");%>

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Editar Empleado</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5 class="font-semibold m-5">Editar Empleado</h5>
            <form action="Empleado" method="post" onsubmit="return  validarFormulario()">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>"> 
                <input type="hidden" name="id" value="<%=empleado.getId()%>">   
                <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtNombre" type="text" name="Nombre" required class="validate"  value="<%=empleado.getNombre()%>" maxlength="50">
                        <label for="txtNombre">Nombre</label>
                    </div>                       
                    <div class="input-field col l4 s12">
                        <input  id="txtApellido" type="text" name="Apellido" required class="validate" value="<%=empleado.getApellido()%>" maxlength="30">
                        <label for="txtApellido">Apellido</label>
                    </div>
                    <div class="input-field col l4 s12">
                        <input  id="txtCargo" type="text" name="Cargo" required class="validate" value="<%=empleado.getCargo()%>" maxlength="25">
                        <label for="txtCargo">Cargo</label>
                    </div> 
                    <div class="input-field col l4 s12">
                        <input  id="txtTelefono" type="text" name="Telefono" required class="validate" value="<%=empleado.getTelefono()%>" minlength="5" maxlength="32">
                        <label for="txtTelefono">Telefono</label>
                    </div>  
                    <div class="input-field col l4 s12">
                        <input  id="txtDUI" type="text" name="DUI" required class="validate"value="<%=empleado.getDUI()%>" minlength="5" maxlength="32">
                        <label for="txtDUI">DUI</label>
                    </div>  
                    <div class="input-field col l4 s12">   
                        <jsp:include page="/Views/Rol/select.jsp">                           
                            <jsp:param name="id" value="<%=empleado.getIdRol() %>" />  
                        </jsp:include>  
                        <span id="slRol_error" style="color:red" class="helper-text"></span>
                    </div>                    
                </div>

                <div class="row">
                    <div class="col l12 s12">
                        <button type="submit" class="waves-effect waves-light btn blue"><i class="material-icons right">save</i>Guardar</button>
                        <a href="Empleado" class="waves-effect waves-light btn blue"><i class="material-icons right">cancel</i>Cancelar</a>                          
                    </div>
                </div>
            </form>          
        </main>

        <jsp:include page="/Views/Shared/footerBody.jsp" />   
        <script>
            function validarFormulario() {
                var result = true;
                var slRol = document.getElementById("slRol");
                var slRol_error = document.getElementById("slRol_error");
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