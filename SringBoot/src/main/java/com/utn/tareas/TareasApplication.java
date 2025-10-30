package com.utn.tareas;

import com.utn.tareas.model.Prioridad;
import com.utn.tareas.service.MensajeService;
import com.utn.tareas.service.TareaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TareasApplication implements CommandLineRunner {

    private final TareaService tareaService;
    private final MensajeService mensajeService;

    public TareasApplication(TareaService tareaService, MensajeService mensajeService) {
        this.tareaService = tareaService;
        this.mensajeService = mensajeService;
    }

    public static void main(String[] args) {
        SpringApplication.run(TareasApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        mensajeService.mostrarBienvenida();
        tareaService.mostrarConfiguracion();
        System.out.println();

        System.out.println("📋 Tareas iniciales:");
        tareaService.listarTodas().forEach(System.out::println);
        System.out.println();

        System.out.println("➕ Agregando nueva tarea...");
        tareaService.agregarTarea("Preparar presentación", Prioridad.ALTA);
        System.out.println();

        System.out.println("⏳ Tareas pendientes:");
        tareaService.listarPendientes().forEach(System.out::println);
        System.out.println();

        System.out.println("✅ Marcando tarea #1 como completada...");
        tareaService.marcarComoCompletada(1L);
        System.out.println();

        System.out.println(tareaService.obtenerEstadisticas());
        System.out.println();

        System.out.println("✔️  Tareas completadas:");
        tareaService.listarCompletadas().forEach(System.out::println);
        System.out.println();

        mensajeService.mostrarDespedida();
    }
}