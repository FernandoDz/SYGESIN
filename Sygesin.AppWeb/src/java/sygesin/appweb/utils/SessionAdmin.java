
package sygesin.appweb.utils;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import sygesin.entidadesdenegocio.*;
public class SessionAdmin {
    
     public static void autenticarAdmin(HttpServletRequest request, Administrador pAdmin) {
        HttpSession session = (HttpSession) request.getSession();
        session.setMaxInactiveInterval(3600);
        session.setAttribute("auth", true);
        session.setAttribute("admin", pAdmin.getLogin());
        session.setAttribute("rol", pAdmin.getRol().getNombre());
    }

    public static boolean isAuth(HttpServletRequest request) {
        HttpSession session = (HttpSession) request.getSession();
        boolean auth = session.getAttribute("auth") != null ? (boolean) session.getAttribute("auth") : false;
        return auth;
    }

    public static String getAdmin(HttpServletRequest request) {
        HttpSession session = (HttpSession) request.getSession();
        String admin = "";
        if (SessionAdmin.isAuth(request)) {
            admin = session.getAttribute("admin") != null ? (String) session.getAttribute("admin") : "";
        }
        return admin;
    }

    public static String getRol(HttpServletRequest request) {
        HttpSession session = (HttpSession) request.getSession();
        String admin = "";
        if (SessionAdmin.isAuth(request)) {
            admin = session.getAttribute("rol") != null ? (String) session.getAttribute("rol") : "";
        }
        return admin;
    }

    public static void authorize(HttpServletRequest request, HttpServletResponse response, IAuthorize pIAuthorize) throws ServletException, IOException {
        if (SessionAdmin.isAuth(request)) {
            pIAuthorize.authorize();
        } else {
            response.sendRedirect("Administrador?accion=login");
        }
    }

    public static void cerrarSession(HttpServletRequest request) {
        HttpSession session = (HttpSession) request.getSession();
        session.invalidate();
    }
    
    
    
}
