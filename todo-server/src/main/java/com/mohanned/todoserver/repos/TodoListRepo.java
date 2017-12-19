package com.mohanned.todoserver.repos;

import com.mohanned.todoserver.models.TodoList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TodoListRepo extends CrudRepository<TodoList, String>{
}
