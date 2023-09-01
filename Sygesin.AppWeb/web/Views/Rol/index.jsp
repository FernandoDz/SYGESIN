<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="sygesin.entidadesdenegocio.Rol"%>
<%@page import="java.util.ArrayList"%>
<link
    href="https://fonts.googleapis.com/css?family=Material+Icons|Material+Icons+Outlined|Material+Icons+Two+Tone|Material+Icons+Round|Material+Icons+Sharp"
    rel="stylesheet">
<script src="https://cdn.tailwindcss.com"></script>

<% ArrayList<Rol> roles = (ArrayList<Rol>) request.getAttribute("roles");
    int numPage = 1;
    int numReg = 10;
    int countReg = 0;
    if (roles == null) {
        roles = new ArrayList();
    } else if (roles.size() > numReg) {
        double divNumPage = (double) roles.size() / (double) numReg;
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
        <title>Lista de Roles</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5 class="font-semibold m-5 text-center">Buscar Rol</h5>
            <form action="Rol" method="post">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>"> 
                <div class="row">
                    <div class="input-field col l6 s12">
                        <input  id="txtNombre" type="text" name="nombre">
                        <label for="txtNombre">Nombre</label>
                    </div>                    
                    <div class="input-field col l3 s12">   
                        <jsp:include page="/Views/Shared/selectTop.jsp">
                            <jsp:param name="top_aux" value="<%=top_aux%>" />                        
                        </jsp:include>                        
                    </div> 
                </div>
                <div class="row">
                    <div class="col l12 s12">
                        <button type="submit" class="waves-effect waves-light btn blue"><i class="material-icons right">search</i>Buscar</button>
                        <a href="Rol?accion=create" class="waves-effect waves-light btn blue"><i class="material-icons right">add</i>Crear</a>                          
                    </div>
                </div>
            </form>

            <div class="overflow-x-auto">
                <div class="  flex items-center justify-start font-sans overflow-hidden">
                    <div class="w-full lg:w-5/6">
                        <div class="bg-white shadow-md rounded my-6">
                            <table class="min-w-max w-full table-auto">
                                <thead>
                                    <tr class="bg-gray-200 text-gray-600 uppercase text-sm leading-normal">
                                        <th class="py-3 px-6 text-left">Nombre</th>
                                        <th class="py-3 px-6 text-center">Acciones</th>
                                    </tr>
                                </thead>
                                <tbody class="text-gray-600 text-sm font-light">
                                    <% for (Rol rol : roles) { %>
                                    <tr>
                                        <td class="py-3 px-6 text-left"><%=rol.getNombre()%></td>                                       
                                        <td class="py-3 px-6 text-center">
                                            <div class="flex item-center justify-center">
                                                <a href="Rol?accion=edit&id=<%=rol.getId()%>" title="Modificar"  class="w-4 mr-2 transform hover:text-gray-500 hover:scale-110 px-5">
                                                    <i class="material-icons">edit</i>
                                                </a>
                                                <a href="Rol?accion=details&id=<%=rol.getId()%>" title="Ver" class="w-4 mr-2 transform hover:text-gray-500 hover:scale-110 px-5">
                                                    <i class="material-icons">description</i>
                                                </a>
                                                <a href="Rol?accion=delete&id=<%=rol.getId()%>" title="Eliminar" class="w-4 mr-2 transform hover:text-gray-500 hover:scale-110 px-5">
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
