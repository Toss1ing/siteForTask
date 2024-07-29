package com.test.trainee.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.trainee.exception.BadRequestException;
import com.test.trainee.exception.ObjectExistException;
import com.test.trainee.exception.ObjectNotFoundException;
import com.test.trainee.model.Task;
import com.test.trainee.service.TaskService;

import lombok.AllArgsConstructor;

// REST controller for handling task-related operations.
@RestController
@AllArgsConstructor
@RequestMapping(path = "api/v1")
public class TaskController {

    // Injecting TaskService dependency.
    private final TaskService taskService;

    // GET endpoint for retrieving all tasks from the database.
    @GetMapping(path = "admin/all/task")
    public ResponseEntity<List<Task>> getAllTask() throws ObjectNotFoundException {
        return new ResponseEntity<>(taskService.getAllTask(), HttpStatus.OK);
    }

    // GET endpoint for retrieving a task with a specific id from the database.
    @GetMapping(path = "admin/task/{id}")
    public ResponseEntity<Task> getAdminTaskById(@PathVariable final Long id) throws ObjectNotFoundException {
        return new ResponseEntity<>(taskService.getAdminTaskById(id), HttpStatus.OK);
    }

    // GET endpoint for retrieving a list of tasks created by a specific user.
    @GetMapping(path = "user/task/{userId}")
    public ResponseEntity<List<Task>> getUserTaskById(@PathVariable final Long userId, @PathVariable final Long taskId)
            throws ObjectNotFoundException {
        return new ResponseEntity<>(taskService.getTasksInUserById(userId), HttpStatus.OK);
    }

    // POST endpoint for creating a new task without an owner.
    @PostMapping(path = "admin/new/task")
    public ResponseEntity<Task> adminAddTask(@RequestBody final Task task) throws ObjectExistException {
        return new ResponseEntity<>(taskService.adminAddTask(task), HttpStatus.CREATED);
    }

    // POST endpoint for creating a new task and assigning it to a specific user.
    @PostMapping(path = "user/{id}/new/task")
    public ResponseEntity<Task> userAddTask(@PathVariable final Long id, @RequestBody final Task task)
            throws ObjectNotFoundException {
        return new ResponseEntity<>(taskService.userAddTask(id, task), HttpStatus.CREATED);
    }

    // DELETE endpoint for deleting a task with a specific id from the database.
    @DeleteMapping(path = "both/delete/task/{userId}/{taskId}")
    public ResponseEntity<Task> deleteTaskFromUserById(@PathVariable final Long userId, @PathVariable final Long taskId)
            throws BadRequestException {
        return new ResponseEntity<>(taskService.deleteTaskFromUserById(userId, taskId), HttpStatus.OK);
    }

    // PUT endpoint for renaming a task with a specific id.
    @PutMapping(path = "both/task/{id}/new/name/{name}")
    public ResponseEntity<Task> adminNewNameTaskById(@PathVariable final Long id, @PathVariable final String name)
            throws ObjectNotFoundException {
        return new ResponseEntity<>(taskService.newNameTaskById(id, name), HttpStatus.OK);
    }

}
