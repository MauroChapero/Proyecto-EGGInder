package com.proyecto.egginder.controladores;

import com.proyecto.egginder.servicios.AlumnoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public String index(@RequestParam(required = false) String login, ModelMap model) { // FALTA FORMULARLO PARA SECURITY
        if (login != null) {
            return "redirect:/registro";
        } else {
            return "/login";
        }
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USUARIO')")
    @GetMapping("/inicio")
    public String inicio(){
        return "/inicio";
    }
    
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, ModelMap model) {
        if (error != null) {
            model.put("error", "Usuario o Contraseña incorrectos");
        }
        if (logout != null) {
            model.put("logout", "Desconectado correctamente");
        }
        return "login";
    }
    
    @GetMapping("/registro")
        public String formularioPerfil() {
        return "registro";
    }

    @PostMapping("/registro")
        public String registrarPerfil(ModelMap model, @RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String email, @RequestParam String clave1, @RequestParam String clave2) {
        try {
            alumnoServicio.crearPerfil(nombre, apellido, email, clave1, clave2);
            model.put("exito", "Perfil creado exitosamente!");
            return "redirect:/login";
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "registro";
        }
    }
}
