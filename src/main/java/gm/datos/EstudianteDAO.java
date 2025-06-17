package gm.datos;

import gm.dominio.Estudiante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static gm.conexion.Conexion.getconexion;

public class EstudianteDAO {
    public List<Estudiante> listar(){
        List<Estudiante> estudiantes = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;

        Connection con = getconexion();
        String sql = "SELECT * FROM estudiante ORDER BY id_estudiante";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                var estudiante = new Estudiante(2, "Julio", "Iglesias", "325435343", "aaaa@gmail.com");
                estudiante.setIdEstudiante(rs.getInt("id_estudiante"));
                estudiante.setNombre(rs.getString("nombre"));
                estudiante.setApellido(rs.getString("apellido"));
                estudiante.setTelefono(rs.getString("telefono"));
                estudiante.setEmail(rs.getString("email"));

                estudiantes.add(estudiante);
            }
        }catch (Exception e){
            System.out.println("Ocurrio un error " + e.getMessage());
        } finally {
            try {
                con.close();

            }catch (Exception e){
                System.out.println("Ocurrio un error al cerrar la aplicacion " + e.getMessage());
            }
        }
        return estudiantes;
    }

    public boolean buscarEstudiantePorId(Estudiante estudiante){
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getconexion();
        String sql = "SELECT * FROM estudiante WHERE id_estudiante = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1,estudiante.getIdEstudiante());
            rs = ps.executeQuery();


            if (rs.next()){
                estudiante.setNombre(rs.getString("nombre"));
                estudiante.setApellido(rs.getString("Apellido"));
                estudiante.setTelefono(rs.getString("Telefono"));
                estudiante.setEmail(rs.getString("email"));

                return true;
            }
        } catch (Exception e){
            System.out.println("ocurrio un error al buscar el estudiante" + e.getMessage());
        }
        finally {
            try {
                con.close();
            }catch (Exception e){
                System.out.println("Ocurrio un error " + e.getMessage());
            }
        }
        return false;
    }

    public boolean agregarEstudiante(Estudiante estudiante){
        PreparedStatement ps;
        Connection con = getconexion();
        String sql = "INSERT INTO estudiante(nombre, apellido, telefono, email) " +
                "VALUES (?, ?, ?, ?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, estudiante.getNombre());
            ps.setString(2, estudiante.getApellido());
            ps.setString(3, estudiante.getTelefono());
            ps.setString(4, estudiante.getEmail());

            ps.execute();

            return true;

        } catch (SQLException e) {
            System.out.println("Error " + e.getMessage());
        }
        finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Error " + e.getMessage());
            }
        }
        return false;

    }

    public boolean modificarEstudiante(Estudiante estudiante){
        PreparedStatement ps;
        Connection con = getconexion();
        String sql = "UPDATE estudiante SET nombre=?, apellido=?, telefono=? ,email=? WHERE id_estudiante = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, estudiante.getNombre());
            ps.setString(2, estudiante.getApellido());
            ps.setString(3, estudiante.getTelefono());
            ps.setString(4, estudiante.getEmail());
            ps.setInt(5, estudiante.getIdEstudiante());

            ps.execute();
            return true;
        }catch (Exception e){
            System.out.println("Error al tratar de modificar"+ e.getMessage());
        }
    finally {
            try{
                con.close();
            }catch (Exception e){
                System.out.println("Error al modificar" + e.getMessage());
            }
        }
    return false;
    }
    public boolean eliminar (Estudiante estudiante){
        PreparedStatement ps;
        Connection con = getconexion();
        String sql = "DELETE FROM estudiante WHERE id_estudiante = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, estudiante.getIdEstudiante());
            ps.execute();
            return true;
        }catch(Exception e){
            System.out.println("Error al eliminar el estudiante" + e.getMessage());
        }
        finally {
            try{
                con.close();
            }catch (Exception e){
                System.out.println("Error al cerrar conexion" + e.getMessage());
            }

        }
        return false;
    }
}
