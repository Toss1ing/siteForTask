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
import com.test.trainee.model.User;
import com.test.trainee.service.UserService;

import lombok.AllArgsConstructor;

//REST controller class for handling user-related operations.
@RestController
@AllArgsConstructor
@RequestMapping(path = "api/v1")
public class UserController {

    // Injecting UserService dependency.
    private final UserService userService;

    // POST endpoint to registration new user.
    @PostMapping(path = "user/new")
    public ResponseEntity<User> addUser(@RequestBody final User user) throws ObjectExistException {
        return new ResponseEntity<>(userService.addUser(user), HttpStatus.CREATED);
    }

    // PUT endpoint for create new admin for user id.
    @PutMapping(path = "admin/{id}")
    public ResponseEntity<User> addRoleAdminToUser(@PathVariable final Long id) throws ObjectNotFoundException {
        return new ResponseEntity<>(userService.addRoleAdminToUser(id), HttpStatus.OK);
    }

    // GET endpoint for retrieving all user from database.
    @GetMapping(path = "admin/all")
    public ResponseEntity<List<User>> getAllUser() throws ObjectNotFoundException {
        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }

    // GET endpoint for retrieving user with specific id from database.
    @GetMapping(path = "user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable final Long id) throws ObjectNotFoundException {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    // PUT endpoint for change phone number user by id.
    @PutMapping(path = "user/{id}/new/number/{phoneNumber}")
    public ResponseEntity<User> changePhoneNumber(@PathVariable final Long id, @PathVariable final String phoneNumber)
            throws ObjectNotFoundException {
        return new ResponseEntity<>(userService.changePhoneNumber(phoneNumber, id), HttpStatus.OK);
    }

    // DELETE endpoint to delete user from database.
    @DeleteMapping(path = "admin/delete/user/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable final Long id) throws BadRequestException {
        return new ResponseEntity<>(userService.deleteUserById(id), HttpStatus.NO_CONTENT);
    }

}
