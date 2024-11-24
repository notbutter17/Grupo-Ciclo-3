package com.upeu.RegisterUser.controlador;

import java.util.List;
import java.util.Optional;

import com.upeu.RegisterUser.interfaceService.IpersonasService;
import com.upeu.RegisterUser.modelo.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Importación correcta de Model
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping; // Añadido para el manejo de POST
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class Controlador {

    @Autowired
    private IpersonasService service;

    @GetMapping("/")
    public String home() {
        return "home"; // Carga la página principal (home.html)
    }

    // Listar personas
    @GetMapping("/listar")
    public String listar(Model model) {
        List<Persona> personas = service.listar();
        model.addAttribute("personas", personas);
        return "index";
    }

    // Mostrar formulario para agregar nueva persona
    @GetMapping("/new")
    public String agregar(Model model) {
        model.addAttribute("persona", new Persona());
        return "form";
    }

    // Guardar una nueva persona (Usando POST)
    @PostMapping("/save")
    public String save(@Validated Persona p, Model model) {
        // Validación de campos
        if (p.getNombre() == null || p.getApellido() == null || p.getEdad() == 0) {
            model.addAttribute("error", "Debe llenar todos los campos correctamente.");
            return "form";
        }

        // Verifica si es una actualización o creación
        if (p.getId() != 0) {
            Optional<Persona> existingPersona = service.listarId(p.getId());
            if (existingPersona.isPresent()) {
                Persona personaToUpdate = existingPersona.get();
                personaToUpdate.setNombre(p.getNombre());
                personaToUpdate.setApellido(p.getApellido());
                personaToUpdate.setEdad(p.getEdad());
                personaToUpdate.setCelular(p.getCelular());
                service.save(personaToUpdate);
            }
        } else {
            service.save(p); // Es una nueva creación
        }

        return "redirect:/listar"; // Redirige al listado después de guardar
    }
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model) {
        Optional<Persona> persona = service.listarId(id);
        if (persona.isPresent()) {
            model.addAttribute("persona", persona.get());
            return "form"; // Redirige al formulario para editar
        } else {
            return "redirect:/listar"; // Si no encuentra la persona, redirige al listado
        }
    }
    @GetMapping("/eliminar/{id}")
    public String delete(Model model,@PathVariable int id) {
        service.deleted(id);
        return "redirect:/listar";
    }
    // Si no encuentra la persona, redirige al listado

}

