package com.utn.tareas.service;

import com.utn.tareas.model.Prioridad;
import com.utn.tareas.model.Tarea;
import com.utn.tareas.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TareaService {
    private final TareaRepository tareaRepository;

    @Value("${app.nombre}")
    private String nombreApp;

    @Value("${app.max-tareas}")
    private int maxTareas;

    @Value("${app.mostrar-estadisticas}")
    private boolean mostrarEstadisticas;

    public TareaService(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
    }

    public Tarea agregarTarea(String descripcion, Prioridad prioridad) {
        List<Tarea> todas = tareaRepository.obtenerTodas();
        if (todas.size() >= maxTareas) {
            throw new RuntimeException("Se alcanzó el límite máximo de tareas: " + maxTareas);
        }

        Tarea nuevaTarea = new Tarea(null, descripcion, prioridad);
        return tareaRepository.guardar(nuevaTarea);
    }

    public List<Tarea> listarTodas() {
        return tareaRepository.obtenerTodas();
    }

    public List<Tarea> listarPendientes() {
        return tareaRepository.obtenerTodas().stream()
                .filter(t -> !t.isCompletada())
                .collect(Collectors.toList());
    }

    public List<Tarea> listarCompletadas() {
        return tareaRepository.obtenerTodas().stream()
                .filter(Tarea::isCompletada)
                .collect(Collectors.toList());
    }

    public boolean marcarComoCompletada(Long id) {
        return tareaRepository.buscarPorId(id)
                .map(tarea -> {
                    tarea.setCompletada(true);
                    return true;
                })
                .orElse(false);
    }

    public String obtenerEstadisticas() {
        List<Tarea> todas = tareaRepository.obtenerTodas();
        long completadas = todas.stream().filter(Tarea::isCompletada).count();
        long pendientes = todas.size() - completadas;

        return String.format(
                "📊 Estadísticas:\n" +
                        "   Total de tareas: %d\n" +
                        "   Completadas: %d\n" +
                        "   Pendientes: %d",
                todas.size(), completadas, pendientes
        );
    }

    public void mostrarConfiguracion() {
        System.out.println("⚙️  Configuración:");
        System.out.println("   Nombre: " + nombreApp);
        System.out.println("   Máximo de tareas: " + maxTareas);
        System.out.println("   Mostrar estadísticas: " + mostrarEstadisticas);
    }
}