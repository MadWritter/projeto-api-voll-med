package com.vollmed.api.model.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MedicoServiceTest {

    private static MedicoService medicoService;

    @BeforeAll
    public static void setUp() {
        medicoService = new MedicoService();
    }

    @Test
    @DisplayName("Deve cadastrar um médico corretamente")
    public void deveCadastrarUmMedico() {

    }
}