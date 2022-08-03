package academy.mindswap.restfulwebservice.controllers;

import academy.mindswap.restfulwebservice.handlers.UserHandler;
import academy.mindswap.restfulwebservice.persistence.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class
UserController {

    @Autowired
    private UserHandler userHandler;


    /**
     * Method who returns a list of all users present in the database.
     */
    @GetMapping
    public Flux<User> getAllUsers() {
        return userHandler.findAll();

    }

    /**
     * Method that adds a new user to the database.
     * @param newUser represents the user to add.
     * @return response ok with user added to the database, or bad request if input name is not valid.
     */
    @PostMapping
    public Mono<User> createUser(@RequestBody User newUser) {
        return userHandler.createUser(newUser);
    }

    /**
     * Method that searched for an user with a specific Id.
     * @param id represents the id to search.
     * @return response ok with user retrieved from cache or database, or bad request, if input id does not exist in the database.
     */
    @GetMapping(value="/id/{id}")
    public Mono<User> getUserById(@PathVariable String id) {
        return userHandler.getUserById(id);
    }

    @GetMapping(value="/test")
    public String test() {
        return "test ran fine";
    }
}


