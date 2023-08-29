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
import sygesin.accesoadatos.EstudianteDAL;
import sygesin.appweb.utils.*;
import sygesin.entidadesdenegocio.Rol;
import sygesin.entidadesdenegocio.Estudiante;

@WebServlet(name = "EstudianteServlet", urlPatterns = {"/Estudiante"})
public class EstudianteServlet extends HttpServlet {

    private Estudiante obtenerEstudiante(HttpServletRequest request) {
        String accion = Utilidad.getParameter(request, "accion", "index");
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre(Utilidad.getParameter(request, "nombre", ""));
        estudiante.setApellido(Utilidad.getParameter(request, "apellido", ""));
        estudiante.setDireccion(Utilidad.getParameter(request, "direccion", ""));
        estudiante.setTelefono(Utilidad.getParameter(request, "telefono", ""));
        estudiante.setEncargado(Utilidad.getParameter(request, "encargado", ""));
        estudiante.setSeccion(Utilidad.getParameter(request, "seccion", ""));
        estudiante.setFechanacimiento(Utilidad.getParameter(request, "fechanacimiento", ""));
        estudiante.setPassword(Utilidad.getParameter(request, "password", ""));
        estudiante.setLogin(Utilidad.getParameter(request, "login", ""));
        estudiante.setDepartamento(Integer.parseInt(Utilidad.getParameter(request, "departamento", "0")));
        estudiante.setSeccionId(Integer.parseInt(Utilidad.getParameter(request, "idseccion", "0")));
        estudiante.setRolId(Integer.parseInt(Utilidad.getParameter(request, "idRol", "0")));
        estudiante.setEstatus(Byte.parseByte(Utilidad.getParameter(request, "estatus", "0")));

        if (accion.equals("index")) {
            estudiante.setTop_aux(Integer.parseInt(Utilidad.getParameter(request, "top_aux", "10")));
            estudiante.setTop_aux(estudiante.getTop_aux() == 0 ? Integer.MAX_VALUE : estudiante.getTop_aux());
        }

        if (accion.equals("login") || accion.equals("create") || accion.equals("cambiarpass")) {
            estudiante.setPassword(Utilidad.getParameter(request, "password", ""));
            estudiante.setConfirmPassword_aux(Utilidad.getParameter(request, "confirmPassword_aux", ""));
            if (accion.equals("cambiarpass")) {
                estudiante.setId(Integer.parseInt(Utilidad.getParameter(request, "id", "0")));
            }
        } else {
            estudiante.setId(Integer.parseInt(Utilidad.getParameter(request, "id", "0")));
        }
        return estudiante;
    }

    private void doGetRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Estudiante estudiante = new Estudiante();
            estudiante.setTop_aux(10);
            ArrayList<Estudiante> estudiantes = EstudianteDAL.buscarIncluirRol(estudiante);
            request.setAttribute("estudiantes", estudiantes);
            request.setAttribute("top_aux", estudiante.getTop_aux());
            request.getRequestDispatcher("Views/Estudiante/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doPostRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Estudiante estudiante = obtenerEstudiante(request);
            ArrayList<Estudiante> estudiantes = EstudianteDAL.buscarIncluirRol(estudiante);
            request.setAttribute("estudiantes", estudiantes);
            request.setAttribute("top_aux", estudiante.getTop_aux());
            request.getRequestDispatcher("Views/Estudiante/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doGetRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Views/Estudiante/create.jsp").forward(request, response);
    }

    private void doPostRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Estudiante estudiante = obtenerEstudiante(request);
            int result = EstudianteDAL.crear(estudiante);
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
            Estudiante estudiante = obtenerEstudiante(request);
            Estudiante estudiante_result = EstudianteDAL.obtenerPorId(estudiante);
            if (estudiante_result.getId() > 0) {
                Rol rol = new Rol();
                rol.setId(estudiante_result.getRolId());
                estudiante_result.setRol(RolDAL.obtenerPorId(rol));
                request.setAttribute("estudiante", estudiante_result);
            } else {
                Utilidad.enviarError("El Id:" + estudiante_result.getId() + " no existe en la tabla de Estudiante", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doGetRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Estudiante/edit.jsp").forward(request, response);
    }

    private void doPostRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Estudiante estudiante = obtenerEstudiante(request);
            int result = EstudianteDAL.modificar(estudiante);
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
        request.getRequestDispatcher("Views/Estudiante/details.jsp").forward(request, response);
    }

    private void doGetRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Estudiante/delete.jsp").forward(request, response);
    }

    private void doPostRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Estudiante estudiante = obtenerEstudiante(request);
            int result = EstudianteDAL.eliminar(estudiante);
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

    private void doGetRequestLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionEstud.cerrarSession(request);
        request.getRequestDispatcher("Views/Estudiante/login.jsp").forward(request, response);
    }

    private void doPostRequestLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Estudiante estudiante = obtenerEstudiante(request);
            Estudiante estudiante_auth = EstudianteDAL.login(estudiante);
            if (estudiante_auth.getId() != 0 && estudiante_auth.getLogin().equals(estudiante.getLogin())) {
                Rol rol = new Rol();
                rol.setId(estudiante_auth.getRolId());
                estudiante_auth.setRol(RolDAL.obtenerPorId(rol));
                SessionEstud.autenticarEstud(request, estudiante_auth);
                response.sendRedirect("Home");
            } else {
                request.setAttribute("error", "Credenciales incorrectas");
                request.setAttribute("accion", "login");
                doGetRequestLogin(request, response);
            }
        } catch (Exception ex) {
            request.setAttribute("error", ex.getMessage());
        }
    }

    private void doGetRequestCambiarPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Estudiante estudiante = new Estudiante();
            estudiante.setLogin(SessionEstud.getEstud(request));
            Estudiante estudiante_result = EstudianteDAL.buscar(estudiante).get(0);
            if (estudiante_result.getId() > 0) {
                request.setAttribute("estudiante", estudiante_result);
                request.getRequestDispatcher("Views/Estudiante/cambiarPassword.jsp").forward(request, response);
            } else {
                Utilidad.enviarError("El Id:" + estudiante_result.getId() + " no existe en la tabla de Estudiante", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doPostRequestCambiarPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Estudiante estudiante = obtenerEstudiante(request);
            String passActual = Utilidad.getParameter(request, "passwordActual", "");
            int result = EstudianteDAL.cambiarPassword(estudiante, passActual);
            if (result != 0) {
                response.sendRedirect("Estudiante?accion=login");
            } else {
                Utilidad.enviarError("No se logro cambiar el password", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = Utilidad.getParameter(request, "accion", "index");
        if (accion.equals("login")) {
            request.setAttribute("accion", accion);
            doGetRequestLogin(request, response);
        } else {
            SessionUser.authorize(request, response, () -> {
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
                    case "cambiarpass":
                        request.setAttribute("accion", accion);
                        doGetRequestCambiarPassword(request, response);
                        break;
                    default:
                        request.setAttribute("accion", accion);
                        doGetRequestIndex(request, response);
                }
            });
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = Utilidad.getParameter(request, "accion", "index");
        if (accion.equals("login")) {
            request.setAttribute("accion", accion);
            doPostRequestLogin(request, response);
        } else {
            SessionUser.authorize(request, response, () -> {
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
                    case "cambiarpass":
                        request.setAttribute("accion", accion);
                        doPostRequestCambiarPassword(request, response);
                        break;
                    default:
                        request.setAttribute("accion", accion);
                        doGetRequestIndex(request, response);
                }
            });
        }
    }

}
