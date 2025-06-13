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

    public static void main(String[] args) {
        var estudiantesDAO = new EstudianteDAO();

        //agregar estudiante
        /*
        var nuevoEstudiante = new Estudiante("Carlos", "lara", "25435435", "calor@gmail.com");
        var agregado = estudiantesDAO.agregarEstudiante(nuevoEstudiante);
        if (agregado){
            System.out.println("Estudiante agregado: " + nuevoEstudiante);
        }else{
            System.out.println("No se agrego el estudiante");
        }*/

        //modificamos un estudiante existente
        var estudianteModificar = new Estudiante(3, "Julio", "Iglesias", "325435343", "aaaa@gmail.com");
        var modificado = estudiantesDAO.modificarEstudiante(estudianteModificar);
        if (modificado){
            System.out.println("Se modifico el Estudiante " + estudianteModificar);
        }else{
            System.out.println("No se logo modificar el estudiante" + estudianteModificar);
        }

        //listar los estudiantes
        System.out.println("Listado de estudiantes");
        List<Estudiante> estudiantes = estudiantesDAO.listar();
        estudiantes.forEach(System.out::println);

        //Buscar por id
        /*
        var estudiante1 = new Estudiante(2);
        System.out.println("Estudiante antes de la busqueda; "+estudiante1);
        var encontrado = estudiantesDAO.buscarEstudiantePorId(estudiante1);
        if (encontrado){
            System.out.println("Estudiante encontrado" + estudiante1);
        }else {
            System.out.println("no se encontro el estudiante" + estudiante1.getIdEstudiante());
        }*/
    }
}
