package infraestructuraobligatorio.dominio;

import infraestructuraobligatorio.dominio.enums.Permiso;
import java.math.BigDecimal;

public class Usuario {
    
    private Integer id;
    private String nombre;
    private Permiso permiso;

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

    public Permiso getPermiso() {
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }

    public Usuario(Integer id, String nombre, Permiso permiso) {
        this.id = id;
        this.nombre = nombre;
        this.permiso = permiso;
    }

    public Usuario() {
        this.id = (int) (Math.random()*987654321+1);
    }
    
    
    
}
