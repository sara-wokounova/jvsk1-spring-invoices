package cz.sda.java.remotesk1.invoices.service;

import cz.sda.java.remotesk1.invoices.model.User;

import java.util.List;

public interface UserService {
    User addUser(String username, String password);
    void removeUser(String username);
    User getUser(String username);
    User updateUser(String username, String password);
    List<User> getAllUsers();
    List<User> findUsersByName(String name);
}
