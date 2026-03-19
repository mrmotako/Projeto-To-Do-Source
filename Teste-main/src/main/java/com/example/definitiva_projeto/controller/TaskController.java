package com.example.definitiva_projeto.controller;

import com.example.definitiva_projeto.model.Task;
import com.example.definitiva_projeto.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;  // Spring injeta automaticamente

    @GetMapping
    public List<Task> listar() {
        return taskRepository.findAll();
    }

    @GetMapping("/pendentes")
    public List<Task> listarPendentes() {
        return taskRepository.findByFeitoFalse();
    }

    @PostMapping
public Task criar(@RequestBody Task task) {
    if (task.getDataCriacao() == null) {
        task.setDataCriacao(java.time.LocalDateTime.now()); // ✅ trocado
    }
    if (task.getFeito() == null) {
        task.setFeito(false);
    }
    return taskRepository.save(task);
}
    @PutMapping("/{id}/done")
public Task marcar(@PathVariable Integer id) {
    Task task = taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Task não encontrada com id: " + id));
    task.setFeito(true);
    task.setFinalizadoEm(java.time.LocalDateTime.now()); // ✅ trocado
    return taskRepository.save(task);
}

    @PutMapping("/{id}/undone")
    public Task desmarcar(@PathVariable Integer id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task não encontrada com id: " + id));
        task.setFeito(false);
        task.setFinalizadoEm(null);
        return taskRepository.save(task);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task não encontrada com id: " + id);
        }
        taskRepository.deleteById(id);
    }

    // Endpoint extra: buscar por ID
    @GetMapping("/{id}")
    public Task buscarPorId(@PathVariable Integer id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task não encontrada com id: " + id));
    }
}