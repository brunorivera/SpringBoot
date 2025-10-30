package com.utn.tareas.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class MensajeDevService implements MensajeService {

    @Override
    public void mostrarBienvenida() {
        System.out.println("🚀 ¡Bienvenido al entorno de DESARROLLO!");
        System.out.println("💻 Modo Debug activado - Logs detallados habilitados");
        System.out.println("=====================================\n");
    }

    @Override
    public void mostrarDespedida() {
        System.out.println("\n=====================================");
        System.out.println("👋 ¡Hasta luego! Entorno de desarrollo");
        System.out.println("🔧 Recuerda: estás en modo DEV");
    }
}