package infraestructuraobligatorio.dominio;

import infraestructuraobligatorio.dominio.enums.EstadoProceso;
import infraestructuraobligatorio.dominio.enums.Permiso;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Random;

public class Proceso{
    
    private Integer id;
    private String nombre;
    private EstadoProceso estado;
    private Double duracion;
    private LinkedList<Tarea> tareas;
    private Recurso recurso;
    private Permiso permiso;
    private Usuario usuario;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Recurso getRecurso() {
        return recurso;
    }

    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }

    public Permiso getPermiso() {
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    

    public Proceso(Integer id, String nombre, EstadoProceso estado, Double duracion, 
                    LinkedList<Tarea> tareas, Recurso recurso, Usuario usuario, Permiso permiso) {
        this.id = id;
        this.nombre = nombre;
        this.estado = estado;
        this.duracion = duracion;
        this.tareas = tareas;
        this.recurso = recurso;
        this.usuario = usuario;
        this.permiso = permiso;
    }

    public Proceso() {
        this.id = (int) (Math.random()*987654321+1);
        this.estado = EstadoProceso.LISTO;
    }

    
}
