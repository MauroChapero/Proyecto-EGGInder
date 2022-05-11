package com.proyecto.egginder.controladores;

import com.proyecto.egginder.entidades.Alumno;
import com.proyecto.egginder.servicios.AlumnoServicio;
import com.proyecto.egginder.servicios.MatchServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/match")
public class MatchControlador {
    
    @Autowired
    private MatchServicio matchServicio;
    
    @Autowired
    private AlumnoServicio alumnoServicio;
    
    @GetMapping("/crear")
    public String match(){
        return "/test/match-crear";
    }
    
    @PostMapping("/crear")
    public String crearMatch(ModelMap model, @RequestParam String idAlumno1, @RequestParam String idAlumno2){
        try {    
        Alumno alumno1 = alumnoServicio.getOne(idAlumno1);
        Alumno alumno2 = alumnoServicio.getOne(idAlumno2);
        matchServicio.crearMatch(alumno1, alumno2);
        model.put("exito", "Match creado, Alumno1 = " + alumno1.getNombre() + ", Alumno 2 = " + alumno2.getNombre());
        return "/test/match-crear";
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "/test/match-crear";
        }
    }

}
