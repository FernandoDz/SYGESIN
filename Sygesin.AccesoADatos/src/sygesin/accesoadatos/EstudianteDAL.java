package sygesin.accesoadatos;
import java.time.LocalDate;
import java.util.*;
import java.sql.*;

import sygesin.entidadesdenegocio.*;


public class EstudianteDAL {
    
    static String obtenerCampos() {
        return "e.Id, e.IdRol,   e.Nombre, e.Apellido, e.direccion, e.departamento, e.telefono, e.correo, e.encargado, e.seccion, e.fechanacimiento ";
    }
    
    private static String obtenerSelect(Estudiante pEstudiante) {
        String sql;
        sql = "SELECT ";
        if (pEstudiante.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {
             sql += "TOP " + pEstudiante.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + " FROM Estudiante e");
        return sql;
    }
    
    private static String agregarOrderBy(Estudiante pEstudiante) {
        String sql = " ORDER BY e.Id DESC";
        if (pEstudiante.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.MYSQL) {
            sql += " LIMIT " + pEstudiante.getTop_aux() + " ";
        }
        return sql;
    }
    
   
    
    
    
     public static int crear(Estudiante pEstudiante) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { 
            sql = "INSERT INTO Estudiante(IdRol,Nombre,Apellido,Direccion,Departamento,Telefono,Correo, Encargado, Seccion,Fechanacimiento ) VALUES(?,?,?,?,?,?,?,?,?,?,?))";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                   
                    ps.setInt(1, pEstudiante.getIdrol());
                    ps.setString(2, pEstudiante.getNombre());
                    ps.setString(3, pEstudiante.getApellido());
                    ps.setString(4, pEstudiante.getDireccion());
                    ps.setString(5, pEstudiante.getDepartamento());
                    ps.setString(6, pEstudiante.getTelefono());
                    ps.setString(7, pEstudiante.getCorreo());
                    ps.setString(8, pEstudiante.getEncargado());
                    ps.setString(9, pEstudiante.getSeccion());
                    ps.setString(10, pEstudiante.getFechanacimiento());
                   
                    result = ps.executeUpdate();
                    ps.close();
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
    public static int modificar(Estudiante pEstudiante) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {
            sql = "UPDATE Rol SET Nombre=?IdRol=?,Apellido=?,Direccion=?,Departamento=?,Telefono=?,Correo=?, Encargado=?, Seccion=?,Fechanacimiento=?  WHERE Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
               ps.setInt(1, pEstudiante.getIdrol());
                    ps.setString(2, pEstudiante.getNombre());
                    ps.setString(3, pEstudiante.getApellido());
                    ps.setString(4, pEstudiante.getDireccion());
                    ps.setString(5, pEstudiante.getDepartamento());
                    ps.setString(6, pEstudiante.getTelefono());
                    ps.setString(7, pEstudiante.getCorreo());
                    ps.setString(8, pEstudiante.getEncargado());
                    ps.setString(9, pEstudiante.getSeccion());
                    ps.setString(10, pEstudiante.getFechanacimiento());
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
    
    
    public static int eliminar(Estudiante pEstudiante) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { 
            sql = "DELETE FROM Estudiante WHERE Id=?"; 
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pEstudiante.getId());
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
    
    static int asignarDatosResultSet(Estudiante pEstudiante, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        pEstudiante.setId(pResultSet.getInt(pIndex)); 
        pIndex++;
        pEstudiante.setIdrol(pResultSet.getInt(pIndex)); 
        pIndex++;
        pEstudiante.setNombre(pResultSet.getString(pIndex)); 
        pIndex++;
        pEstudiante.setApellido(pResultSet.getString(pIndex)); 
        pIndex++;
        pEstudiante.setDireccion(pResultSet.getString(pIndex)); 
        pIndex++;
        pEstudiante.setDepartamento(pResultSet.getString(pIndex)); 
        pIndex++;
        pEstudiante.setTelefono(pResultSet.getString(pIndex)); 
        pIndex++;
        pEstudiante.setCorreo(pResultSet.getString(pIndex)); 
        pIndex++;
        pEstudiante.setEncargado(pResultSet.getString(pIndex)); 
        pIndex++;
        pEstudiante.setSeccion(pResultSet.getString(pIndex)); 
        pIndex++;
        pEstudiante.setFechanacimiento(pResultSet.getString(pIndex)); 
        pIndex++;
        return pIndex;
    }
    
    private static void obtenerDatos(PreparedStatement pPS, ArrayList<Estudiante> pEstudiante) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) { 
            while (resultSet.next()) {
                Estudiante esttudiante = new Estudiante();
                asignarDatosResultSet(esttudiante, resultSet, 0);
                pEstudiante.add(esttudiante);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    private static void obtenerDatosIncluirRol(PreparedStatement pPS, ArrayList<Estudiante> pEstudiante) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) {
            HashMap<Integer, Rol> rolMap = new HashMap(); 
            while (resultSet.next()) {
                Estudiante estudiante = new Estudiante();
                int index = asignarDatosResultSet(estudiante, resultSet, 0);
                if (rolMap.containsKey(estudiante.getId()) == false) {
                    Rol rol = new Rol();
                    RolDAL.asignarDatosResultSet(rol, resultSet, index);
                    rolMap.put(rol.getId(), rol); 
                    estudiante.setRol(rol); 
                } else {
                    estudiante.setRol(rolMap.get(estudiante.getId())); 
                }
                pEstudiante.add(estudiante); 
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex; 
        }
    }
    
    public static Estudiante obtenerPorId(Estudiante pEstudiante) throws Exception {
        Estudiante estudiante = new Estudiante();
        ArrayList<Estudiante> estudiantes = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pEstudiante);
            sql += " WHERE e.Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pEstudiante.getId());
                obtenerDatos(ps, estudiantes);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        if (estudiantes.size() > 0) {
            estudiante = estudiantes.get(0);
        }
        return estudiante;
    }
    
    public static ArrayList<Estudiante> obtenerTodos() throws Exception {
        ArrayList<Estudiante> estudiante;
        estudiante = new ArrayList<>();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(new Estudiante()); 
            sql += agregarOrderBy(new Estudiante());
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                obtenerDatos(ps, estudiante);
                ps.close();
            } catch (SQLException ex) {
                throw ex; 
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        return estudiante;
    }
    
    static void querySelect(Estudiante pEstudiante, ComunDB.utilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement();
        if (pEstudiante.getId() > 0) {
            pUtilQuery.AgregarNumWhere(" e.Id=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pEstudiante.getId());
            }
        }

        if (pEstudiante.getId() > 0) {
            pUtilQuery.AgregarNumWhere(" e.IdRol=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pEstudiante.getId());
            }
        }
        
        
        if (pEstudiante.getNombre() != null && pEstudiante.getNombre().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" e.Nombre LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pEstudiante.getNombre() + "%");
            }
        }

        if (pEstudiante.getApellido() != null && pEstudiante.getApellido().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" e.Apellido LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pEstudiante.getApellido() + "%");
            }
        }
        
         if (pEstudiante.getDireccion() != null && pEstudiante.getDireccion().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" e.Direccion LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pEstudiante.getDireccion() + "%");
            }
        }
         
