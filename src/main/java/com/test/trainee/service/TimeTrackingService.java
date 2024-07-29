package com.test.trainee.service;

import static com.test.trainee.Utilities.Constant.BAD_REQUEST_MSG;
import static com.test.trainee.Utilities.Constant.NOT_FOUND_MSG;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.test.trainee.exception.BadRequestException;
import com.test.trainee.exception.ObjectNotFoundException;
import com.test.trainee.model.Task;
import com.test.trainee.model.TimeTracking;
import com.test.trainee.repository.TaskRepository;
import com.test.trainee.repository.TimeTrackingRepository;

import lombok.AllArgsConstructor;

// Service class for time tracking-related operations.
@Service
@AllArgsConstructor
public class TimeTrackingService {

    // Repositories for dependency injection.
    private final TimeTrackingRepository timeTrackingRepository;
    private final TaskRepository taskRepository;

    // Method to get all time tracking entries.
    public List<TimeTracking> getAllTimeTracking() throws ObjectNotFoundException {
        List<TimeTracking> timeTrackings = timeTrackingRepository.findAll();

        if (timeTrackings.isEmpty()) {
            throw new ObjectNotFoundException(NOT_FOUND_MSG);
        }

        return timeTrackings;
    }

    // Method to get a time tracking entry by ID.
    public TimeTracking getTimeTrackingById(final Long id) throws ObjectNotFoundException {
        Optional<TimeTracking> timeTracking = timeTrackingRepository.findById(id);

        if (timeTracking.isEmpty()) {
            throw new ObjectNotFoundException(NOT_FOUND_MSG);
        }

        return timeTracking.get();
    }

    // Method to delete a time tracking entry by task ID and time tracking ID.
    public TimeTracking deleteTrackingById(final Long taskId, final Long timeTrackingId) throws BadRequestException {
        Optional<Task> task = taskRepository.findById(taskId);

        if (task.isEmpty()) {
            throw new BadRequestException(BAD_REQUEST_MSG);
        }

        Optional<TimeTracking> timeTracking = timeTrackingRepository.findById(timeTrackingId);

        if (timeTracking.isEmpty()) {
            throw new BadRequestException(BAD_REQUEST_MSG);
        }

        task.get().removeTimeTracking(timeTrackingId);

        timeTracking.get().setTrackTask(null);

        taskRepository.save(task.get());

        timeTrackingRepository.deleteById(timeTrackingId);

        return timeTracking.get();
    }

    // Method to add a new time tracking entry to a task.
    public TimeTracking addNewTimeTracking(Long taskId, TimeTracking timeTracking) throws ObjectNotFoundException {
        Optional<Task> task = taskRepository.findById(taskId);

        if (task.isEmpty()) {
            throw new ObjectNotFoundException(NOT_FOUND_MSG);
        }

        task.get().getTaskTimeTracking().add(timeTracking);

        timeTracking.setTrackTask(task.get());

        taskRepository.save(task.get());
        timeTrackingRepository.save(timeTracking);

        return timeTracking;
    }
}
