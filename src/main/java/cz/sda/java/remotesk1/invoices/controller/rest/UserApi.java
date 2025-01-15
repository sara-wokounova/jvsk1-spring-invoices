package cz.sda.java.remotesk1.invoices.controller.rest;

import cz.sda.java.remotesk1.invoices.controller.rest.request.CreateUser;
import cz.sda.java.remotesk1.invoices.controller.rest.request.UpdateUser;
import cz.sda.java.remotesk1.invoices.exception.NotFoundException;
import cz.sda.java.remotesk1.invoices.model.User;
import cz.sda.java.remotesk1.invoices.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/rest/users")
class UserApi {

    private final UserService userService;

    @Autowired
    UserApi(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    ResponseEntity<User> addUser(@RequestBody CreateUser user) {
        User created = userService.addUser(user.username(), user.password());
        return ResponseEntity.created(URI.create("/users/" + created.getUsername())).body(created);
    }

    @GetMapping("/")
    List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/!find")
    List<User> findUsers(@RequestParam("name") String name) {
        return userService.findUsersByName(name);
    }

    @GetMapping("/{id}")
    User getUser(@NonNull @PathVariable("id") String id) {
        return userService.getUser(id);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> removeUser(@PathVariable("id") String id) {
        userService.removeUser(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    ResponseEntity<User> updateUser(@PathVariable("id") String id, @RequestBody UpdateUser user) {
        var updated = userService.updateUser(user.username(), user.password());
        return ResponseEntity.ok(updated);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        log.debug("User not found", e);
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        log.debug("User not found", e);
        return ResponseEntity.notFound().build();
    }
}
