package com.proyecto.egginder.entidades;
import com.proyecto.egginder.enumeraciones.Role;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Alumno {

    //IDENTIFICADOR UNICO
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    //ATRIBUTOS
    private String nombre;
    private String apellido;
    private String email;
    private String clave;
    
    //ROL
    @Enumerated(EnumType.STRING)
    private Role rol;
    
    //VOTO DEL ALUMNO DE LAS MATERIAS A APRENDER
    @OneToOne
    private Voto voto;

    public Alumno() {
    }

    public Alumno(String nombre, String apellido, String email, String clave, Role rol, Voto voto) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.clave = clave;
        this.rol = rol;
        this.voto = voto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Role getRol() {
        return rol;
    }

    public void setRol(Role rol) {
        this.rol = rol;
    }

    public Voto getVoto() {
        return voto;
    }

    public void setVoto(Voto voto) {
        this.voto = voto;
    }

    
    
}