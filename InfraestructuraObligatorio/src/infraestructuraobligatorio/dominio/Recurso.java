package infraestructuraobligatorio.dominio;

import java.math.BigDecimal;
import java.util.List;

public class Recurso {
    
    private Integer id;
    private String nombre;
    private boolean enUso;
    
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

    public boolean isEnUso() {
        return enUso;
    }

    public void setEnUso(boolean enUso) {
        this.enUso = enUso;
    }

    
    public Recurso(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.enUso = false;
    }

    public Recurso() {
        this.id = (int) (Math.random()*987654321+1);
        this.enUso = false;
    }
    
    
    
    
}
