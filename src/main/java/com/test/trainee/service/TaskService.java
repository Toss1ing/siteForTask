package com.test.trainee.service;

import static com.test.trainee.Utilities.Constant.BAD_REQUEST_MSG;
import static com.test.trainee.Utilities.Constant.NOT_FOUND_MSG;
import static com.test.trainee.Utilities.Constant.OBJECT_EXIST_MSG;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.test.trainee.exception.BadRequestException;
import com.test.trainee.exception.ObjectExistException;
import com.test.trainee.exception.ObjectNotFoundException;
import com.test.trainee.model.Task;
import com.test.trainee.model.User;
import com.test.trainee.repository.TaskRepository;
import com.test.trainee.repository.UserRepository;

import lombok.AllArgsConstructor;

// Service class for task-related operations.
@Service
@AllArgsConstructor
public class TaskService {

    // Repositories for dependency injection.
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    // Method to get all tasks.
    public List<Task> getAllTask() throws ObjectNotFoundException {
        List<Task> tasks = taskRepository.findAll();

        if (tasks.isEmpty()) {
            throw new ObjectNotFoundException(NOT_FOUND_MSG);
        }

        return tasks;
    }

    // Method to get a task by ID for admin.
    public Task getAdminTaskById(final Long id) throws ObjectNotFoundException {
        Optional<Task> task = taskRepository.findById(id);

        if (task.isEmpty()) {
            throw new ObjectNotFoundException(NOT_FOUND_MSG);
        }

        return task.get();
    }

    // Method to add a new task by admin.
    public Task adminAddTask(final Task task) throws ObjectExistException {
        Optional<Task> repositoryTask = taskRepository.findByName(task.getName());

        if (repositoryTask.isPresent()) {
            throw new ObjectExistException(OBJECT_EXIST_MSG);
        }

        taskRepository.save(task);

        return task;
    }

    // Method to get tasks by user ID.
    public List<Task> getTasksInUserById(final Long userId) throws ObjectNotFoundException {
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new ObjectNotFoundException(NOT_FOUND_MSG);
        }

        return user.get().getTasks();
    }

    // Method to add a task to a user.
    public Task userAddTask(Long id, Task task) throws ObjectNotFoundException {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new ObjectNotFoundException(NOT_FOUND_MSG);
        }

        taskRepository.save(task);

        user.get().getTasks().add(task);

        userRepository.save(user.get());

        return task;
    }

    // Method to rename a task by ID.
    public Task newNameTaskById(final Long id, final String name) throws ObjectNotFoundException {
        Optional<Task> task = taskRepository.findById(id);

        if (task.isEmpty()) {
            throw new ObjectNotFoundException(NOT_FOUND_MSG);
        }

        task.get().setName(name);

        taskRepository.save(task.get());

        return task.get();
    }

    // Method to delete a task from a user by IDs.
    public Task deleteTaskFromUserById(final Long userId, final Long taskId) throws BadRequestException {
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new BadRequestException(BAD_REQUEST_MSG);
        }

        Optional<Task> task = taskRepository.findById(taskId);

        if (task.isEmpty()) {
            throw new BadRequestException(BAD_REQUEST_MSG);
        }

        user.get().removeTask(taskId);

        userRepository.save(user.get());
        taskRepository.delete(task.get());

        return task.get();
    }
}
