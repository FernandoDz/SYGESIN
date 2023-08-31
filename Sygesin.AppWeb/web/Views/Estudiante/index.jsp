
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="sygesin.entidadesdenegocio.Estudiante"%>
<%@page import="java.util.ArrayList"%>

<% ArrayList<Estudiante> estudiantes = (ArrayList<Estudiante>) request.getAttribute("estudiantes");
    int numPage = 1;
    int numReg = 10;
    int countReg = 0;
    if (estudiantes == null) {
        estudiantes = new ArrayList();
    } else if (estudiantes.size() > numReg) {
        double divNumPage = (double) estudiantes.size() / (double) numReg;
        numPage = (int) Math.ceil(divNumPage);
    }
    String strTop_aux = request.getParameter("top_aux");
    int top_aux = 10;
    if (strTop_aux != null && strTop_aux.trim().length() > 0) {
        top_aux = Integer.parseInt(strTop_aux);
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Lista de Estudiante</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Buscar Estudiante</h5>
            <form action="Estudiante" method="post">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>"> 
                <div class="row">
                    
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
                            <jsp:param name="id" value="0" />  
                        </jsp:include>                        
                    </div>
                                    
                    <div class="input-field col l3 s12">   
                        <jsp:include page="/Views/Shared/selectTop.jsp">
                            <jsp:param name="top_aux" value="<%=top_aux%>" />                        
                        </jsp:include>                        
                    </div> 
                </div>
                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">search</i>Buscar</button>
                        <a href="Estudiante?accion=create" class="waves-effect waves-light btn blue"><i class="material-icons right">add</i>Crear</a>                          
                    </div>
                </div>
            </form>

            <div class="row">
                <div class="col l12 s12">
                    <div style="overflow: auto">
                        <table class="paginationjs">
                            <thead>
                                <tr>
                                    <th>Rol</th>
                                     <th>Nombre</th>
                                      <th>Apellido</th>
                                       <th>Direccion</th>
                                        <th>Departamento</th>
                                         <th>Telefono</th>
                                          <th>Correo</th>
                                           <th>Encargado</th>
                                            <th>Seccion</th>
                                             <th>Fecha de nacimiento</th>
                                    
                                </tr>
                            </thead>                       
                            <tbody>                           
                                <% for (Estudiante estudiante : estudiantes) {
                                        int tempNumPage = numPage;
                                        if (numPage > 1) {
                                            countReg++;
                                            double divTempNumPage = (double) countReg / (double) numReg;
                                            tempNumPage = (int) Math.ceil(divTempNumPage);
                                        }
                                %>
                                <tr data-page="<%= tempNumPage%>">
                                    
                                    <td><%=estudiante.getNombre()%></td>
                                    <td><%=estudiante.getApellido()%></td>
                                    <td><%=estudiante.getDireccion()%></td>
                                    <td><%=estudiante.getDepartamento()%></td>
                                    <td><%=estudiante.getCorreo()%></td>
                                    <td><%=estudiante.getEncargado()%></td>
                                    <td><%=estudiante.getSeccion()%></td>
                                    <td><%=estudiante.getFechanacimiento()%></td>
                                    <td>
                                        <div style="display:flex">
                                            <a href="Contacto?accion=edit&id=<%=estudiante.getId()%>" title="Modificar" class="waves-effect waves-light btn green">
                                                <i class="material-icons">edit</i>
                                            </a>
                                            <a href="Contacto?accion=details&id=<%=estudiante.getId()%>" title="Ver" class="waves-effect waves-light btn blue">
                                                <i class="material-icons">description</i>
                                            </a>
                                            <a href="Contacto?accion=delete&id=<%=estudiante.getId()%>" title="Eliminar" class="waves-effect waves-light btn red">
                                                <i class="material-icons">delete</i>
                                            </a>     
                                        </div>
                                    </td>                                   
                                </tr>
                                <% } %>                                                       
                            </tbody>
                        </table>
                    </div>                  
                </div>
            </div>
            <div class="row">
                <div class="col l12 s12">
                    <jsp:include page="/Views/Shared/paginacion.jsp">
                        <jsp:param name="numPage" value="<%= numPage%>" />                        
                    </jsp:include>
                </div>
            </div>
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />
    </body>
</html>