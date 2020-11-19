package infraestructuraobligatorio;

import infraestructuraobligatorio.dominio.Proceso;
import infraestructuraobligatorio.dominio.Recurso;
import infraestructuraobligatorio.dominio.Tarea;
import infraestructuraobligatorio.dominio.Usuario;
import infraestructuraobligatorio.dominio.enums.Colores;
import infraestructuraobligatorio.dominio.enums.EstadoProceso;
import infraestructuraobligatorio.dominio.enums.Permiso;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Sistema {

    private final Double quantum = 1D;

    public void procesar(List<Proceso> procesos) throws Exception {
        validarListaVacia(procesos);
        List<Proceso> procesosClonada = new ArrayList<>(procesos);
        
        Double maxIteraciones = maxIteracionesPrevencionDeadLocks(procesos);
        
        Double contador = 1D;
        while (!procesosClonada.isEmpty() && contador <= maxIteraciones) {
            for (Proceso proceso : procesosClonada) {
                boolean tienePermisos = validarPermiso(procesos, proceso);
                procesosClonada = new ArrayList<>(procesos);
                
                Thread.sleep(1000L); 
                
                if(tienePermisos){
                    ejecutar(proceso);
                    borrarElementoLista(procesos, proceso);
                }
            }
            procesosClonada = new ArrayList<>(procesos);
            contador++;
        }
    }

    private void ejecutar(Proceso proceso) {
        if(proceso.getRecurso().isEnUso() 
                && !EstadoProceso.BLOQUEADO.equals(proceso.getEstado())){
            imprimirConColor("El proceso: " + proceso.getId() + " espera por el recurso: " + proceso.getRecurso().getNombre(), Colores.YELLOW);
            return;
        }
        
        if(EstadoProceso.LISTO.equals(proceso.getEstado())){
            actualizarEstado(proceso, EstadoProceso.EN_EJECUCION);
        }
        
        proceso.setDuracion(proceso.getDuracion() - quantum);
        proceso.getRecurso().setEnUso(true);
        
        borrarTarea(proceso);
        
        actualizarEstado(proceso, EstadoProceso.BLOQUEADO);
    }

    private void validarListaVacia(List<Proceso> procesos) throws Exception{
        if(procesos == null || procesos.isEmpty()){
            throw new Exception("La lista de procesos no puede ser vacia");
        }
    }
    
    private void actualizarEstado(Proceso proceso, EstadoProceso estado) {
        if (!proceso.getEstado().equals(estado)) {
            proceso.setEstado(estado);
            imprimirConColor("El proceso: " + proceso.getId() + " se cambio al estado: " + proceso.getEstado(), Colores.CYAN);
        }
    }

    private void borrarElementoLista(List<Proceso> procesos, Proceso proceso) {
        if (proceso.getDuracion() <= 0D) {
            proceso.getRecurso().setEnUso(false);
            procesos.remove(proceso);
            imprimirConColor("El proceso: " + proceso.getId() + " ha finalizado y libero el recurso: " + proceso.getRecurso().getNombre(), Colores.GREEN);
        }
    }

    private void borrarTarea(Proceso proceso) {
        LinkedList<Tarea> tareas = proceso.getTareas();
        if (!tareas.isEmpty()) {
            Tarea tarea = tareas.element();
            System.out.println("Se ejecuto la tarea: " + tarea.getNombre() + " del proceso " + proceso.getId());
            tareas.pop();
        }
    }
    
    public List<Proceso> precargarProcesos(List<Proceso> procesos, List<Usuario> usuarios, List<Recurso> recursos){
        Recurso r1 = new Recurso(1, "Impresora");
        Recurso r2 = new Recurso(2, "Mouse");
        Recurso r3 = new Recurso(2, "Disco Duro");
        
        recursos.add(r1);
        recursos.add(r2);
        recursos.add(r3);
        
        LinkedList<Tarea> t1p1 = new LinkedList<>();
        Tarea t1 = new Tarea("Leer");
        Tarea t2 = new Tarea("Escritura");
       
        t1p1.push(t1);
        t1p1.push(t2);
        
        LinkedList<Tarea> t1p2 = new LinkedList<>();
        Tarea t3 = new Tarea("Dibujar");
        Tarea t4 = new Tarea("Pintar");
       
        t1p2.push(t3);
        t1p2.push(t4);
        
        LinkedList<Tarea> t1p3 = new LinkedList<>();
        Tarea t5 = new Tarea("Destruir");
        Tarea t6 = new Tarea("Picar");
        Tarea t7 = new Tarea("Construir");
       
        t1p3.push(t5);
        t1p3.push(t6);
        t1p3.push(t7);

        Usuario u1 = new Usuario(1, "Nico Arsel", Permiso.INVITADO);
        Usuario u2 = new Usuario(2, "Facundo Rodriguez", Permiso.ADMIN);
        Usuario u3 = new Usuario(3, "Adrian Oses", Permiso.ADMIN);
        
        usuarios.add(u1);
        usuarios.add(u2);
        usuarios.add(u3);
        
        Proceso p1 = new Proceso(1, "Zoom", EstadoProceso.LISTO, 2D,  t1p1,r1,u1,Permiso.ADMIN);
        
        Proceso p2 = new Proceso(2, "Word", EstadoProceso.LISTO, 2D, t1p2,r2,u2,Permiso.ADMIN);
        Proceso p3 = new Proceso(3, "PowerPoint", EstadoProceso.LISTO, 3D, t1p3,r3,u3,Permiso.USUARIO);

        //List<Proceso> procesos = new ArrayList<>();
        procesos.add(p1);
        procesos.add(p2);
        procesos.add(p3);
        
        //ordenarListaPorPrioridad(procesos);
        
        return procesos;
    }
    
    private void imprimirConColor(String mensaje,Colores color){
        System.out.println(color.getCodigoColor()+mensaje+Colores.ANSI_RESET.getCodigoColor());
    }
    
    private boolean validarPermiso(List<Proceso> procesos, Proceso proceso){
        boolean tienePermisos = true;
        int igual = proceso.getPermiso().compareTo(proceso.getUsuario().getPermiso());

        if(igual < 0){
            imprimirConColor("El proceso "+proceso.getId()+" no tiene permisos",Colores.RED);
            procesos.remove(proceso);
            tienePermisos = false;
        }
        return tienePermisos;
    }

    public Usuario crearUsuario(){
        imprimirConColor("     USUARIO     ", Colores.PURPLE);
        Usuario u = new Usuario();
        Scanner in = new Scanner(System.in);
        System.out.println("Elija un nombre");
        u.setNombre(in.nextLine());
        System.out.println("Asigne el permiso, puede ser:");
        imprimirConColor("1- ADMIN", Colores.BLUE);
        imprimirConColor("2- USUARIO", Colores.BLUE);
        imprimirConColor("3- INVITADO", Colores.BLUE);
        boolean permisoValido = false;
        while(!permisoValido){
            try{
                Integer numeroIn = Integer.valueOf(in.nextLine());
                validarRango(numeroIn, 1, 3);
                switch(numeroIn){
                    case 1:
                        u.setPermiso(Permiso.ADMIN);
                        permisoValido = true;
                        break;
                    case 2:
                        u.setPermiso(Permiso.USUARIO);
                        permisoValido = true;
                        break;
                    case 3:
                        u.setPermiso(Permiso.INVITADO);
                        permisoValido = true;
                        break;
                    default:
                        System.out.println("Debe ser una opcion de estas:");
                        imprimirConColor("1- ADMIN", Colores.BLUE);
                        imprimirConColor("2- USUARIO", Colores.BLUE);
                        imprimirConColor("3- INVITADO", Colores.BLUE);
                        break;
                }
            }catch(NumberFormatException nE){
                System.err.println("Debe ingresar un numero");
            }catch(Exception e){
                System.err.println(e.getLocalizedMessage());
            }
        }
        imprimirConColor("Se creo con exito el usuario: "+u.getNombre(), Colores.GREEN);
        return u;
    }
    
    public Recurso crearRecurso(){
        imprimirConColor("    RECURSO     ", Colores.PURPLE);
        Recurso r = new Recurso();
        Scanner in = new Scanner(System.in);
        
        System.out.println("Elija un nombre");
        r.setNombre(in.nextLine());
        
        imprimirConColor("Se creo con exito el recurso: "+r.getNombre(), Colores.GREEN);
        return r;
    }
    
    public Proceso crearProceso(List<Usuario> usuarios, List<Recurso> recursos) throws Exception{
        imprimirConColor("    PROCESO     ", Colores.PURPLE);
        Proceso p = new Proceso();
        
        Scanner in = new Scanner(System.in);
        
        System.out.println("Elija un nombre");
        p.setNombre(in.nextLine());
        
        System.out.println("Asigne el permiso, puede ser:");
        imprimirConColor("1- ADMIN", Colores.BLUE);
        imprimirConColor("2- USUARIO", Colores.BLUE);
        imprimirConColor("3- INVITADO", Colores.BLUE);
        boolean permisoValido = false;
        while(!permisoValido){
            try{
                Integer numeroIn = Integer.valueOf(in.nextLine());
                validarRango(numeroIn, 1, 3);
                switch(numeroIn){
                    case 1:
                        p.setPermiso(Permiso.ADMIN);
                        permisoValido = true;
                        break;
                    case 2:
                        p.setPermiso(Permiso.USUARIO);
                        permisoValido = true;
                        break;
                    case 3:
                        p.setPermiso(Permiso.INVITADO);
                        permisoValido = true;
                        break;
                    default:
                        System.out.println("Debe ser una opcion de estas:");
                        imprimirConColor("1- ADMIN", Colores.BLUE);
                        imprimirConColor("2- USUARIO", Colores.BLUE);
                        imprimirConColor("3- INVITADO", Colores.BLUE);
                        break;
                }
            }catch(NumberFormatException nE){
                System.err.println("Debe ingresar un numero");
            }catch(Exception e){
                System.err.println(e.getLocalizedMessage());
            }
        }
        validoExistenciaUsuariosConPermisoProceso(p, usuarios);
        
        LinkedList<Tarea> tareas = agregoTareaProceso();
        p.setTareas(tareas);
        p.setDuracion(Double.valueOf(tareas.size()));
        
        
        List<Usuario> usuariosConPermiso = new ArrayList<>();
        for(Usuario usuario : usuarios){
            //if(p.getPermiso().equals(usuario.getPermiso())){
            int igual = p.getPermiso().compareTo(usuario.getPermiso());
            if(igual >= 0){
                usuariosConPermiso.add(usuario);
            }
        }
        p.setUsuario(asignoUsuario(usuariosConPermiso));
        p.setRecurso(asignoRecurso(recursos));
        
        imprimirConColor("Se creo con exito el recurso: "+p.getNombre(), Colores.GREEN);
        return p;
    }
    
    private Recurso asignoRecurso(List<Recurso> recursos){
        Recurso r = null;
        Scanner in = new Scanner(System.in);
        boolean sigo = true;
        while(sigo){
            try{
                System.out.println("Elija un usuario de la lista:");
                for(int i = 0; i < recursos.size(); i++){
                    System.out.println((i+1) + " - " + recursos.get(i).getNombre());
                }
                Integer numeroIn = Integer.valueOf(in.nextLine());
                validarRango(numeroIn, 1, recursos.size());
                r = recursos.get(numeroIn -1);
                sigo = false;
            }catch(NumberFormatException nE){
                System.err.println("Debe ingresar un numero");
            }catch(Exception e){
                System.err.println(e.getLocalizedMessage());
            }
        }
        imprimirConColor("Se asigno correctamente el recurso: "+r.getNombre(), Colores.GREEN);
        return r;
    }
    
    private Usuario asignoUsuario(List<Usuario> usuarios){
        Usuario u = null;
        Scanner in = new Scanner(System.in);
        boolean sigo = true;
        while(sigo){
            try{
                System.out.println("Elija un usuario de la lista:");
                for(int i = 0; i < usuarios.size(); i++){
                    System.out.println((i+1) + " - " + usuarios.get(i).getNombre());
                }
                Integer numeroIn = Integer.valueOf(in.nextLine());
                validarRango(numeroIn, 1, usuarios.size());
                u = usuarios.get(numeroIn -1);
                sigo = false;
            }catch(NumberFormatException nE){
                System.err.println("Debe ingresar un numero");
            }catch(Exception e){
                System.err.println(e.getLocalizedMessage());
            }
        }
        imprimirConColor("Se asigno correctamente el usuario: "+u.getNombre(), Colores.GREEN);
        return u;
    }
    
    private LinkedList<Tarea> agregoTareaProceso(){
        LinkedList<Tarea> tareas = new LinkedList<>();
        
        Scanner in = new Scanner(System.in);
        System.out.println("");
        System.out.println("Escriba el nombre de la primera tarea:");
        Tarea t1 = new Tarea(in.nextLine());
        tareas.push(t1);
        imprimirConColor("Se agrego correctamente la tarea "+t1.getNombre(), Colores.GREEN);
        boolean sigoAgregandoTareas = true;
        while(sigoAgregandoTareas){
            try{
                System.out.println("Desea agregar mas tareas?");
                imprimirConColor("1- SI", Colores.BLUE);
                imprimirConColor("2- NO", Colores.BLUE);
                System.out.println("");
                System.out.println("Elija una opcion: ");
                Integer numeroIn = Integer.valueOf(in.nextLine());
                validarRango(numeroIn, 1, 2);
                switch(numeroIn){
                    case 1:
                        System.out.println("Escriba una nueva tarea: ");
                        Tarea t = new Tarea(in.nextLine());
                        tareas.push(t);
                        imprimirConColor("Se agrego correctamente la tarea "+t1.getNombre(), Colores.GREEN);
                        break;
                    case 2:
                        sigoAgregandoTareas = false;
                        break;
                    default:
                        imprimirConColor("Opcion no valida", Colores.YELLOW);
                        break;
                }
            }catch(NumberFormatException nE){
                System.err.println("Debe ingresar un numero");
            }catch(Exception e){
                System.err.println(e.getLocalizedMessage());
            }
        }
        return tareas;
    }
    
    private void validarRango(Integer numero, Integer rangoMin, Integer rangoMax) throws Exception{
        if(numero < rangoMin || numero > rangoMax){
            throw new Exception("El numero ingresado debe estar entre " + rangoMin + " y "+ rangoMax);
        }
    }
    
    private void validoExistenciaUsuariosConPermisoProceso(Proceso proceso, List<Usuario> usuarios) throws Exception{
        boolean existeUsuarioConPermiso=false;
        for(Usuario usuario : usuarios){
            //if(proceso.getPermiso().equals(usuario.getPermiso())){
            int igual = proceso.getPermiso().compareTo(usuario.getPermiso());
            if(igual >= 0){
                existeUsuarioConPermiso= true;
                break;
            }
        }
        
        if(!existeUsuarioConPermiso){
            throw new Exception("No existe ningun usuario con el permiso: " + proceso.getPermiso() + " asignado al proceso, Vuelva a intentarlo");
        }
    }
    
    private Double maxIteracionesPrevencionDeadLocks(List<Proceso> procesos){
        Double maxIteraciones = 1D;
        for(Proceso proceso : procesos){
            maxIteraciones = maxIteraciones * proceso.getDuracion();
        }
        maxIteraciones = (maxIteraciones * maxIteraciones);
        return maxIteraciones;
    }
    
}
