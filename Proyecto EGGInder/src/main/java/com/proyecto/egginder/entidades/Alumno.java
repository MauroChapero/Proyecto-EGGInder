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
    private String password;
    
    //ROL
    @Enumerated(EnumType.STRING)
    private Role rol;
    
    //VOTO DEL ALUMNO DE LAS MATERIAS A APRENDER
    @OneToOne
    private Voto votoAprender;
    
    //VOTO DEL ALUMNO DE LAS MATERIAS A ENSEÑAR
    @OneToOne
    private Voto votoEnseniar;

    //CONSTRUCTOR VACIO
    public Alumno() {
    }
    
    //CONSTRUCTOR PARAMETRIZADO
    public Alumno(String nombre, String apellido, String email, String password, Role rol, Voto votoAprender, Voto votoEnseniar) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.votoAprender = votoAprender;
        this.votoEnseniar = votoEnseniar;
    }

    //GETTERS Y SETTERS
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRol() {
        return rol;
    }

    public void setRol(Role rol) {
        this.rol = rol;
    }

    public Voto getVotoAprender() {
        return votoAprender;
    }

    public void setVotoAprender(Voto votoAprender) {
        this.votoAprender = votoAprender;
    }

    public Voto getVotoEnseniar() {
        return votoEnseniar;
    }

    public void setVotoEnseniar(Voto votoEnseniar) {
        this.votoEnseniar = votoEnseniar;
    }
    
}