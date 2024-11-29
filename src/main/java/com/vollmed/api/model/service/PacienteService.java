package com.vollmed.api.model.service;

import org.springframework.stereotype.Service;

import com.vollmed.api.model.repository.PacienteRepository;

@Service
public class PacienteService {
    
    private PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }
}
