package com.test.trainee.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.trainee.exception.BadRequestException;
import com.test.trainee.exception.ObjectNotFoundException;
import com.test.trainee.model.TimeTracking;
import com.test.trainee.service.TimeTrackingService;

import lombok.AllArgsConstructor;

//REST controller class for handling time tracking-related operations.
@RestController
@AllArgsConstructor
@RequestMapping(path = "api/v1")
public class TimeTrackingController {

    // Injecting TimeTrackingService dependency.
    private final TimeTrackingService timeTrackingService;

    // GET endpoint for get add time tracking record from database.
    @GetMapping(path = "admin/all/timeTracking")
    public ResponseEntity<List<TimeTracking>> getAllTimeTracking() throws ObjectNotFoundException {
        return new ResponseEntity<>(timeTrackingService.getAllTimeTracking(),
                HttpStatus.OK);
    }

    // GET endpoint for get time tracking record with specific id from database.
    @GetMapping(path = "user/timeTracking/{id}")
    public ResponseEntity<TimeTracking> getTimeTrackingById(@PathVariable final Long id)
            throws ObjectNotFoundException {
        return new ResponseEntity<>(timeTrackingService.getTimeTrackingById(id),
                HttpStatus.OK);
    }

    // DELETE endpoint for delete time tracking record from database with specific
    // id.
    @DeleteMapping(path = "both/delete/timeTracking/{taskId}/{timeTackingId}")
    public ResponseEntity<TimeTracking> deleteTimeTrackingById(@PathVariable final Long taskId,
            @PathVariable final Long timeTackingId)
            throws BadRequestException {
        return new ResponseEntity<>(timeTrackingService.deleteTrackingById(
                taskId,
                timeTackingId),
                HttpStatus.NO_CONTENT);
    }

    // POST endpoint for add time tracking record to task by specific task id.
    @PostMapping(path = "user/new/timeTrack/task/{taskId}")
    public ResponseEntity<TimeTracking> addNewTimeTracking(@PathVariable final Long taskId,
            @RequestBody final TimeTracking timeTracking) throws ObjectNotFoundException {
        return new ResponseEntity<>(timeTrackingService.addNewTimeTracking(taskId, timeTracking), HttpStatus.OK);
    }

}
