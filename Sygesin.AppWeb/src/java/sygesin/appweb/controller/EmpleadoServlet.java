package sygesin.appweb.controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import sygesin.accesoadatos.RolDAL;
import sygesin.accesoadatos.EmpleadoDAL;
import sygesin.appweb.utils.*;
import sygesin.entidadesdenegocio.Rol;
import sygesin.entidadesdenegocio.Empleado;

@WebServlet(name = "EmpleadoServlet", urlPatterns = {"/Empleado"})
public class EmpleadoServlet extends HttpServlet {

    private Empleado obtenerEmpleado(HttpServletRequest request) {
       String accion = Utilidad.getParameter(request, "accion", "index");
        Empleado empleado = new Empleado();
        if (accion.equals("create") == false) {
            empleado.setId(Integer.parseInt(Utilidad.getParameter(request, "id", "0")));
        }
        empleado.setNombre(Utilidad.getParameter(request, "nombre", ""));
        empleado.setApellido(Utilidad.getParameter(request, "apellido", ""));
        empleado.setCargo(Utilidad.getParameter(request, "cargo", ""));
        empleado.setTelefono(Utilidad.getParameter(request, "telefono", ""));
        empleado.setDUI(Utilidad.getParameter(request, "DUI", ""));
        empleado.setIdRol(Integer.parseInt(Utilidad.getParameter(request, "idRol", "0")));

        if (accion.equals("index")) {
            empleado.setTop_aux(Integer.parseInt(Utilidad.getParameter(request, "top_aux", "10")));
            empleado.setTop_aux(empleado.getTop_aux() == 0 ? Integer.MAX_VALUE : empleado.getTop_aux());
        }
        return empleado;
    }

    private void doGetRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Empleado empleado = new Empleado();
            empleado.setTop_aux(10);
            ArrayList<Empleado> empleados = EmpleadoDAL.buscarIncluirRol(empleado);
            request.setAttribute("empleados", empleados);
            request.setAttribute("top_aux", empleado.getTop_aux());
            request.getRequestDispatcher("Views/Empleado/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doPostRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Empleado empleado = obtenerEmpleado(request);
            ArrayList<Empleado> empleados = EmpleadoDAL.buscarIncluirRol(empleado);
            request.setAttribute("empleados", empleados);
            request.setAttribute("top_aux", empleado.getTop_aux());
            request.getRequestDispatcher("Views/Empleado/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doGetRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Views/Empleado/create.jsp").forward(request, response);
    }

    private void doPostRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Empleado empleado = obtenerEmpleado(request);
            int result = EmpleadoDAL.crear(empleado);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se logro registrar un nuevo registro", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }

    }

    private void requestObtenerPorId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Empleado empleado = obtenerEmpleado(request);
            Empleado empleado_result = EmpleadoDAL.obtenerPorId(empleado);
            if (empleado_result.getId() > 0) {
                Rol rol = new Rol();
                rol.setId(empleado_result.getIdRol());
                empleado_result.setRol(RolDAL.obtenerPorId(rol));
                request.setAttribute("empleado", empleado_result);
            } else {
                Utilidad.enviarError("El Id:" + empleado_result.getId() + " no existe en la tabla de Empleado", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doGetRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Empleado/edit.jsp").forward(request, response);
    }

    private void doPostRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Empleado empleado = obtenerEmpleado(request);
            int result = EmpleadoDAL.modificar(empleado);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se logro actualizar el registro", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doGetRequestDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Empleado/details.jsp").forward(request, response);
    }

    private void doGetRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Empleado/delete.jsp").forward(request, response);
    }

    private void doPostRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Empleado empleado = obtenerEmpleado(request);
            int result = EmpleadoDAL.eliminar(empleado);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se logro eliminar el registro", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionUser.authorize(request, response, () -> {
            String accion = Utilidad.getParameter(request, "accion", "index");
            switch (accion) {
                case "index":
                    request.setAttribute("accion", accion);
                    doGetRequestIndex(request, response);
                    break;
                case "create":
                    request.setAttribute("accion", accion);
                    doGetRequestCreate(request, response);
                    break;
                case "edit":
                    request.setAttribute("accion", accion);
                    doGetRequestEdit(request, response);
                    break;
                case "delete":
                    request.setAttribute("accion", accion);
                    doGetRequestDelete(request, response);
                    break;
                case "details":
                    request.setAttribute("accion", accion);
                    doGetRequestDetails(request, response);
                    break;
                default:
                    request.setAttribute("accion", accion);
                    doGetRequestIndex(request, response);
            }
        });
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionUser.authorize(request, response, () -> {
            String accion = Utilidad.getParameter(request, "accion", "index");
            switch (accion) {
                case "index":
                    request.setAttribute("accion", accion);
                    doPostRequestIndex(request, response);
                    break;
                case "create":
                    request.setAttribute("accion", accion);
                    doPostRequestCreate(request, response);
                    break;
                case "edit":
                    request.setAttribute("accion", accion);
                    doPostRequestEdit(request, response);
                    break;
                case "delete":
                    request.setAttribute("accion", accion);
                    doPostRequestDelete(request, response);
                    break;
                default:
                    request.setAttribute("accion", accion);
                    doGetRequestIndex(request, response);
            }
        });
    }

}
