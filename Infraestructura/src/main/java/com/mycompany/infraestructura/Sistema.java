package com.mycompany.infraestructura;

import com.mycompany.infraestructura.dominio.Proceso;
import com.mycompany.infraestructura.dominio.Tarea;
import com.mycompany.infraestructura.dominio.enums.EstadoProceso;
import java.util.LinkedList;

public class Sistema {

    private Double quantum = 1D;

    public void procesar() {
        LinkedList<Tarea> t1p1 = new LinkedList<Tarea>();
        LinkedList<Tarea> t1p2 = new LinkedList<Tarea>();
        LinkedList<Tarea> t1p3 = new LinkedList<Tarea>();
        Tarea t1 = new Tarea("Leer");
        Tarea t2 = new Tarea("Escritura");

        t1p1.add(t2);
        t1p1.add(t1);
        t1p1.add(t2);

        t1p2.add(t2);
        t1p2.add(t1);

        t1p3.add(t2);
        t1p3.add(t1);

        Proceso p1 = new Proceso(1L, "Zoom", EstadoProceso.LISTO, 3D, t1p1);
        Proceso p2 = new Proceso(2L, "Word", EstadoProceso.LISTO, 2D, t1p2);
        Proceso p3 = new Proceso(3L, "PowerPoint", EstadoProceso.LISTO, 2D, t1p3);

        LinkedList<Proceso> procesos = new LinkedList<Proceso>();
        procesos.push(p1);
        procesos.push(p2);
        procesos.push(p3);

        while (!procesos.isEmpty()) {
            for (Proceso proceso : procesos) {
                ejecutar(proceso);
                borrarElementoLista(procesos, proceso);
            }
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

    private void borrarElementoLista(LinkedList<Proceso> procesos, Proceso proceso) {
        if (proceso.getDuracion() <= 0D) {
            procesos.pop();
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

}
