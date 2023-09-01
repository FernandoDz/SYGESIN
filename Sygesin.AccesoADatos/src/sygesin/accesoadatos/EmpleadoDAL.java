package sygesin.accesoadatos;

import java.time.LocalDate;
import java.util.*;
import java.sql.*;
import sygesin.entidadesdenegocio.*;

public class EmpleadoDAL {

    static String obtenerCampos() {
        return "e.Id, e.IdRol, e.Nombre, e.Apellido, e.Cargo, e.Telefono , e.DUI";
    }

    private static String obtenerSelect(Empleado pEmpleado) {
        String sql;
        sql = "SELECT ";
        if (pEmpleado.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {
            sql += "TOP " + pEmpleado.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + "FROM Empleado e");
        return sql;
    }

    private static String agregarOrderBy(Empleado pEmpleado) {
        String sql = " ORDER BY e.Id DESC";
        if (pEmpleado.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.MYSQL) {
            sql += " LIMIT " + pEmpleado.getTop_aux() + " ";
        }
        return sql;
    }

    public static int crear(Empleado pEmpleado) throws Exception {
        int result;
        String sql;

        try (Connection conn = ComunDB.obtenerConexion();) {
            sql = "INSERT INTO Empleado(IdRol,Nombre,Apellido,Cargo,Telefono,DUI) VALUES(?,?,?,?,?,?)";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pEmpleado.getIdRol());
                ps.setString(2, pEmpleado.getNombre());
                ps.setString(3, pEmpleado.getApellido());
                ps.setString(4, pEmpleado.getCargo());
                ps.setString(5, pEmpleado.getTelefono());
                ps.setString(6, pEmpleado.getDUI());
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

    public static int modificar(Empleado pEmpleado) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {
            sql = "UPDATE Empleado SET IdRol=?, Nombre=?, Apellido=?, Cargo=?, Telefono=?, DUI=?  WHERE Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pEmpleado.getIdRol());
                ps.setString(2, pEmpleado.getNombre());
                ps.setString(3, pEmpleado.getApellido());
                ps.setString(4, pEmpleado.getCargo());
                ps.setString(5, pEmpleado.getTelefono());
                ps.setString(6, pEmpleado.getDUI());
                ps.setInt(7, pEmpleado.getId());
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

    static int asignarDatosResultSet(Empleado pEmpleado, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        pEmpleado.setId(pResultSet.getInt(pIndex));
        pIndex++;
        pEmpleado.setIdRol(pResultSet.getInt(pIndex));
        pIndex++;
        pEmpleado.setNombre(pResultSet.getString(pIndex));
        pIndex++;
        pEmpleado.setApellido(pResultSet.getString(pIndex));
        pIndex++;
        pEmpleado.setCargo(pResultSet.getString(pIndex));
        pIndex++;
        pEmpleado.setTelefono(pResultSet.getString(pIndex));
        pIndex++;
        pEmpleado.setDUI(pResultSet.getString(pIndex));
        return pIndex;
    }

    private static void obtenerDatos(PreparedStatement pPS, ArrayList<Empleado> pEmpleado) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) {
            while (resultSet.next()) {
                Empleado empleado = new Empleado();
                asignarDatosResultSet(empleado, resultSet, 0);
                pEmpleado.add(empleado);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }

    private static void obtenerDatosIncluirRol(PreparedStatement pPS, ArrayList<Empleado> pEmpleado) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) {
            HashMap<Integer, Rol> rolMap = new HashMap();
            while (resultSet.next()) {
                Empleado empleado = new Empleado();
                int index = asignarDatosResultSet(empleado, resultSet, 0);
                if (rolMap.containsKey(empleado.getId()) == false) {
                    Rol rol = new Rol();
                    RolDAL.asignarDatosResultSet(rol, resultSet, index);
                    rolMap.put(rol.getId(), rol);
                    empleado.setRol(rol);
                } else {
                    empleado.setRol(rolMap.get(empleado.getIdRol()));
                }
                pEmpleado.add(empleado);
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
            sql += " WHERE e.Id=?";
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
        ArrayList<Empleado> empleados = new ArrayList<>();
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
            pUtilQuery.AgregarNumWhere(" e.Id=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pEmpleado.getId());
            }
        }

        if (pEmpleado.getIdRol() > 0) {
            pUtilQuery.AgregarNumWhere(" e.IdRol=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pEmpleado.getIdRol());
            }
        }

        if (pEmpleado.getNombre() != null && pEmpleado.getNombre().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" e.Nombre LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pEmpleado.getNombre() + "%");
            }
        }

        if (pEmpleado.getApellido() != null && pEmpleado.getApellido().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" e.Apellido LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pEmpleado.getApellido() + "%");
            }
        }

        if (pEmpleado.getCargo() != null && pEmpleado.getCargo().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" e.Cargo=? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), pEmpleado.getCargo());
            }
        }

        if (pEmpleado.getTelefono() != null && pEmpleado.getTelefono().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" e.Telefono=? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), pEmpleado.getTelefono());
            }
        }
        if (pEmpleado.getDUI() != null && pEmpleado.getTelefono().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" e.DUI=? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), pEmpleado.getTelefono());
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
            sql += " FROM Empleado e";
            sql += " JOIN  Rol r on (e.IdRol=r.Id)";
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
