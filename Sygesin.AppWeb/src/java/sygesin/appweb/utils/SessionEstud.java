
package sygesin.appweb.utils;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import sygesin.entidadesdenegocio.*;

public class SessionEstud {
    
      public static void autenticarEstud(HttpServletRequest request, Estudiante pEstud) {
        HttpSession session = (HttpSession) request.getSession();
        session.setMaxInactiveInterval(3600);
        session.setAttribute("auth", true);
        session.setAttribute("estud", pEstud.getLogin());
        session.setAttribute("rol", pEstud.getRol().getNombre());
    }

    public static boolean isAuth(HttpServletRequest request) {
        HttpSession session = (HttpSession) request.getSession();
        boolean auth = session.getAttribute("auth") != null ? (boolean) session.getAttribute("auth") : false;
        return auth;
    }

    public static String getEstud(HttpServletRequest request) {
        HttpSession session = (HttpSession) request.getSession();
        String estud = "";
        if (SessionEstud.isAuth(request)) {
            estud = session.getAttribute("estud") != null ? (String) session.getAttribute("estud") : "";
        }
        return estud;
    }

    public static String getRol(HttpServletRequest request) {
        HttpSession session = (HttpSession) request.getSession();
        String estud = "";
        if (SessionEstud.isAuth(request)) {
            estud = session.getAttribute("rol") != null ? (String) session.getAttribute("rol") : "";
        }
        return estud;
    }

    public static void authorize(HttpServletRequest request, HttpServletResponse response, IAuthorize pIAuthorize) throws ServletException, IOException {
        if (SessionEstud.isAuth(request)) {
            pIAuthorize.authorize();
        } else {
            response.sendRedirect("Estudiante?accion=login");
        }
    }

    public static void cerrarSession(HttpServletRequest request) {
        HttpSession session = (HttpSession) request.getSession();
        session.invalidate();
    }
    
}
