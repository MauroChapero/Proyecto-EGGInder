package com.proyecto.egginder.controladores;

import com.proyecto.egginder.Servicios.VotoServicio;
import com.proyecto.egginder.entidades.Alumno;
import com.proyecto.egginder.entidades.Materia;
import com.proyecto.egginder.entidades.Voto;
import com.proyecto.egginder.servicios.AlumnoServicio;
import com.proyecto.egginder.servicios.MateriaServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/perfil")
public class AlumnoControlador {

    // Servicio del alumno
    @Autowired
    private AlumnoServicio alumnoServicio;

    @Autowired
    private VotoServicio votoServicio;

    @Autowired
    private MateriaServicio materiaServicio;

    @GetMapping("/voto")
    public String votoFormulario(ModelMap model, HttpSession session) {
        
        Alumno perfil = (Alumno) session.getAttribute("usuariosession");
        model.addAttribute("perfil", perfil);
        return "/test/voto-formulario";
    }

    @PostMapping("/voto")
    public String asignarModificarVoto(ModelMap model, HttpSession session ,/*@RequestParam String id,*/
            @RequestParam String nombreMateria, @RequestParam String nombreMateria2) {
        try {
            Alumno perfil = (Alumno) session.getAttribute("usuariosession");
            
            Materia materia = materiaServicio.buscarPorNombre(nombreMateria);
            Materia materia2 = materiaServicio.buscarPorNombre(nombreMateria2);

            Voto votoAprender = votoServicio.save(materia);
            Voto votoEnseniar = votoServicio.save(materia);

            alumnoServicio.asignarCambiarVotos(perfil.getId(), votoAprender, votoEnseniar);
            model.put("exito", "VOTOS ASIGNADOS CORRECTAMENTE");
            return "/test/voto-formulario";
        } catch (Exception e) {
            Alumno perfil = (Alumno) session.getAttribute("usuariosession");
            model.put("error", e.getMessage());
            model.addAttribute("perfil", perfil);
            return "/test/voto-formulario";
        }
    }
}
