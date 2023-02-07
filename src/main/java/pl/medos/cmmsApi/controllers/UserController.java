package pl.medos.cmmsApi.controllers;

import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.exception.UserNotFoundException;
import pl.medos.cmmsApi.model.User;
import pl.medos.cmmsApi.service.UserService;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class UserController {

    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public List list() {
        LOGGER.info("userList()");
        List users = userService.list();
        LOGGER.info("userList(...)" + users);
        return users;
    }

    @PostMapping("/user")
    public User create(@RequestBody User user) {
        LOGGER.info("createUser(" + user + ")");
        User createdUser = userService.create(user);
        LOGGER.info("createUser(...)");
        return createdUser;
    }

    @GetMapping("/user/{id}")
    public User read(@PathVariable(name = "id") Long id) throws UserNotFoundException {
        LOGGER.info("readUser(" + id + ")");
        User readedUser = userService.read(id);
        LOGGER.info("readUser(...) " + readedUser);
        return readedUser;
    }

    @PutMapping("/user")
    public User update(@RequestBody User user) {
        LOGGER.info("updateUser(" + user + ")");
        User updatedUser = userService.update(user);
        LOGGER.info("updateUser(...) " + updatedUser);
        return updatedUser;
    }

    @DeleteMapping("/user/{id}")
    public String delete(@PathVariable(name = "id") Long id) {
        LOGGER.info("deleteUser(" + id + ")");
        String deleteMessage = userService.delete(id);
        LOGGER.info("deleteUser(...)");
        return deleteMessage;
    }
}
