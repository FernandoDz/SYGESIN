package sygesin.accesoadatos;

import java.time.LocalDate;
import java.util.*;
import java.sql.*;
import sygesin.entidadesdenegocio.*;

public class AdministradorDAL {

    public static String encriptarMD5(String txt) throws Exception {
        try {
            StringBuffer sb;
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(txt.getBytes());
            sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException ex) {
            throw ex;
        }

    }

    static String obtenerCampos() {
        return "a.Id, a.IdRol, a.Nombre, a.Apellido, a.Login, a.EstatusAdministrador, a.FechaRegistro";
    }

    private static String obtenerSelect(Administrador pAdmin) {
        String sql;
        sql = "SELECT ";
        if (pAdmin.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {
            sql += "TOP " + pAdmin.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + " FROM Administrador a");
        return sql;
    }

    private static String agregarOrderBy(Administrador pAdmin) {
        String sql = " ORDER BY a.Id DESC";
        if (pAdmin.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.MYSQL) {
            sql += " LIMIT " + pAdmin.getTop_aux() + " ";
        }
        return sql;
    }

    private static boolean existeLogin(Administrador pAdmin) throws Exception {
        boolean existe = false;
        ArrayList<Administrador> administradores = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pAdmin);
            sql += " WHERE a.Id<>? AND a.Login=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pAdmin.getId());
                ps.setString(2, pAdmin.getLogin());
                obtenerDatos(ps, administradores);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        if (administradores.size() > 0) {
            Administrador administrador = administradores.get(0);
            if (administrador.getId() > 0 && administrador.getLogin().equals(pAdmin.getLogin())) {
                existe = true;
            }
        }
        return existe;
    }
     public static int crear(Administrador pAdmin) throws Exception {
        int result;
        String sql;
        boolean existe = existeLogin(pAdmin);
        if (existe == false) {
            try (Connection conn = ComunDB.obtenerConexion();) {
                sql = "INSERT INTO Administrador(IdRol,Nombre,Apellido,Login,Password,EstatusAdministrador,FechaRegistro) VALUES(?,?,?,?,?,?,?)";
                try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                    ps.setInt(1, pAdmin.getId());
                    ps.setString(2, pAdmin.getNombre());
                    ps.setString(3, pAdmin.getApellido()); 
                    ps.setString(4, pAdmin.getLogin());
                    ps.setString(5, encriptarMD5(pAdmin.getPassword())); 
                    ps.setByte(6, pAdmin.getEstatus());
                    ps.setDate(7, java.sql.Date.valueOf(LocalDate.now()));
                    result = ps.executeUpdate();
                    ps.close();
                } catch (SQLException ex) {
                    throw ex;
                }
                conn.close();
            }
            catch (SQLException ex) {
                throw ex;
            }
        } else {
            result = 0;
            throw new RuntimeException("Login ya existe");
        }
        return result;
    }
      public static int modificar(Administrador pAdmin) throws Exception {
        int result;
        String sql;
        boolean existe = existeLogin(pAdmin);
        if (existe == false) {
            try (Connection conn = ComunDB.obtenerConexion();) {                
                sql = "UPDATE Administrador SET RolId=?, Nombre=?, Apellido=?, Login=?, EstatusAdministrador=? WHERE Id=?";
                try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                    ps.setInt(1, pAdmin.getIdRol());
                    ps.setString(2, pAdmin.getNombre());  
                    ps.setString(3, pAdmin.getApellido());
                    ps.setString(4, pAdmin.getLogin());
                    ps.setByte(5, pAdmin.getEstatus());
                    ps.setInt(6, pAdmin.getId());
                    result = ps.executeUpdate();
                    ps.close();
                } catch (SQLException ex) {
                    throw ex;
                }
                conn.close();
            } 
            catch (SQLException ex) {
                throw ex;
            }
        } else {
            result = 0;
            throw new RuntimeException("Login ya existe");
        }
        return result;
    }
    
       public static int eliminar(Administrador pAdmin) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { 
            sql = "DELETE FROM Administrador WHERE Id=?"; 
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pAdmin.getId());
                result = ps.executeUpdate();
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        return result;
    }
    
    
     private static void obtenerDatos(PreparedStatement pPS, ArrayList<Administrador> pAdmin) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) { 
            while (resultSet.next()) {
                Administrador administradores = new Administrador();
                asignarDatosResultSet(administradores, resultSet, 0);
                pAdmin.add(administradores);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }
     
     static int asignarDatosResultSet(Administrador pAdmin, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        pAdmin.setId(pResultSet.getInt(pIndex)); 
        pIndex++;
        pAdmin.setId(pResultSet.getInt(pIndex)); 
        pIndex++;
        pAdmin.setNombre(pResultSet.getString(pIndex)); 
        pIndex++;
        pAdmin.setApellido(pResultSet.getString(pIndex)); 
        pIndex++;
        pAdmin.setLogin(pResultSet.getString(pIndex)); 
        pIndex++;
        pAdmin.setEstatus(pResultSet.getByte(pIndex)); 
        pIndex++;
        pAdmin.setFechaRegistro(pResultSet.getDate(pIndex).toLocalDate()); 
        return pIndex;
    }
    
   
    
    private static void obtenerDatosIncluirRol(PreparedStatement pPS, ArrayList<Administrador> pAdmin) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) {
            HashMap<Integer, Rol> rolMap = new HashMap(); 
            while (resultSet.next()) {
                Administrador administradores = new Administrador();
                int index = asignarDatosResultSet(administradores, resultSet, 0);
                if (rolMap.containsKey(administradores.getId()) == false) {
                    Rol rol = new Rol();
                    RolDAL.asignarDatosResultSet(rol, resultSet, index);
                    rolMap.put(rol.getId(), rol); 
                    administradores.setRol(rol); 
                } else {
                    administradores.setRol(rolMap.get(administradores.getId())); 
                }
                pAdmin.add(administradores); 
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex; 
        }
    }
    
    public static Administrador obtenerPorId(Administrador pAdmin) throws Exception {
        Administrador administrador = new Administrador();
        ArrayList<Administrador> administradores = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pAdmin);
            sql += " WHERE a.Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pAdmin.getId());
                obtenerDatos(ps, administradores);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        if (administradores.size() > 0) {
            administrador = administradores.get(0);
        }
        return administrador;
    }
    
    public static ArrayList<Administrador> obtenerTodos() throws Exception {
        ArrayList<Administrador> administradores;
        administradores = new ArrayList<>();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(new Administrador()); 
            sql += agregarOrderBy(new Administrador());
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                obtenerDatos(ps, administradores);
                ps.close();
            } catch (SQLException ex) {
                throw ex; 
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        return administradores;
    }
    
    static void querySelect(Administrador pAdmin, ComunDB.utilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement();
        if (pAdmin.getId() > 0) {
            pUtilQuery.AgregarNumWhere(" a.Id=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pAdmin.getId());
            }
        }

        if (pAdmin.getId() > 0) {
            pUtilQuery.AgregarNumWhere(" a.IdRol=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pAdmin.getId());
            }
        }
        
        if (pAdmin.getNombre() != null && pAdmin.getNombre().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" a.Nombre LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pAdmin.getNombre() + "%");
            }
        }

        if (pAdmin.getApellido() != null && pAdmin.getApellido().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" a.Apellido LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pAdmin.getApellido() + "%");
            }
        }

        if (pAdmin.getLogin() != null && pAdmin.getLogin().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" a.Login=? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), pAdmin.getLogin());
            }
        }

        if (pAdmin.getEstatus() > 0) {
            pUtilQuery.AgregarNumWhere(" a.EstatusAdministrador=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pAdmin.getEstatus());
            }
        }
    }
    
    public static ArrayList<Administrador> buscar(Administrador pAdmin) throws Exception {
        ArrayList<Administrador> administradores = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pAdmin);
            ComunDB comundb = new ComunDB();
            ComunDB.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0);
            querySelect(pAdmin, utilQuery);
            sql = utilQuery.getSQL();
            sql += agregarOrderBy(pAdmin);
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pAdmin, utilQuery);
                obtenerDatos(ps, administradores);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } 
        catch (SQLException ex) {
            throw ex;
        }
        return administradores;
    }
    
    public static Administrador login(Administrador pAdmin) throws Exception {
        Administrador administrador = new Administrador();
        ArrayList<Administrador> administradores = new ArrayList();
        String password = encriptarMD5(pAdmin.getPassword());
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pAdmin);
            sql += " WHERE a.Login=? AND a.Password=? AND a.EstatusAdministrador=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setString(1, pAdmin.getLogin());
                ps.setString(2, password);
                ps.setByte(3, Administrador.EstatusAdministrador.ACTIVO);
                obtenerDatos(ps, administradores);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } 
        catch (SQLException ex) {
            throw ex;
        }
        if (administradores.size() > 0) {
            administrador = administradores.get(0);
        }
        return administrador;
    }
    
    public static int cambiarPassword(Administrador pAdmin, String pPasswordAnt) throws Exception {
        int result;
        String sql;
        Administrador admistradorAnt = new Administrador();
        admistradorAnt.setLogin(pAdmin.getLogin());
        admistradorAnt.setPassword(pPasswordAnt);
        Administrador AdministradorAut = login(admistradorAnt);

        if (AdministradorAut.getId() > 0 && AdministradorAut.getLogin().equals(pAdmin.getLogin())) {
            try (Connection conn = ComunDB.obtenerConexion();) {
                sql = "UPDATE Administrador SET Password=? WHERE Id=?";
                try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                    ps.setString(1, encriptarMD5(pAdmin.getPassword())); 
                    ps.setInt(2, pAdmin.getId());
                    result = ps.executeUpdate();
                    ps.close();
                } catch (SQLException ex) {
                    throw ex;
                }
                conn.close();
            }
            catch (SQLException ex) {
                throw ex;
            }
        } else {
            result = 0;
            throw new RuntimeException("El password actual es incorrecto");
        }
        return result;
    }
    
    public static ArrayList<Administrador> buscarIncluirRol(Administrador pAdmin) throws Exception {
        ArrayList<Administrador> administradores = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = "SELECT ";
            if (pAdmin.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {
                sql += "TOP " + pAdmin.getTop_aux() + " "; 
            }
            sql += obtenerCampos();
            sql += ",";
            sql += RolDAL.obtenerCampos();
            sql += " FROM Administrador a";
            sql += " JOIN Rol r on (a.IdRol=r.Id)";
            ComunDB comundb = new ComunDB();
            ComunDB.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0);
            querySelect(pAdmin, utilQuery);
            sql = utilQuery.getSQL();
            sql += agregarOrderBy(pAdmin);
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pAdmin, utilQuery);
                obtenerDatosIncluirRol(ps, administradores);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return administradores;
    }

}
