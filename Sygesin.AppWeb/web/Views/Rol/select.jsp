<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="sygesin.entidadesdenegocio.Rol"%>
<%@page import="sygesin.accesoadatos.RolDAL"%>
<%@page import="java.util.ArrayList"%>

<% ArrayList<Rol> roles = RolDAL.obtenerTodos();
    int id = Integer.parseInt(request.getParameter("id"));
%>
<select id="slRol" name="idRol">
    <option <%=(id == 0) ? "selected" : ""%>  value="0">SELECCIONAR</option>
    <% for (Rol rol : roles) {%>
    <option <%=(id == rol.getId()) ? "selected" : ""%>  value="<%=rol.getId()%>"><%= rol.getNombre()%></option>
    <%}%>
</select>
<label for="idRol">Rol</label>