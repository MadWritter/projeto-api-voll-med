package com.vollmed.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vollmed.api.model.service.PacienteService;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
    
    private PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    //TODO implementar os métodos http
}
