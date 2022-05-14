package com.proyecto.egginder.controladores;

import com.proyecto.egginder.entidades.Alumno;
import com.proyecto.egginder.entidades.Match;
import com.proyecto.egginder.servicios.AlumnoServicio;
import com.proyecto.egginder.servicios.MatchServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  
    @RequestMapping("/eliminar/{id}")
    public String borrarMatch(ModelMap model, @RequestParam String idAlumno1, @RequestParam String idAlumno2) {
        try {
            matchServicio.eliminarMatch(idAlumno1);
            matchServicio.eliminarMatch(idAlumno2);
            model.put("exito", "Match eliminado");
        } catch(Exception e) {
        }
        return "/test/lista";
    }
    /*
    //@PreAuthorize("hasRole('ROLE_ADMIN','ROLE_USER')")
    public String listarMatchs(ModelMap model) throws Exception {
        try {
            List<Match> listaMatchs = matchServicio.listarMatchs();
            model.addAttribute("match", listaMatchs);
            return "listaMatchs.html";
        } catch (Exception e) {
            return "listaMaterias.html";
        }
    }

*/  
    //************************************************************
    //Falta test.
    //Revisar roles.
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/eliminar/{id}")
    public String borrarMatch(ModelMap model, @RequestParam String id) throws Exception {
        try {
            matchServicio.eliminarMatch(id);
            model.put("exito", "Match eliminado");
        } catch(Exception e) {
        }
        return "/test/lista";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/")
    public String listarMatchs(ModelMap model) throws Exception {
        try {
            List<Match> listaMatchs = matchServicio.findAll();
            model.addAttribute("match", listaMatchs);
            return "listaMatchs.html";
        } catch (Exception e) {
            return "listaMatchs.html";
        }
    }
    
    @GetMapping("/modificar/{id}")
    public String editarMatch(ModelMap model, @PathVariable String id, @RequestParam Alumno alumno1, @RequestParam Alumno alumno2) {
        try {
            matchServicio.modificarMatch(id, alumno1, alumno2);
            model.put("exito","Match editado.");
            return "modificaroMatch.html";
        } catch (Exception e) {
            model.put("error","No se logró editar el match.");
            return "modificarMatch.html";
        }
    }
}
