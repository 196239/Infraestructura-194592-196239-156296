
package infraestructuraobligatorio;

import infraestructuraobligatorio.dominio.Proceso;
import infraestructuraobligatorio.dominio.Recurso;
import infraestructuraobligatorio.dominio.Usuario;
import infraestructuraobligatorio.dominio.enums.Colores;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ComunidacionUsuario {
    
    public void iniciar(){
        imprimirConColor("         BIENVENIDO         ",Colores.PURPLE);
        Sistema s = new Sistema();
        imprimirInicio();
        Scanner in = new Scanner(System.in);
        List<Proceso> procesos = new ArrayList<>();
        List<Usuario> usuarios = new ArrayList<>();
        List<Recurso> recursos = new ArrayList<>();
        
        while(in.hasNextLine()){
            try{
                Integer numeroIn = Integer.valueOf(in.nextLine());
                validarRango(numeroIn, 1, 7);
                switch(numeroIn){
                    case 1:
                        usuarios.add(s.crearUsuario());
                        imprimirInicio();
                        break;
                    case 2:
                        recursos.add(s.crearRecurso());
                        imprimirInicio();
                        break;
                    case 3:
                        validarListaUsuariosYRecursos(usuarios, recursos);
                        procesos.add(s.crearProceso(usuarios,recursos));
                        imprimirInicio();
                        break;
                    case 4:
                        procesos = s.precargarProcesos(procesos,usuarios,recursos);
                        imprimirInicio();
                        break;
                    case 5:
                        validarProceso(procesos, usuarios, recursos);
                        s.procesar(procesos);
                        imprimirInicio();
                        break;
                    case 6:
                        visualizarDatos(procesos,usuarios,recursos);
                        imprimirInicio();
                        break;
                    case 7:
                        imprimirConColor("Gracias por visitar nuestro sistema", Colores.YELLOW);
                        System.exit(0);
                        break;
                    default:
                        imprimirConColor("Opcion no valida", Colores.YELLOW);
                }
            }catch(NumberFormatException nE){
                System.err.println("Debe ingresar un numero");
                imprimirInicio();
            }catch(Exception e){
                System.err.println(e.getLocalizedMessage());
                imprimirInicio();
            }
        }
    }
    
    private void imprimirInicio(){
        System.out.println("-------------------------");
        System.out.println("-   1) Crear usuarios   -");
        System.out.println("-   2) Crear recursos   -");
        System.out.println("-   3) Crear procesos   -");
        System.out.println("-   4) Precargar lista  -");
        System.out.println("-   5) Ejecutar         -");
        System.out.println("-   6) Visualizar Datos -");
        System.out.println("-   7) Salir            -");
        System.out.println("-------------------------");
        System.out.println("Elija una opcion:");
    }
    
    private void imprimirConColor(String mensaje,Colores color){
        System.out.println(color.getCodigoColor()+mensaje+Colores.ANSI_RESET.getCodigoColor());
    }
    
    
    private void validarRango(Integer numero, Integer rangoMin, Integer rangoMax) throws Exception{
        if(numero < rangoMin || numero > rangoMax){
            throw new Exception("El numero ingresado debe estar entre " + rangoMin + " y "+ rangoMax);
        }
    }
    
    private void validarListaUsuariosYRecursos(List<Usuario> usuarios, List<Recurso> recursos) throws Exception{
        if(usuarios == null || usuarios.isEmpty()){
            throw new Exception("Para ingresar a esta opcion, la lista de usuarios no puede ser vacia");
        }
        if(recursos == null || recursos.isEmpty()){
            throw new Exception("Para ingresar a esta opcion, la lista de recursos no puede ser vacia");
        }
    }
    
    private void validarProceso(List<Proceso> procesos, List<Usuario> usuario, List<Recurso> recursos) throws Exception{
        if(procesos == null || procesos.isEmpty()){
            throw new Exception("Para ingresar a esta opcion, la lista de procesos no puede ser vacia");
        }
        validarListaUsuariosYRecursos(usuario, recursos);
    }
    
    private void visualizarDatos(List<Proceso> procesos, List<Usuario> usuarios, List<Recurso> recursos) throws Exception{
        imprimirConColor("    USUARIOS     ", Colores.PURPLE);
        if(usuarios == null || usuarios.isEmpty()){
            imprimirConColor("No hay datos", Colores.RED);
        }else{
            for(int i=0 ; i< usuarios.size(); i++){
                System.out.println((i+1) + ") "+ usuarios.get(i).getNombre()+ "-" + usuarios.get(i).getPermiso());
            }
        }
        imprimirConColor("    RECURSOS     ", Colores.PURPLE);
        if(recursos == null || recursos.isEmpty()){
            imprimirConColor("No hay datos", Colores.RED);
        }else{
            for(int i=0 ; i< recursos.size(); i++){
                System.out.println((i+1) + ") "+ recursos.get(i).getNombre());
            }
        }
        imprimirConColor("    PROCESOS     ", Colores.PURPLE);
        if(procesos == null || procesos.isEmpty()){
            imprimirConColor("No hay datos", Colores.RED);
        }else{
            for(int i=0 ; i< procesos.size(); i++){
                System.out.println((i+1) + ") "+ procesos.get(i).getNombre() + "-" + procesos.get(i).getPermiso());
            }
        }
    }
}
