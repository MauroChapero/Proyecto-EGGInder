package com.proyecto.egginder.servicios;

import com.proyecto.egginder.entidades.Alumno;
import com.proyecto.egginder.entidades.Voto;
import com.proyecto.egginder.enumeraciones.Role;
import com.proyecto.egginder.repositorios.AlumnoRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlumnoServicio {
    
    @Autowired
    private AlumnoRepositorio alumnoRepositorio;
    
    @Transactional
    public Alumno crearPerfil(String nombre, String apellido, String email, String clave1, String clave2) throws Exception{
        //Validar los datos del formulario para crear un alumno
        validar(nombre, apellido, email, clave1, clave2);
        //Creacion del alumno y se guarda en el repositorio
        Alumno alumno = new Alumno();
        alumno.setNombre(nombre);
        alumno.setApellido(apellido);
        alumno.setEmail(email);
        alumno.setClave(clave1);
        alumno.setRol(Role.USUARIO);
        return alumnoRepositorio.save(alumno);
    }
    
    @Transactional
    public Alumno modificarPerfil(String id, String nombre, String apellido, String email, String clave1, String clave2) throws Exception{
        //Validar los datos del formulario para modificar al alumno
        validar(nombre, apellido, email, clave1, clave2);
        //Busqueda del alumno
        Optional<Alumno> optional = alumnoRepositorio.findById(id);
        //Si se encuentra un alumno se lo modifica y se guarda en el repositorio
        if (optional.isPresent()) {
            Alumno alumno = optional.get();
            alumno.setNombre(nombre);
            alumno.setApellido(apellido);
            alumno.setEmail(email);
            alumno.setClave(clave1);
            alumno.setClave(clave2);
            return alumnoRepositorio.save(alumno);
        //Si no se encuentra al alumno, se devuelve una Exception indicandolo y se termina el metodo
        } else {
            throw new Exception("No existe un alumno con ese ID.");
        }
    }
    
    @Transactional
    public Alumno asignarCambiarVotos(String id, Voto votoAprender, Voto votoEnseniar) throws Exception{
        Optional<Alumno> respuesta = alumnoRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Alumno alumno = respuesta.get();
            alumno.setVotoAprender(votoAprender);
            alumno.setVotoEnseniar(votoEnseniar);
            return alumnoRepositorio.save(alumno);
        } else {
            throw new Exception("No existe un alumno con ese ID.");
        }
    }
    
    @Transactional
    public void eliminarAlumno(String id){
        //Eliminar del repositorio los datos de un alumno asignandolo por su id 
        Alumno alumno = getOne(id);
        alumnoRepositorio.delete(alumno);
    }
    
    @Transactional(readOnly = true)
    public List<Alumno> listarAlumnos(){
        return alumnoRepositorio.findAll();
    }
    
    @Transactional(readOnly = true)
    public Alumno getOne(String id){
        //Trae un alumno por su id
        return alumnoRepositorio.getById(id);
    }

    public void validar(String nombre, String apellido, String email, String clave1, String clave2) throws Exception{
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("Nombre no puede estar vacio.");
        }
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new Exception("Apellido no puede estar vacio.");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new Exception("Email no puede estar vacio.");
        }
        if (clave1 == null || clave2 == null || clave2.trim().isEmpty() || clave1.trim().isEmpty()) {
            throw new Exception("La constraseña no puede estar vacia.");
        }
        if (!clave1.equals(clave2)) {
            throw new Exception("Las contraseñas deben coincidir.");
        }
        if (clave1.length() < 8 || clave1.length() > 16) {
            throw new Exception("La contraseña debe contener entre 8 y 16 caracteres");
        }
    }
}
/*
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
    private Voto votoAprender;
    
    //VOTO DEL ALUMNO DE LAS MATERIAS A ENSEÑAR
    @OneToOne
    private Voto votoEnseniar;
*/