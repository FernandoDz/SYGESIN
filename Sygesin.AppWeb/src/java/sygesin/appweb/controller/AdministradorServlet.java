package sygesin.appweb.controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import sygesin.accesoadatos.AdministradorDAL;
import sygesin.accesoadatos.RolDAL;
import sygesin.appweb.utils.*;
import sygesin.entidadesdenegocio.Rol;
import sygesin.entidadesdenegocio.Administrador;

@WebServlet(name = "AdministradorServlet", urlPatterns = {"/Administrador"})
public class AdministradorServlet extends HttpServlet {

    private Administrador obtenerAdministrador(HttpServletRequest request) {

        String accion = Utilidad.getParameter(request, "accion", "index");
        Administrador administrador = new Administrador();
        administrador.setNombre(Utilidad.getParameter(request, "nombre", ""));
        administrador.setApellido(Utilidad.getParameter(request, "apellido", ""));
        administrador.setLogin(Utilidad.getParameter(request, "login", ""));
        administrador.setIdRol(Integer.parseInt(Utilidad.getParameter(request, "idRol", "0")));
        administrador.setEstatus(Byte.parseByte(Utilidad.getParameter(request, "EstatusAdministrador", "0")));

        if (accion.equals("index")) {
            administrador.setTop_aux(Integer.parseInt(Utilidad.getParameter(request, "top_aux", "10")));
            administrador.setTop_aux(administrador.getTop_aux() == 0 ? Integer.MAX_VALUE : administrador.getTop_aux());
        }

        if (accion.equals("login") || accion.equals("create") || accion.equals("cambiarpass")) {
           administrador.setId(Integer.parseInt(Utilidad.getParameter(request, "id", "0")));
            administrador.setConfirmPassword_aux(Utilidad.getParameter(request, "confirmPassword_aux", ""));
            if (accion.equals("cambiarpass")) {
                administrador.setId(Integer.parseInt(Utilidad.getParameter(request, "id", "0")));
            }
        } else {
            administrador.setId(Integer.parseInt(Utilidad.getParameter(request, "id", "0")));
        }
        return administrador;
    }

    private void doGetRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Administrador administrador = new Administrador();
            administrador.setTop_aux(10);
            ArrayList<Administrador> administradores = AdministradorDAL.buscarIncluirRol(administrador);
            request.setAttribute("administradores", administradores);
            request.setAttribute("top_aux", administrador.getTop_aux());
            request.getRequestDispatcher("Views/Administrador/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doPostRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Administrador administrador = obtenerAdministrador(request);
            ArrayList<Administrador> administradores = AdministradorDAL.buscarIncluirRol(administrador);
            request.setAttribute("administradores", administradores);
            request.setAttribute("top_aux", administrador.getTop_aux());
            request.getRequestDispatcher("Views/Administrador/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doGetRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Views/Administrador/create.jsp").forward(request, response);
    }

    private void doPostRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Administrador administrador = obtenerAdministrador(request);
            int result = AdministradorDAL.crear(administrador);
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
            Administrador administrador = obtenerAdministrador(request);
            Administrador administrador_result = AdministradorDAL.obtenerPorId(administrador);
            if (administrador_result.getId() > 0) {
                Rol rol = new Rol();
                rol.setId(administrador_result.getIdRol());
                administrador_result.setRol(RolDAL.obtenerPorId(rol));
                request.setAttribute("administrador", administrador_result);
            } else {
                Utilidad.enviarError("El Id:" + administrador_result.getId() + " no existe en la tabla de Administrador", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doGetRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Administrador/edit.jsp").forward(request, response);
    }

    private void doPostRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Administrador administrador = obtenerAdministrador(request);
            int result = AdministradorDAL.modificar(administrador);
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
        request.getRequestDispatcher("Views/Administrador/details.jsp").forward(request, response);
    }

    private void doGetRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Administrador/delete.jsp").forward(request, response);
    }

    private void doPostRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Administrador administrador = obtenerAdministrador(request);
            int result = AdministradorDAL.eliminar(administrador);
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
        SessionUser.cerrarSession(request);
        request.getRequestDispatcher("Views/Administrador/login.jsp").forward(request, response);
    }

    private void doPostRequestLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Administrador administrador = obtenerAdministrador(request);
            Administrador administrador_auth = AdministradorDAL.login(administrador);
            if (administrador_auth.getId() != 0 && administrador_auth.getLogin().equals(administrador.getLogin())) {
                Rol rol = new Rol();
                rol.setId(administrador_auth.getIdRol());
                administrador_auth.setRol(RolDAL.obtenerPorId(rol));
                SessionAdmin.autenticarAdmin(request, administrador_auth);
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
            Administrador administrador = new Administrador();
            administrador.setLogin(SessionAdmin.getAdmin(request));
            Administrador administrador_result = AdministradorDAL.buscar(administrador).get(0);
            if (administrador_result.getId() > 0) {
                request.setAttribute("administrador", administrador_result);
                request.getRequestDispatcher("Views/Administrador/cambiarPassword.jsp").forward(request, response);
            } else {
                Utilidad.enviarError("El Id:" + administrador_result.getId() + " no existe en la tabla de Administrador", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doPostRequestCambiarPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Administrador administrador = obtenerAdministrador(request);
            String passActual = Utilidad.getParameter(request, "passwordActual", "");
            int result = AdministradorDAL.cambiarPassword(administrador, passActual);
            if (result != 0) {
                response.sendRedirect("Administrador?accion=login");
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
            SessionAdmin.authorize(request, response, () -> {
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
            SessionAdmin.authorize(request, response, () -> {
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
