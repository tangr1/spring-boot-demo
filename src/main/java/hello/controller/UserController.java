package hello.controller;

import hello.domain.User;
import hello.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public Iterable<User> list() {
        return userRepository.findAll();
    }

    @RequestMapping("/users/{id}")
    public User get(final @PathVariable Long id) {
        return userRepository.findOne(id);
    }
}
