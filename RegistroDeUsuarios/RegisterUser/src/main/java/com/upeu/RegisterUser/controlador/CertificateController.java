package com.tu_proyecto.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CertificateController {

    @GetMapping("/certificado")
    public String mostrarCertificado(Model model) {
        // Datos dinámicos del certificado
        model.addAttribute("nombre", "Juan Pérez");
        model.addAttribute("fecha", "25 de noviembre de 2024");
        model.addAttribute("lugar", "Iglesia Central");

        return "certificate";
    }
}