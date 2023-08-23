
package sygesin.appweb.utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import sygesin.entidadesdenegocio.Empleado;

public class SessionEmployee {
        public static void autenticarEmployee(HttpServletRequest request, Empleado pEmpleado) {
        HttpSession session = (HttpSession) request.getSession();
        session.setMaxInactiveInterval(3600);
        session.setAttribute("auth", true);
        session.setAttribute("user", pEmpleado.getLogin());
        session.setAttribute("rol", pEmpleado.getRol().getNombre());
    }

    public static boolean isAuth(HttpServletRequest request) {
        HttpSession session = (HttpSession) request.getSession();
        boolean auth = session.getAttribute("auth") != null ? (boolean) session.getAttribute("auth") : false;
        return auth;
    }

    public static String getEmployee(HttpServletRequest request) {
        HttpSession session = (HttpSession) request.getSession();
        String user = "";
        if (SessionUser.isAuth(request)) {
            user = session.getAttribute("user") != null ? (String) session.getAttribute("user") : "";
        }
        return user;
    }

    public static String getRol(HttpServletRequest request) {
        HttpSession session = (HttpSession) request.getSession();
        String user = "";
        if (SessionUser.isAuth(request)) {
            user = session.getAttribute("rol") != null ? (String) session.getAttribute("rol") : "";
        }
        return user;
    }

    public static void authorize(HttpServletRequest request, HttpServletResponse response, IAuthorize pIAuthorize) throws ServletException, IOException {
        if (SessionUser.isAuth(request)) {
            pIAuthorize.authorize();
        } else {
            response.sendRedirect("Usuario?accion=login");
        }
    }

    public static void cerrarSession(HttpServletRequest request) {
        HttpSession session = (HttpSession) request.getSession();
        session.invalidate();
    }
}
