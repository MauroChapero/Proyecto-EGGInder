package com.proyecto.egginder.controladores;

import com.proyecto.egginder.servicios.AlumnoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class MainControlador {

    @Autowired
    private AlumnoServicio alumnoServicio;
    
    @GetMapping("/")
    public String redirectRegistro(){
        return "redirect:/login";
    }
    
    @GetMapping("/login")
    public String login(){ // FALTA FORMULARLO PARA SECURITY
        return "login";
    }
    
    @GetMapping("/registro")
    public String formularioPerfil(){
        return "Proyecto main1";
    }
    
    @PostMapping("/registro")
    public String registrarPerfil(ModelMap model, @RequestParam String nombre, @RequestParam String apellido, 
            @RequestParam String email, @RequestParam String clave1, @RequestParam String clave2){
        try {
            alumnoServicio.crearPerfil(nombre, apellido, email, clave1, clave2);
            model.put("exito", "Perfil creado exitosamente!");
            return "Proyecto main1";
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "Proyecto main1";
        }
    }
}