        if (pEstudiante.getDepartamento()!= null && pEstudiante.getDireccion().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" e.Departamento LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pEstudiante.getDepartamento() + "%");
            }
        }
        
        if (pEstudiante.getTelefono() != null && pEstudiante.getTelefono().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" e.Telefono LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pEstudiante.getTelefono() + "%");
            }
        }
        
        if (pEstudiante.getCorreo() != null && pEstudiante.getCorreo().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" e.Correo LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pEstudiante.getCorreo() + "%");
            }
        }
        
         if (pEstudiante.getEncargado() != null && pEstudiante.getEncargado().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" e.Encargado LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pEstudiante.getEncargado() + "%");
            }
        }
         
         if (pEstudiante.getSeccion() != null && pEstudiante.getSeccion().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" e.Seccion LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pEstudiante.getSeccion() + "%");
            }
        }
         
         if (pEstudiante.getFechanacimiento() != null && pEstudiante.getFechanacimiento().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" e.Fechanacimiento LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pEstudiante.getFechanacimiento() + "%");
            }
        }

        
    }
    
    public static ArrayList<Estudiante> buscar(Estudiante pEstudiante) throws Exception {
        ArrayList<Estudiante> estudiantes = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pEstudiante);
            ComunDB comundb = new ComunDB();
            ComunDB.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0);
            querySelect(pEstudiante, utilQuery);
            sql = utilQuery.getSQL();
            sql += agregarOrderBy(pEstudiante);
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pEstudiante, utilQuery);
                obtenerDatos(ps, estudiantes);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } 
        catch (SQLException ex) {
            throw ex;
        }
        return estudiantes;
    }
    
    
    
   
    public static ArrayList<Estudiante> buscarIncluirRol(Estudiante pEstudiante) throws Exception {
        ArrayList<Estudiante> estudiantes = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = "SELECT ";
            if (pEstudiante.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {
                sql += "TOP " + pEstudiante.getTop_aux() + " "; 
            }
            sql += obtenerCampos();
            sql += ",";
            sql += RolDAL.obtenerCampos();
            sql += " FROM Estudiante e";
            sql += " JOIN Rol r on (e.IdRol=r.Id)";
            ComunDB comundb = new ComunDB();
            ComunDB.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0);
            querySelect(pEstudiante, utilQuery);
            sql = utilQuery.getSQL();
            sql += agregarOrderBy(pEstudiante);
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pEstudiante, utilQuery);
                obtenerDatosIncluirRol(ps, estudiantes);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return estudiantes;
    }
        
    }
