package com.proyecto.egginder.controladores;

import com.proyecto.egginder.Servicios.VotoServicio;
import com.proyecto.egginder.entidades.Alumno;
import com.proyecto.egginder.entidades.Materia;
import com.proyecto.egginder.entidades.Voto;
import com.proyecto.egginder.servicios.AlumnoServicio;
import com.proyecto.egginder.servicios.MateriaServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
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
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USUARIO')")
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
    public String crearVoto(ModelMap model, HttpSession session) {

        Alumno perfil = (Alumno) session.getAttribute("usuariosession");
        List<Materia> listMaterias = materiaServicio.listarMaterias();
        model.addAttribute("materias", listMaterias);
        model.addAttribute("perfil", perfil);
        model.addAttribute("voto1", perfil.getVotoAprender());
        model.addAttribute("voto2", perfil.getVotoEnseniar());
        return "/test/voto-formulario";
    }

    @PostMapping("/voto")
    public String crearVoto(ModelMap model, HttpSession session, @RequestParam String idMateria1, 
            @RequestParam String idMateria2) {
        
        try {
            Alumno perfil = (Alumno) session.getAttribute("usuariosession");
            if (perfil == null) {
                throw new Exception("httpSession = null, sesion no iniciada");
            }
            Materia materia1 = materiaServicio.getOne(idMateria1);
            Materia materia2 = materiaServicio.getOne(idMateria2);
            Voto votoAprender = votoServicio.save(materia1);
            Voto votoEnseniar = votoServicio.save(materia2);
            
            alumnoServicio.asignarCambiarVotos(perfil.getId(),votoAprender, votoEnseniar);
            return "redirect:/inicio";
        } catch (Exception e) {
            Alumno perfil = (Alumno) session.getAttribute("usuariosession");
            
            List<Materia> listMaterias = materiaServicio.listarMaterias();
            model.addAttribute("materias", listMaterias);
            model.put("error", "Salto un error"); 
            model.addAttribute("perfil", perfil);
            return "/test/voto-formulario";
        }
    }
}

    /*
    @PostMapping("/voto")
    public String asignarModificarVoto(ModelMap model, HttpSession session ,//@RequestParam String id,
            @RequestParam(required = false) String idMateria, @RequestParam(required = false) String idMateria2) {
        try {
            Alumno perfil = (Alumno) session.getAttribute("usuariosession");
            
            Materia materia = materiaServicio.getOne(idMateria);
            Materia materia2 = materiaServicio.getOne(idMateria2);

            Voto votoAprender = votoServicio.save(materia);
            Voto votoEnseniar = votoServicio.save(materia2);

            alumnoServicio.asignarCambiarVotos(perfil.getId(), votoAprender, votoEnseniar);
            model.put("exito", "VOTOS ASIGNADOS CORRECTAMENTE");
            return "redirect:/inicio";
        } catch (Exception e) {
            Alumno perfil = (Alumno) session.getAttribute("usuariosession");
            model.put("fallo", e.getMessage());
            model.addAttribute("perfil", perfil);
            return "/test/voto-formulario";
        }*/
 /* @GetMapping("/voto/crear")
    public String generarVoto(){
        return "/test/guardarVoto.html";
    }
    
    @PostMapping("/voto/crear")
    public String generarVoto(ModelMap model, @RequestParam String nombreMateria) throws Exception {
        try {
            Materia materia = materiaServicio.buscarPorNombre(nombreMateria);
            votoServicio.save(materia);
            model.put("exito", "Voto añadido.");
            return "/test/guardarVoto.html";
        } catch (Exception e) {
            model.put("error", "No se pudo Guardar el voto.");
            return "/test/guardarVoto.html";
        }
    }*/
