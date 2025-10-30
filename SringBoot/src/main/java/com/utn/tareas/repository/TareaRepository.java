package com.utn.tareas.repository;

import com.utn.tareas.model.Prioridad;
import com.utn.tareas.model.Tarea;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TareaRepository {
    private final List<Tarea> tareas;
    private final AtomicLong idGenerator;

    public TareaRepository() {
        this.tareas = new ArrayList<>();
        this.idGenerator = new AtomicLong(1);

        tareas.add(new Tarea(idGenerator.getAndIncrement(), "Estudiar Spring Boot", Prioridad.ALTA));
        tareas.add(new Tarea(idGenerator.getAndIncrement(), "Hacer ejercicio", Prioridad.MEDIA));
        tareas.add(new Tarea(idGenerator.getAndIncrement(), "Leer documentación", Prioridad.BAJA));
        tareas.add(new Tarea(idGenerator.getAndIncrement(), "Completar TP", Prioridad.ALTA));
    }

    public Tarea guardar(Tarea tarea) {
        if (tarea.getId() == null) {
            tarea.setId(idGenerator.getAndIncrement());
        }
        tareas.add(tarea);
        return tarea;
    }

    public List<Tarea> obtenerTodas() {
        return new ArrayList<>(tareas);
    }

    public Optional<Tarea> buscarPorId(Long id) {
        return tareas.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst();
    }

    public boolean eliminarPorId(Long id) {
        return tareas.removeIf(t -> t.getId().equals(id));
    }
}