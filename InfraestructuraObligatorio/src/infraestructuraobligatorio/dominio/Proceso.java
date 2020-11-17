package infraestructuraobligatorio.dominio;

import infraestructuraobligatorio.dominio.enums.EstadoProceso;
import java.util.LinkedList;

public class Proceso{
    
    private Long id;
    private String nombre;
    private EstadoProceso estado;
    private Double duracion;
    private LinkedList<Tarea> tareas;
    private Integer prioridad;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public EstadoProceso getEstado() {
        return estado;
    }

    public void setEstado(EstadoProceso estado) {
        this.estado = estado;
    }

    public Double getDuracion() {
        return duracion;
    }

    public void setDuracion(Double duracion) {
        this.duracion = duracion;
    }

    public LinkedList<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(LinkedList<Tarea> tareas) {
        this.tareas = tareas;
    }

    public Integer getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }
    
    

    public Proceso(Long id, String nombre, EstadoProceso estado, Double duracion, Integer prioridad, LinkedList<Tarea> tareas) {
        this.id = id;
        this.nombre = nombre;
        this.estado = estado;
        this.duracion = duracion;
        this.tareas = tareas;
        this.prioridad = prioridad;
    }

    
}
