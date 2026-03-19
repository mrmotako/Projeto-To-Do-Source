package com.example.definitiva_projeto.repository;

import com.example.definitiva_projeto.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    // Busca tarefas pendentes (feito = false)
    List<Task> findByFeitoFalse();

    // Busca tarefas concluídas (feito = true)
    List<Task> findByFeitoTrue();

    // Busca por nome (contendo)
    List<Task> findByNomeContainingIgnoreCase(String nome);
}
