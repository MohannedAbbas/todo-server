package com.mohanned.todoserver.restapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohanned.todoserver.models.TaskId;
import com.mohanned.todoserver.models.TodoList;
import com.mohanned.todoserver.models.TodoTask;
import com.mohanned.todoserver.repos.TodoListRepo;
import com.mohanned.todoserver.repos.TodoTaskRepo;
import com.mohanned.todoserver.restapi.webmodels.TodoBody;
import com.mohanned.todoserver.restapi.webmodels.TodoListBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api")
public class TodoController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TodoListRepo todoListRepo;

    @Autowired
    private TodoTaskRepo todoTaskRepo;


    @GetMapping("/lists")
    public
    @ResponseBody
    List<TodoList>
    getLists() {
        List<TodoList> lists = new ArrayList<>();

        todoListRepo.findAll().forEach(todoList -> lists.add(todoList));

        return lists;
    }

    @PostMapping("/lists")
    public
    @ResponseBody
    TodoList
    newList(@RequestBody TodoListBody todoListBody) {
        if (todoListBody != null) {
            return todoListRepo.save(new TodoList(UUID.randomUUID().toString(), todoListBody.getName()));
        }

        return null;
    }

    @PutMapping("/lists/{listId}")
    public
    @ResponseBody
    TodoList
    updateList(@RequestParam("listId") String listId, @RequestBody TodoListBody todoListBody) {
        if (todoListBody != null && listId != null) {
            TodoList list = todoListRepo.findOne(listId);

            list.setName(todoListBody.getName());

            if (list != null) {
                return todoListRepo.save(list);
            }
        }

        return null;
    }

    @DeleteMapping("/lists/{listId}")
    public
    @ResponseBody
    String
    deleteList(@PathVariable("listId") String listId) {

        if (listId != null) {
            TodoList list = todoListRepo.findOne(listId);
            if (list != null) {
                todoListRepo.delete(listId);

                AtomicInteger childCount = new AtomicInteger(0);
                TaskId taskId = new TaskId(listId, null);

                Iterable<TodoTask> tasks = todoTaskRepo.findAllByTaskIdListId(listId);

                tasks.forEach(todoTask -> {
                    todoTaskRepo.delete(todoTask.getTaskId());
                    childCount.incrementAndGet();
                });

                return "Deleted list with id:" + listId + ", it had tasks:" + childCount.get();
            }
        }
        return "List not deleted, id:" + listId;
    }

    @GetMapping("/tasks")
    public
    @ResponseBody
    List<TodoTask>
    getTasks() {
        List<TodoTask> lists = new ArrayList<>();

        todoTaskRepo.findAll().forEach(todoList -> lists.add(todoList));

        return lists;
    }

    @GetMapping("/tasks/{listId}")
    public
    @ResponseBody
    List<TodoTask>
    getTasks(@PathVariable("listId") String listId) {
        List<TodoTask> lists = new ArrayList<>();

        todoTaskRepo.findAllByTaskIdListId(listId).forEach(todoList -> lists.add(todoList));

        return lists;
    }

    @PostMapping("/tasks/{listId}")
    public
    @ResponseBody
    TodoTask
    newTask(@PathVariable("listId") String listId, @RequestBody TodoBody todo) {

        if (todo != null && listId != null) {
            TaskId taskId = new TaskId();
            taskId.setListId(listId);
            taskId.setTaskId(UUID.randomUUID().toString());

            TodoTask task = new TodoTask();

            task.setTaskId(taskId);
            task.setDescription(todo.getDescription());
            task.setDone(todo.getDone());

            return todoTaskRepo.save(task);
        }

        return null;
    }

    @PutMapping("/tasks/{listId}/{taskId}")
    public
    @ResponseBody
    TodoTask
    updateTask(@PathVariable("listId") String listId, @PathVariable("taskId") String taskId, @RequestBody TodoBody todo) {
        if (todo != null && listId != null && taskId != null) {
            TaskId taskIdObj = new TaskId(listId, taskId);

            TodoTask task = todoTaskRepo.findOne(taskIdObj);

            if (task != null) {

                try {
                    logger.info("Found task: {}", new ObjectMapper().writeValueAsString(task));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

                task.setDescription(todo.getDescription());
                task.setDone(todo.getDone());

                return todoTaskRepo.save(task);
            }

        }

        return null;
    }

    @DeleteMapping("/tasks/{listId}/{taskId}")
    public
    @ResponseBody
    String
    deleteTask(@PathVariable("listId") String listId, @PathVariable("taskId") String taskId) {
        if (listId != null && taskId != null) {
            TaskId taskIdObj = new TaskId(listId, taskId);

            TodoTask task = todoTaskRepo.findOne(taskIdObj);

            if (task != null) {
                todoTaskRepo.delete(task);

                return "Task deleted, id:" + taskId;
            }

        }

        return "Did not delete task with id:" + taskId;
    }
}
