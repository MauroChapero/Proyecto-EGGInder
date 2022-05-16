package com.proyecto.egginder.servicios;

import com.proyecto.egginder.entidades.Alumno;
import com.proyecto.egginder.entidades.Voto;
import com.proyecto.egginder.enumeraciones.Role;
import com.proyecto.egginder.repositorios.AlumnoRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class AlumnoServicio implements UserDetailsService{
    
    @Autowired
    private AlumnoRepositorio alumnoRepositorio;
    
    @Transactional
    public Alumno crearPerfil(String nombre, String apellido, String email, String clave1, String clave2) throws Exception{
        
        validar(nombre, apellido, email, clave1, clave2);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        Alumno perfil = new Alumno();
        perfil.setNombre(nombre);
        perfil.setApellido(apellido);
        perfil.setEmail(email);
        perfil.setClave(encoder.encode(clave1));
        perfil.setRol(Role.USUARIO);
        return alumnoRepositorio.save(perfil);
        
        /*//Validar los datos del formulario para crear un alumno
        validar(nombre, apellido, email, clave1, clave2);
        //Encriptador de password
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //Creacion del alumno y se guarda en el repositorio
        Alumno alumno = new Alumno();
        alumno.setNombre(nombre);
        alumno.setApellido(apellido);
        alumno.setEmail(email);
        //Encripto la clave con el metodo encode()
        alumno.setClave(encoder.encode(clave1));
        alumno.setRol(Role.USUARIO);
        return alumnoRepositorio.save(alumno);*/
    }
    
    @Transactional
    public Alumno modificarPerfil(String id, String nombre, String apellido, String email, String clave1, String clave2) throws Exception{
        //Validar los datos del formulario para modificar al alumno
        validar(nombre, apellido, email, clave1, clave2);
        //Encriptador de password
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //Busqueda del alumno
        Optional<Alumno> optional = alumnoRepositorio.findById(id);;
        //Si se encuentra un alumno se lo modifica y se guarda en el repositorio
        if (optional.isPresent()) {
            Alumno alumno = optional.get();
            alumno.setNombre(nombre);
            alumno.setApellido(apellido);
            alumno.setEmail(email);
            //Encripto la clave con el metodo encode()
            alumno.setClave(clave1);
            return alumnoRepositorio.save(alumno);
        //Si no se encuentra al alumno, se devuelve una Exception indicandolo y se termina el metodo
        } else {
            throw new Exception("No existe un alumno con ese ID.");
        }
    }
    
    @Transactional
    public Alumno crearVotoAprender(String id, Voto votoAprender) throws Exception{
        Optional<Alumno> respuesta = alumnoRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Alumno alumno = respuesta.get();
            alumno.setVotoAprender(votoAprender);
            return alumnoRepositorio.save(alumno);
        } else {
            throw new Exception("No existe un alumno con ese ID.");
        }
    }
    
    @Transactional
    public Alumno crearVotoEnseniar(String id, Voto votoEnseniar) throws Exception{
        Optional<Alumno> respuesta = alumnoRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Alumno alumno = respuesta.get();
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
    
    @Transactional(readOnly = true)
    public Alumno findByEmail(String email){
        return alumnoRepositorio.findByEmail(email);
    }
    
    public Voto buscarVotoAprender(String id){
        Alumno alumno = getOne(id);
        return alumno.getVotoAprender();
    }
    
    public Voto buscarVotoEnseniar(String id){
        Alumno alumno = getOne(id);
        return alumno.getVotoEnseniar();
    }
    
    public void validar(String nombre, String apellido, String email, String clave1, String clave2) throws Exception{
        // si el nombre viene vacio || si el nombre viene unicamente con espacios
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("Nombre no puede estar vacio.");
        }
        
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new Exception("Apellido no puede estar vacio.");
        }
        
        if (email == null || email.trim().isEmpty()) {
            throw new Exception("Email no puede estar vacio.");
        }
        if (!email.contains("@") || !email.contains(".")) {
            throw new Exception("Error de sintaxis: Email debe contener un '@', '.'");
        }
        if (alumnoRepositorio.findByEmail(email)!=null) {
            throw new Exception("Ya existe un usuario con ese correo");
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

    // Metodo abstracto de la interfaz
    // Va a encargarse de crear una sesion, conceder permisos y validar datos
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Alumno usuario = alumnoRepositorio.findByEmail(email);
        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();

            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_" + usuario.getRol());//ROLE_ADMIN O ROLE_USER
            permisos.add(p1);

            //Esto me permite guardar el OBJETO USUARIO LOG, para luego ser utilizado
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);

            User user = new User(usuario.getEmail(), usuario.getClave(), permisos);
            return user;

        } else {
            return null;
        }
    }
}