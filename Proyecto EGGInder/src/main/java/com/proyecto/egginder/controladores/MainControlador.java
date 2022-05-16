package com.proyecto.egginder.controladores;

import com.proyecto.egginder.entidades.Materia;
import com.proyecto.egginder.servicios.AlumnoServicio;
import com.proyecto.egginder.servicios.MateriaServicio;
import java.util.List;
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
    
    @Autowired
    private MateriaServicio materiaServicio;

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
    public String formularioPerfil(ModelMap model) {
        List<Materia> listMaterias = materiaServicio.listarMaterias();
        model.addAttribute("materias", listMaterias);
        return "registro";
    }

    @PostMapping("/registro")
        public String registrarPerfil(ModelMap model, @RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String email, @RequestParam String clave1, @RequestParam String clave2, @RequestParam String idMateria) {
        try {
            Materia materia = materiaServicio.getOne(idMateria);
            alumnoServicio.crearPerfil(nombre, apellido, email, clave1, clave2, materia);
            model.put("exito", "Perfil creado exitosamente!");
            return "redirect:/login";
        } catch (Exception e) {
            List<Materia> listMaterias = materiaServicio.listarMaterias();
            model.put("error", e.getMessage());
            model.addAttribute("materias", listMaterias);
            return "registro";
        }
    }
}
