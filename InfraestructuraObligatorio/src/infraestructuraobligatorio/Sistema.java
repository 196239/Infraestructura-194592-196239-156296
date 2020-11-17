package infraestructuraobligatorio;

import infraestructuraobligatorio.dominio.Proceso;
import infraestructuraobligatorio.dominio.Tarea;
import infraestructuraobligatorio.dominio.enums.EstadoProceso;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Sistema {

    private Double quantum = 1D;

    public void procesar() {
        List<Proceso> procesos = construirProcesos();
        List<Proceso> procesosClonada = new ArrayList<Proceso>(procesos);
        
        while (!procesosClonada.isEmpty()) {
            for (Proceso proceso : procesosClonada) {
                ejecutar(proceso);
                borrarElementoLista(procesos, proceso);
            }
            procesosClonada = new ArrayList<Proceso>(procesos);
        }
    }

    private void ejecutar(Proceso proceso) {
        actualizarEstado(proceso, EstadoProceso.EN_EJECUCION);

        proceso.setDuracion(proceso.getDuracion() - quantum);

        borrarTarea(proceso);
    }

    private void actualizarEstado(Proceso proceso, EstadoProceso estado) {
        if (!proceso.getEstado().equals(estado)) {
            proceso.setEstado(estado);
            System.out.println("El proceso: " + proceso.getId() + " se cambio al estado: " + proceso.getEstado());
        }
    }

    private void borrarElementoLista(List<Proceso> procesos, Proceso proceso) {
        if (proceso.getDuracion() <= 0D) {
            procesos.remove(proceso);
        }
    }

    private void borrarTarea(Proceso proceso) {
        LinkedList<Tarea> tareas = proceso.getTareas();
        if (!tareas.isEmpty()) {
            Tarea tarea = tareas.getFirst();
            System.out.println("Se ejecuto la tarea: " + tarea.getNombre() + " del proceso " + proceso.getId());
            tareas.pop();
        }
    }
    
    private List<Proceso> construirProcesos(){
        LinkedList<Tarea> t1p1 = new LinkedList<Tarea>();
        Tarea t1 = new Tarea("Leer");
        Tarea t2 = new Tarea("Escritura");
       
        t1p1.push(t1);
        t1p1.push(t2);
        
        LinkedList<Tarea> t1p2 = new LinkedList<Tarea>();
        Tarea t3 = new Tarea("Dibujar");
        Tarea t4 = new Tarea("Pintar");
       
        t1p2.push(t3);
        t1p2.push(t4);
        
        LinkedList<Tarea> t1p3 = new LinkedList<Tarea>();
        Tarea t5 = new Tarea("Contruir");
        Tarea t6 = new Tarea("Picar");
        Tarea t7 = new Tarea("Destruir");
       
        t1p3.push(t5);
        t1p3.push(t6);
        t1p3.push(t7);

        Proceso p1 = new Proceso(1L, "Zoom", EstadoProceso.LISTO, 2D, 2, t1p1);
        Proceso p2 = new Proceso(2L, "Word", EstadoProceso.LISTO, 2D, 1,t1p2);
        Proceso p3 = new Proceso(3L, "PowerPoint", EstadoProceso.LISTO, 3D,3, t1p3);

        List<Proceso> procesos = new ArrayList<Proceso>();
        procesos.add(p1);
        procesos.add(p2);
        procesos.add(p3);
        
        ordenarListaPorPrioridad(procesos);
        
        return procesos;
    }
    
    private void ordenarListaPorPrioridad(List<Proceso> procesos){
        System.out.println("Sin ordenar por prioridad");
        procesos.forEach( (p) -> { System.out.println("Id:" + p.getId()+"-prioridad:"+p.getPrioridad()); } );
        
        Collections.sort(procesos, (o1, o2) -> o1.getPrioridad().compareTo(o2.getPrioridad()));
        
        System.out.println("Ordenados por prioridad");
        procesos.forEach( (p) -> { System.out.println("Id:" + p.getId()+"-prioridad:"+p.getPrioridad()); } );
        System.out.println("");
       
    }

}
