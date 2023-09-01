
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="sygesin.entidadesdenegocio.Administrador"%>
<%@page import="sygesin.entidadesdenegocio.Rol"%>
<%@page import="java.util.ArrayList"%>
<% ArrayList<Administrador> administradores = (ArrayList<Administrador>) request.getAttribute("administradores");
    int numPage = 1;
    int numReg = 10;
    int countReg = 0;
    if (administradores == null) {
        administradores = new ArrayList();
    } else if (administradores.size() > numReg) {
        double divNumPage = (double) administradores.size() / (double) numReg;
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
        <title>Lista de Administradores</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />
        <main class="container">
            <h5 class="font-semibold m-5 text-center">Buscar Administrador</h5>
            <form action="Administrador" method="post">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>">
                <div class="row">
                    <div class="input-field col l4 s12">
                        <input id="txtNombre" type="text" name="nombre">
                        <label for="txtNombre">Nombre</label>
                    </div>
                    <div class="input-field col l4 s12">
                        <input id="txtApellido" type="text" name="apellido">
                        <label for="txtApellido">Apellido</label>
                    </div>
                    <div class="input-field col l4 s12">
                        <input id="txtLogin" type="text" name="login">
                        <label for="txtLogin">Login</label>
                    </div>
                    <div class="input-field col l4 s12">
                        <select id="slEstatus" name="estatusAdministrador">
                            <option value="0">SELECCIONAR</option>
                            <option value="<%=Administrador.EstatusAdministrador.ACTIVO%>">ACTIVO</option>
                            <option value="<%=Administrador.EstatusAdministrador.INACTIVO%>">INACTIVO</option>
                        </select>
                        <label for="slEstatus">Estatus</label>
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
                        <button type="submit" class="waves-effect waves-light btn blue"><i class="material-icons right">search</i>Buscar</button>
                        <a href="Administrador?accion=create" class="waves-effect waves-light btn blue"><i class="material-icons right">add</i>Crear</a>
                    </div>
                </div>
            </form>
            <div class="overflow-x-auto">
                <div class="flex items-center justify-start font-sans overflow-hidden">
                    <div class="w-full lg:w-5/6">
                        <div class="bg-white shadow-md rounded my-6">
                            <table class="min-w-max w-full table-auto">
                                <thead>
                                    <tr class="bg-gray-200 text-gray-600 uppercase text-sm leading-normal">
                                        <th class="py-3 px-6 text-left">Nombre</th>
                                        <th class="py-3 px-6 text-left">Apellido</th>
                                        <th class="py-3 px-6 text-left">Login</th>
                                        <th class="py-3 px-6 text-left">Estatus</th>
                                        <th class="py-3 px-6 text-left">Rol</th>
                                        <th class="py-3 px-6 text-left">Fecha registro</th>
                                        <th class="py-3 px-6 text-center">Acciones</th>
                                    </tr>
                                </thead>
                                <tbody class="text-gray-600 text-sm font-light">
                                    <% for (Administrador administrador : administradores) {
                                           int tempNumPage = numPage;
                                           if (numPage > 1) {
                                               countReg++;
                                               double divTempNumPage = (double) countReg / (double) numReg;
                                               tempNumPage = (int) Math.ceil(divTempNumPage);
                                           }
                                           String estatus = "";
                                           switch (administrador.getEstatus()) {
                                               case Administrador.EstatusAdministrador.ACTIVO:
                                                   estatus = "ACTIVO";
                                                   break;
                                               case Administrador.EstatusAdministrador.INACTIVO:
                                                   estatus = "INACTIVO";
                                                   break;
                                               default:
                                                   estatus = "";
                                           }
                                    %>
                                    <tr>
                                        <td class="py-3 px-6 text-left"><%=administrador.getNombre()%></td>
                                        <td class="py-3 px-6 text-left"><%=administrador.getApellido()%></td>
                                        <td class="py-3 px-6 text-left"><%=administrador.getLogin()%></td>
                                        <td class="py-3 px-6 text-left"><%=estatus%></td>
                                        <td class="py-3 px-6 text-left"><%=administrador.getRol().getNombre()%></td>
                                        <td class="py-3 px-6 text-left"><%=administrador.getFechaRegistro()%></td>
                                        <td class="py-3 px-6 text-center">
                                            <div class="flex item-center justify-center">
                                                <a href="Administrador?accion=edit&id=<%=administrador.getId()%>" title="Modificar" class="w-4 mr-2 transform hover:text-purple-500 hover:scale-110 px-5">
                                                    <i class="material-icons">edit</i>
                                                </a>
                                                <a href="Administrador?accion=details&id=<%=administrador.getId()%>" title="Ver" class="w-4 mr-2 transform hover:text-purple-500 hover:scale-110 px-5">
                                                    <i class="material-icons">description</i>
                                                </a>
                                                <a href="Administrador?accion=delete&id=<%=administrador.getId()%>" title="Eliminar" class="w-4 mr-2 transform hover:text-purple-500 hover:scale-110 px-5">
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

