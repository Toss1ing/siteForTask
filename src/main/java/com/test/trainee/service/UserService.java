package com.test.trainee.service;

import static com.test.trainee.Utilities.Constant.BAD_REQUEST_MSG;
import static com.test.trainee.Utilities.Constant.NOT_FOUND_MSG;
import static com.test.trainee.Utilities.Constant.OBJECT_EXIST_MSG;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.test.trainee.exception.BadRequestException;
import com.test.trainee.exception.ObjectExistException;
import com.test.trainee.exception.ObjectNotFoundException;
import com.test.trainee.model.Role;
import com.test.trainee.model.Task;
import com.test.trainee.model.User;
import com.test.trainee.repository.RoleRepository;
import com.test.trainee.repository.TaskRepository;
import com.test.trainee.repository.UserRepository;

import lombok.AllArgsConstructor;

// Service class for user-related operations.
@Service
@AllArgsConstructor
public class UserService {

    // Repositories and password encoder for dependency injection.
    private final TaskRepository taskRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Method to get all users.
    public List<User> getAllUser() throws ObjectNotFoundException {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new ObjectNotFoundException(NOT_FOUND_MSG);
        }

        return users;
    }

    // Method to add a new user.
    public User addUser(User user) throws ObjectExistException {
        User newUser = user;
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        Optional<User> repositoryUser = userRepository.findByName(user.getName());
        if (repositoryUser.isPresent()) {
            throw new ObjectExistException(OBJECT_EXIST_MSG);
        }

        Optional<Role> role = roleRepository.findByName("ROLE_USER");

        newUser.getUserRole().add(role.get());
        userRepository.save(newUser);
        return newUser;
    }

    // Method to get a user by ID.
    public User getUserById(final Long id) throws ObjectNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new ObjectNotFoundException(NOT_FOUND_MSG);
        }
        return user.get();
    }

    // Method to assign a task to a user by IDs.
    public User taskInUserById(final Long userId, final Long taskId) throws ObjectNotFoundException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new ObjectNotFoundException(NOT_FOUND_MSG);
        }
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isEmpty()) {
            throw new ObjectNotFoundException(NOT_FOUND_MSG);
        }
        user.get().getTasks().add(task.get());
        userRepository.save(user.get());
        return user.get();
    }

    // Method to change a user's phone number.
    public User changePhoneNumber(final String phoneNumber, final Long userId) throws ObjectNotFoundException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new ObjectNotFoundException(NOT_FOUND_MSG);
        }
        user.get().setPhoneNumber(phoneNumber);
        userRepository.save(user.get());
        return user.get();
    }

    // Method to add admin role to a user.
    public User addRoleAdminToUser(Long id) throws ObjectNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new ObjectNotFoundException(NOT_FOUND_MSG);
        }

        Optional<Role> role = roleRepository.findByName("ROLE_ADMIN");

        user.get().getUserRole().add(role.get());

        userRepository.save(user.get());

        return user.get();
    }

    // Method to delete a user by ID.
    public User deleteUserById(final Long id) throws BadRequestException {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new BadRequestException(BAD_REQUEST_MSG);
        }

        userRepository.delete(user.get());

        return user.get();
    }
}
