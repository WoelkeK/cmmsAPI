package pl.medos.cmmsApi.controllers;

import org.springframework.web.bind.annotation.*;
import pl.medos.cmmsApi.exception.UserNotFoundException;
import pl.medos.cmmsApi.model.UserOld;
import pl.medos.cmmsApi.service.UserService;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class UserController_old {

    private static final Logger LOGGER = Logger.getLogger(UserController_old.class.getName());
    private UserService userService;

    public UserController_old(UserService userService) {
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
    public UserOld create(@RequestBody UserOld userOld) {
        LOGGER.info("createUser(" + userOld + ")");
        UserOld createdUserOld = userService.create(userOld);
        LOGGER.info("createUser(...)");
        return createdUserOld;
    }

    @GetMapping("/user/{id}")
    public UserOld read(@PathVariable(name = "id") Long id) throws UserNotFoundException {
        LOGGER.info("readUser(" + id + ")");
        UserOld readedUserOld = userService.read(id);
        LOGGER.info("readUser(...) " + readedUserOld);
        return readedUserOld;
    }

    @PutMapping("/user")
    public UserOld update(@RequestBody UserOld userOld) {
        LOGGER.info("updateUser(" + userOld + ")");
        UserOld updatedUserOld = userService.update(userOld);
        LOGGER.info("updateUser(...) " + updatedUserOld);
        return updatedUserOld;
    }

    @DeleteMapping("/user/{id}")
    public String delete(@PathVariable(name = "id") Long id) {
        LOGGER.info("deleteUser(" + id + ")");
        String deleteMessage = userService.delete(id);
        LOGGER.info("deleteUser(...)");
        return deleteMessage;
    }
}
