package sygesin.accesoadatos;

import java.time.LocalDate;
import java.util.*;
import java.sql.*;
import sygesin.entidadesdenegocio.*;

public class EmpleadoDAL {

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
        return "a.Id, a.IdRol, a.Nombre, a.Apellido, a.Login, a.Estatus, a.FechaRegistro";
    }

    private static String obtenerSelect(Empleado pEmpleado) {
        String sql;
        sql = "SELECT ";
        if (pEmpleado.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {
            sql += "TOP " + pEmpleado.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + " FROM Empleado a");
        return sql;
    }

    private static String agregarOrderBy(Empleado pEmpleado) {
        String sql = " ORDER BY a.Id DESC";
        if (pEmpleado.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.MYSQL) {
            sql += " LIMIT " + pEmpleado.getTop_aux() + " ";
        }
        return sql;
    }

    private static boolean existeLogin(Empleado pEmpleado) throws Exception {
        boolean existe = false;
        ArrayList<Empleado> empleados = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pEmpleado);
            sql += " WHERE a.Id<>? AND a.Login=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pEmpleado.getId());
                ps.setString(2, pEmpleado.getLogin());
                obtenerDatos(ps, empleados);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        if (empleados.size() > 0) {
            Empleado empleado = empleados.get(0);
            if (empleado.getId() > 0 && empleado.getLogin().equals(pEmpleado.getLogin())) {
                existe = true;
            }
        }
        return existe;
    }

    public static int crear(Empleado pEmpleado) throws Exception {
        int result;
        String sql;
        boolean existe = existeLogin(pEmpleado);
        if (existe == false) {
            try (Connection conn = ComunDB.obtenerConexion();) {
                sql = "INSERT INTO Empleado(IdRol,Nombre,Apellido,Login,Pass,Estatus,FechaRegistro) VALUES(?,?,?,?,?,?,?)";
                try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                    ps.setInt(1, pEmpleado.getId());
                    ps.setString(2, pEmpleado.getNombre());
                    ps.setString(3, pEmpleado.getApellido());
                    ps.setString(4, pEmpleado.getLogin());
                    ps.setString(5, encriptarMD5(pEmpleado.getPassword()));
                    ps.setByte(6, pEmpleado.getEstatus());
                    ps.setDate(7, java.sql.Date.valueOf(LocalDate.now()));
                    result = ps.executeUpdate();
                    ps.close();
                } catch (SQLException ex) {
                    throw ex;
                }
                conn.close();
            } catch (SQLException ex) {
                throw ex;
            }
        } else {
            result = 0;
            throw new RuntimeException("Login ya existe");
        }
        return result;
    }

    public static int modificar(Empleado pEmpleado) throws Exception {
        int result;
        String sql;
        boolean existe = existeLogin(pEmpleado);
        if (existe == false) {
            try (Connection conn = ComunDB.obtenerConexion();) {
                sql = "UPDATE Empleado SET RolId=?, Nombre=?, Apellido=?, Login=?, Estatus=? WHERE Id=?";
                try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                    ps.setInt(1, pEmpleado.getIdRol());
                    ps.setString(2, pEmpleado.getNombre());
                    ps.setString(3, pEmpleado.getApellido());
                    ps.setString(4, pEmpleado.getLogin());
                    ps.setByte(5, pEmpleado.getEstatus());
                    ps.setInt(6, pEmpleado.getId());
                    result = ps.executeUpdate();
                    ps.close();
                } catch (SQLException ex) {
                    throw ex;
                }
                conn.close();
            } catch (SQLException ex) {
                throw ex;
            }
        } else {
            result = 0;
            throw new RuntimeException("Login ya existe");
        }
        return result;
    }

    public static int eliminar(Empleado pEmpleado) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {
            sql = "DELETE FROM Empleado WHERE Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pEmpleado.getId());
                result = ps.executeUpdate();
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return result;
    }

    private static void obtenerDatos(PreparedStatement pPS, ArrayList<Empleado> pEmpleado) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) {
            while (resultSet.next()) {
                Empleado empleados = new Empleado();
                asignarDatosResultSet(empleados, resultSet, 0);
                pEmpleado.add(empleados);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }

    static int asignarDatosResultSet(Empleado pEmpleado, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        pEmpleado.setId(pResultSet.getInt(pIndex));
        pIndex++;
        pEmpleado.setId(pResultSet.getInt(pIndex));
        pIndex++;
        pEmpleado.setNombre(pResultSet.getString(pIndex));
        pIndex++;
        pEmpleado.setApellido(pResultSet.getString(pIndex));
        pIndex++;
        pEmpleado.setLogin(pResultSet.getString(pIndex));
        pIndex++;
        pEmpleado.setEstatus(pResultSet.getByte(pIndex));
        pIndex++;
        pEmpleado.setFechaRegistro(pResultSet.getDate(pIndex).toLocalDate());
        return pIndex;
    }

    private static void obtenerDatosIncluirRol(PreparedStatement pPS, ArrayList<Empleado> pEmpleado) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) {
            HashMap<Integer, Rol> rolMap = new HashMap();
            while (resultSet.next()) {
                Empleado empleados = new Empleado();
                int index = asignarDatosResultSet(empleados, resultSet, 0);
                if (rolMap.containsKey(empleados.getId()) == false) {
                    Rol rol = new Rol();
                    RolDAL.asignarDatosResultSet(rol, resultSet, index);
                    rolMap.put(rol.getId(), rol);
                    empleados.setRol(rol);
                } else {
                    empleados.setRol(rolMap.get(empleados.getId()));
                }
                pEmpleado.add(empleados);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }

    public static Empleado obtenerPorId(Empleado pEmpleado) throws Exception {
        Empleado empleado = new Empleado();
        ArrayList<Empleado> empleados = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pEmpleado);
            sql += " WHERE a.Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pEmpleado.getId());
                obtenerDatos(ps, empleados);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        if (empleados.size() > 0) {
            empleado = empleados.get(0);
        }
        return empleado;
    }

    public static ArrayList<Empleado> obtenerTodos() throws Exception {
        ArrayList<Empleado> empleados;
        empleados = new ArrayList<>();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(new Empleado());
            sql += agregarOrderBy(new Empleado());
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                obtenerDatos(ps, empleados);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return empleados;
    }

    static void querySelect(Empleado pEmpleado, ComunDB.utilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement();
        if (pEmpleado.getId() > 0) {
            pUtilQuery.AgregarNumWhere(" a.Id=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pEmpleado.getId());
            }
        }

        if (pEmpleado.getId() > 0) {
            pUtilQuery.AgregarNumWhere(" a.IdRol=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pEmpleado.getId());
            }
        }

        if (pEmpleado.getNombre() != null && pEmpleado.getNombre().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" a.Nombre LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pEmpleado.getNombre() + "%");
            }
        }

        if (pEmpleado.getApellido() != null && pEmpleado.getApellido().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" a.Apellido LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pEmpleado.getApellido() + "%");
            }
        }

        if (pEmpleado.getLogin() != null && pEmpleado.getLogin().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" a.Login=? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), pEmpleado.getLogin());
            }
        }

        if (pEmpleado.getEstatus() > 0) {
            pUtilQuery.AgregarNumWhere(" a.Estatus=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pEmpleado.getEstatus());
            }
        }
    }

    public static ArrayList<Empleado> buscar(Empleado pEmpleado) throws Exception {
        ArrayList<Empleado> empleados = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pEmpleado);
            ComunDB comundb = new ComunDB();
            ComunDB.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0);
            querySelect(pEmpleado, utilQuery);
            sql = utilQuery.getSQL();
            sql += agregarOrderBy(pEmpleado);
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pEmpleado, utilQuery);
                obtenerDatos(ps, empleados);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return empleados;
    }

    public static Empleado login(Empleado pEmpleado) throws Exception {
        Empleado empleado = new Empleado();
        ArrayList<Empleado> empleados = new ArrayList();
        String password = encriptarMD5(pEmpleado.getPassword());
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pEmpleado);
            sql += " WHERE a.Login=? AND a.Password=? AND a.Estatus=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setString(1, pEmpleado.getLogin());
                ps.setString(2, password);
                ps.setByte(3, Empleado.EstatusEmpleado.ACTIVO);
                obtenerDatos(ps, empleados);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        if (empleados.size() > 0) {
            empleado = empleados.get(0);
        }
        return empleado;
    }

    public static int cambiarPassword(Empleado pEmpleado, String pPasswordAnt) throws Exception {
        int result;
        String sql;
        Empleado empleadoAnt = new Empleado();
        empleadoAnt.setLogin(pEmpleado.getLogin());
        empleadoAnt.setPassword(pPasswordAnt);
        Empleado EmpleadoAut = login(empleadoAnt);

        if (EmpleadoAut.getId() > 0 && EmpleadoAut.getLogin().equals(pEmpleado.getLogin())) {
            try (Connection conn = ComunDB.obtenerConexion();) {
                sql = "UPDATE Empleado SET Password=? WHERE Id=?";
                try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                    ps.setString(1, encriptarMD5(pEmpleado.getPassword()));
                    ps.setInt(2, pEmpleado.getId());
                    result = ps.executeUpdate();
                    ps.close();
                } catch (SQLException ex) {
                    throw ex;
                }
                conn.close();
            } catch (SQLException ex) {
                throw ex;
            }
        } else {
            result = 0;
            throw new RuntimeException("El password actual es incorrecto");
        }
        return result;
    }

    public static ArrayList<Empleado> buscarIncluirRol(Empleado pEmpleado) throws Exception {
        ArrayList<Empleado> empleados = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = "SELECT ";
            if (pEmpleado.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {
                sql += "TOP " + pEmpleado.getTop_aux() + " ";
            }
            sql += obtenerCampos();
            sql += ",";
            sql += RolDAL.obtenerCampos();
            sql += " FROM Empleado a";
            sql += " JOIN Rol r on (a.IdRol=r.Id)";
            ComunDB comundb = new ComunDB();
            ComunDB.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0);
            querySelect(pEmpleado, utilQuery);
            sql = utilQuery.getSQL();
            sql += agregarOrderBy(pEmpleado);
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pEmpleado, utilQuery);
                obtenerDatosIncluirRol(ps, empleados);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return empleados;
    }
}
