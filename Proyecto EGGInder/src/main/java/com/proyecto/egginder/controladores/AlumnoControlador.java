package com.proyecto.egginder.controladores;

import com.proyecto.egginder.entidades.Alumno;
import com.proyecto.egginder.servicios.AlumnoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/alumno")
public class AlumnoControlador {

    // Servicio del alumno
    @Autowired
    private AlumnoServicio alumnoServicio;
    
    // Registrar un perfil (nombre, apellido, email, clave)
    
    @GetMapping("/registrarse")
    public String formularioRegistroPerfil(){
        return "registro.html";
    }
    
    @PostMapping("/registrarse")
    public String registrarPerfil(ModelMap model, @RequestParam String nombre, @RequestParam String apellido, 
            @RequestParam String email, @RequestParam String clave1, @RequestParam String clave2) throws Exception{
        
        try {
            Alumno perfil = alumnoServicio.crearPerfil(nombre, apellido, email, clave1, clave2);
            model.put("exito", "Perfil registrado correctamente!");
            return "registro.html";
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "registro.html";
        }
    }
}