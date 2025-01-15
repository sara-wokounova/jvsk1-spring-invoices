package cz.sda.java.remotesk1.invoices.repository;

import cz.sda.java.remotesk1.invoices.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, String> {
    List<User> findByUsernameContainingIgnoreCase(String searchText);
}
