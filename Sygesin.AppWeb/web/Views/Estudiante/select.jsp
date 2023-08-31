<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="sygesin.entidadesdenegocio.Estudiante"%>
<%@page import="sygesin.entidadesdenegocio.EstudianteDAL"%>

<%@page import="java.util.ArrayList"%>

<% ArrayList<Estudiante> estudiantes = EstudianteDAL.obtenerTodos();
    int id = Integer.parseInt(request.getParameter("id"));
%>
<select id="slEstudiante" name="idEstudiante">
    <option <%=(id == 0) ? "selected" : ""%>  value="0">SELECCIONAR</option>
    <% for (Estudiante estudiante : estudiantes) {%>
        <option <%=(id == estudiante.getId()) ? "selected" : "" %>  value="<%=estudiante.getId()%>"><%= estudiante.getNombre()%></option>
    <%}%>
</select>
<label for="idEstudiante">Estudiante</label>