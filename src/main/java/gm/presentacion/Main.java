package gm.presentacion;

import gm.datos.EstudianteDAO;
import gm.dominio.Estudiante;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        var salir = false;
        var teclado = new Scanner(System.in);
        var estudianteDao = new EstudianteDAO();
        while (!salir){
            try{
                menu();
                salir = ejecutarOpciones(teclado, estudianteDao);
            }catch (Exception e){
                System.out.println("Ocurrio un error inesperado: " + e.getMessage());
            }
        }

    }

    private static void menu() {
        System.out.println("Bienvenido al sistema\n" +
                "Seleccione una opcion: \n" +
                "1. Listado de estudiantes\n" +
                "2. Buscar Estudiante \n" +
                "3. Agregar Estudiante \n" +
                "4. Modificar Estudiante \n" +
                "5. Eliminar estudiante\n" +
                "6. Salir");

    }

    private static boolean ejecutarOpciones(Scanner teclado, EstudianteDAO estudianteDAO){
        var opcion = Integer.parseInt(teclado.nextLine());
        var salir = false;
        switch (opcion){
            case 1: //Listar los estudiantes
                System.out.println("Listado de estudiantes");
                var estudiantes = estudianteDAO.listar();
                estudiantes.forEach(System.out::println);
                break;

            case 2: //Buscar estudiante por ID
                System.out.println("Introduce el ID estudiante");
                var id_estudiante = Integer.parseInt(teclado.nextLine());
                var estudiante  = new Estudiante(id_estudiante);
                var encontrado = estudianteDAO.buscarEstudiantePorId(estudiante);
                if (encontrado){
                    System.out.println("Estudiante encontrado: " + estudiante);
                }else{
                    System.out.println("No se encontro el estudiante");
                }
                break;


            case 3: //Agregar estudiante
                System.out.println("Agregar estudiante");
                System.out.print("Digite el nombre");
                var nombre = teclado.nextLine();
                System.out.print("Digite el apellido");
                var apellido = teclado.nextLine();
                System.out.print("Digite el telefono");
                var telefono = teclado.nextLine();
                System.out.print("Digite el email");
                var email = teclado.nextLine();

                //crear objeto estudiante sin ID
                var estudiantee = new Estudiante(nombre, apellido, telefono, email);
                var agregado = estudianteDAO.agregarEstudiante(estudiantee);
                if (agregado){
                    System.out.println("Se agrego el estudiante: " + estudiantee);
                }else {
                    System.out.println("No se agrego el estudiante");
                }
                break;
            case 4:
                System.out.println("Modificar un estudiante");
                System.out.println("Digite el ID del estudiante a modificar");
                var idTemp = teclado.nextInt();
                teclado.nextLine();
                System.out.print("Digite el nombre: ");
                var nombre1 = teclado.nextLine();
                System.out.print("Digite el apellido");
                var apellido1 = teclado.nextLine();
                System.out.print("Digite el telefono");
                var telefono1 = teclado.nextLine();
                System.out.print("Digite el email");
                var email1 = teclado.nextLine();
                //Crear el objeto estudiante con ID
                var estudianta = new Estudiante (idTemp, nombre1, apellido1, telefono1, email1);
                var modificado = estudianteDAO.modificarEstudiante(estudianta);
                if (modificado){
                    System.out.println("Se modifico el estudiante: " + estudianta);
                }else{
                    System.out.println("No se modifico el estudiante" + estudianta);
                }
                break;
            case 5: //Eliminar estudainte
                System.out.println("Eliminar estudiante");
                System.out.println("Eliminar un estudiante");
                System.out.println("Digite el ID del estudiante a Eliminar");
                var idTemp1 = teclado.nextInt();
                var estudianteEliminar = new Estudiante(idTemp1);
                var eliminado = estudianteDAO.eliminar(estudianteEliminar);
                if (eliminado){
                    System.out.println("Se elimino el estudiante: "+ estudianteEliminar);
                }else {
                    System.out.println("No se pudo eliminar el estudiante" + estudianteEliminar);
                }
                break;
            case 6: //saliendo del programa
                System.out.println("----Saliendo del programa----");
                salir = true;
                break;

            default:
                System.out.println("Opcion no reconocida ");

        }
        return salir;

    }
}
