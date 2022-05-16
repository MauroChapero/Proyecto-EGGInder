package com.proyecto.egginder.controladores;

import com.proyecto.egginder.Servicios.VotoServicio;
import com.proyecto.egginder.entidades.Alumno;
import com.proyecto.egginder.entidades.Materia;
import com.proyecto.egginder.entidades.Voto;
import com.proyecto.egginder.servicios.AlumnoServicio;
import com.proyecto.egginder.servicios.MatchServicio;
import com.proyecto.egginder.servicios.MateriaServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USUARIO')")
@RequestMapping("/perfil")
public class AlumnoControlador {

    ////////////////
    // SERVICIOS
    ////////////////
    @Autowired
    private AlumnoServicio alumnoServicio;

    @Autowired
    private VotoServicio votoServicio;

    @Autowired
    private MateriaServicio materiaServicio;

    @Autowired
    private MatchServicio matchServicio;

    ////////////////
    // REDIRECT
    ////////////////
    @GetMapping("/")
    public String redirect() {
        return "redirect:/perfil/ver";
    }

    ////////////////
    // PERFIL
    ////////////////
    @GetMapping("/ver")
    public String perfil(ModelMap model, HttpSession session) {
        Alumno perfil = (Alumno) session.getAttribute("usuariosession");
        Alumno usuario = alumnoServicio.getOne(perfil.getId());
        
        List<Alumno> usuarios = alumnoServicio.alumnosMateriaCoinciden(usuario.getVoto().getMateria().getId());
        
        Alumno usuario2 = usuarios.get(0);
        if (usuario2.getId().equals(usuario.getId())) {
            usuario2 = usuarios.get(1);
        }
        
        model.addAttribute("perfil", perfil);
        model.addAttribute("usuario", usuario);
        model.addAttribute("usuario2", usuario2);
        model.addAttribute("voto", usuario.getVoto());
        //  model.addAttribute("materia", usuario.getVoto().getMateria().getNombre());
        return "perfil";
    }

    ////////////////
    // EDITAR PERFIL
    ////////////////
    @GetMapping("/modificar-perfil")
    public String formPerfil(ModelMap model, HttpSession session) {
        Alumno perfil = (Alumno) session.getAttribute("usuariosession");
        Alumno usuario = alumnoServicio.getOne(perfil.getId());
        model.addAttribute("perfil", perfil);
        model.addAttribute("usuario", usuario);
        return "/soledad/editardatos";
    }

    @PostMapping("/modificar-perfil")
    public String guardarPerfil(ModelMap model, HttpSession session, RedirectAttributes redirectAttributes,
            @RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String email, @RequestParam String clave1, @RequestParam String clave2) {
        try {
            Alumno perfil = (Alumno) session.getAttribute("usuariosession");
            Alumno usuario = alumnoServicio.getOne(perfil.getId());

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (encoder.matches(clave1, usuario.getClave())) {
                alumnoServicio.modificarPerfil(usuario.getId(), nombre, apellido, email, clave1, clave2);
            } else {
                throw new Exception("Por favor, ingrese su clave correctamente para poder guardar los cambios");
            }
            model.addAttribute("perfil", perfil);
            model.addAttribute("usuario", usuario);
            return "redirect:/perfil/";
        } catch (Exception e) {
            Alumno perfil = (Alumno) session.getAttribute("usuariosession");
            Alumno usuario = alumnoServicio.getOne(perfil.getId());
            model.put("error", e.getMessage());
            model.addAttribute("perfil", perfil);
            model.addAttribute("usuario", usuario);
            return "/soledad/editardatos";
        }
    }

    ////////////////
    // Editar Clave nueva
    ////////////////
    @GetMapping("/modificar-clave")
    public String formClave(ModelMap model, HttpSession session) {
        Alumno perfil = (Alumno) session.getAttribute("usuariosession");
        Alumno usuario = alumnoServicio.getOne(perfil.getId());

        model.addAttribute("perfli", perfil);
        model.addAttribute("usuario", usuario);
        return "/test/modificar-clave";
    }

    @PostMapping("/modificar-clave")
    public String guardarClave(ModelMap model, HttpSession session, @RequestParam String claveEncriptada,
            @RequestParam String claveFormulario, @RequestParam String claveFormularioVerificada) {
        try {
            Alumno perfil = (Alumno) session.getAttribute("usuariosession");
            Alumno usuario = alumnoServicio.getOne(perfil.getId());

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (encoder.matches(claveEncriptada, usuario.getClave())) {
                alumnoServicio.cambiarClave(perfil.getId(), claveFormulario, claveFormularioVerificada);
            } else {
                throw new Exception("Por favor, ingrese su clave correctamente para poder guardar los cambios");
            }

            model.addAttribute("perfil", perfil);
            model.addAttribute("usuario", usuario);
            return "redirect:/perfil/";
        } catch (Exception e) {
            Alumno perfil = (Alumno) session.getAttribute("usuariosession");
            Alumno usuario = alumnoServicio.getOne(perfil.getId());
            model.put("error", e.getMessage());
            model.addAttribute("perfil", perfil);
            model.addAttribute("usuario", usuario);
            return "/test/modificar-clave";
        }
    }

