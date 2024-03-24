package com.kmm.controller;

import com.kmm.domain.User;
import com.kmm.exception.ResourceNotFoundException;
import com.kmm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    //http://localhost:8080/swagger-ui/index.html
    private final UserRepository userRepository;

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getById(@PathVariable(name = "id") Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
    }

    @GetMapping
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public User updateById(@PathVariable(name = "id") Long id,
                                           @RequestBody User user) {
        User existedUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        existedUser.setEmail(user.getEmail());
        existedUser.setPhone(user.getPhone());
        existedUser.setName(user.getName());
        return existedUser;
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable(name = "id") Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found"));
        userRepository.delete(user);
    }
}
