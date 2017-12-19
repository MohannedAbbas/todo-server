package com.mohanned.todoserver.repos;

import com.mohanned.todoserver.models.TaskId;
import com.mohanned.todoserver.models.TodoTask;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TodoTaskRepo extends CrudRepository<TodoTask, TaskId>{

    Iterable<TodoTask> findAllByTaskIdListId(String listId);
}