    ////////////////
    // EDITAR VOTO
    ////////////////
    @GetMapping("/modificar-voto")
    public String modificarVoto(ModelMap model, HttpSession session) {

        Alumno perfil = (Alumno) session.getAttribute("usuariosession");
        Alumno usuario = alumnoServicio.getOne(perfil.getId()); //  PASE A UN USUARIO CON EL ID DE LA SESSION
        List<Materia> listMaterias = materiaServicio.listarMaterias();
        // la mejor manera de modelar los cambios actualizados, es modelar un usuario en vez de una sesion
        model.addAttribute("materias", listMaterias);
        model.addAttribute("perfil", perfil);
        model.put("voto", usuario.getVoto());  // USE EL ID DEL USUARIO
        // Se trabaja con el objeto usuario, en vez de la session, porque nunca se actualizan los valores
        //model.put("voto2", usuario.getVotoEnseniar());  // USE EL ID DEL USUARIO
        return "voto-formulario";
    }

    @PostMapping("/modificar-voto")
    public String guardarVoto(ModelMap model, HttpSession session, @RequestParam String idMateria) {

        try {
            Alumno usuario = (Alumno) session.getAttribute("usuariosession");
            Alumno perfil = alumnoServicio.getOne(usuario.getId());

            if (perfil == null) {
                throw new Exception("httpSession = null, sesion no iniciada");
            }

            Materia materia = materiaServicio.getOne(idMateria);

            if (perfil.getVoto() != null) {
                votoServicio.edit(perfil.getVoto().getId(), materia);
            } else {
                Voto votoAprender = votoServicio.save(materia);
                alumnoServicio.crearVoto(perfil.getId(), votoAprender);
            }

            if (perfil == null) {   //  Session sigue abierta y no se reseteo
                throw new Exception("httpSession = null, sesion no iniciada");
            }

            return "redirect:/perfil/";
        } catch (Exception e) {
            Alumno perfil = (Alumno) session.getAttribute("usuariosession");

            List<Materia> listMaterias = materiaServicio.listarMaterias();
            model.addAttribute("materias", listMaterias);
            model.put("error", e.getMessage());
            model.addAttribute("perfil", perfil);
            return "voto-formulario";
        }
    }
    ////////////////
    //MATCH
    ////////////////

//    @GetMapping("/match")
//    public String match(ModelMap model, HttpSession session) {
//        Alumno perfil = (Alumno) session.getAttribute("usuariosession");
//        Alumno usuario = alumnoServicio.getOne(perfil.getId());
//        
//        List<Alumno> usuarios = alumnoServicio.alumnosMateriaCoinciden(usuario.getVoto().getMateria().getId());
//        
//        Alumno usuario2 = usuarios.get(0);
//        if (usuario2.getId().equals(usuario.getId())) {
//            usuario2 = usuarios.get(1);
//        }
////        if (usuario2.getId().equals(usuario.getId())) {
////            throw new Exception("No se ha encontrado un match aun");
////        }
//        model.addAttribute("perfil", perfil);
//        model.addAttribute("usuario", usuario);
//        model.addAttribute("usuario2", usuario2);
//        return "/test/match";
//    }
//
//    @PostMapping("/match")
//    public String matchPost(ModelMap model, HttpSession session) {
//        try {
//            Alumno perfil = (Alumno) session.getAttribute("usuariosession");
//            Alumno usuario = alumnoServicio.getOne(perfil.getId());
//
//            if (usuario.getVoto() == null) {
//                throw new Exception("No has seleccionado tu materia todavia");
//            }
//            List<Alumno> usuarios = alumnoServicio.alumnosMateriaCoinciden(usuario.getVoto().getMateria().getId());
//            
//            Alumno usuario2 = usuarios.get(0);
//            matchServicio.crearMatch(usuario2, usuario2);
//            
//            
//            return "/test/match";
//
//        } catch (Exception e) {
//            Alumno perfil = (Alumno) session.getAttribute("usuariosession");
//            Alumno usuario = alumnoServicio.getOne(perfil.getId());
//            
//            model.put("error", e.getMessage());
//            model.addAttribute("perfil", perfil);
//            model.addAttribute("usuario", usuario);
//            
//            return "/test/match";
//        }
//    }
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
