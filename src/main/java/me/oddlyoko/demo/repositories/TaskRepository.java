package me.oddlyoko.demo.repositories;

import me.oddlyoko.demo.models.Project;
import me.oddlyoko.demo.models.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findAllByProjectId(long projectId, Pageable pageable);

    Page<Task> findAllByProject(Project project, Pageable pageable);
}
