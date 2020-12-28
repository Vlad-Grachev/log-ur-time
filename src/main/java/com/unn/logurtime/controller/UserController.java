package com.unn.logurtime.controller;

import com.unn.logurtime.model.User;
import com.unn.logurtime.client.input.UpdateUser;
import com.unn.logurtime.client.output.PageRecords;
import com.unn.logurtime.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping()
    public User newUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/{username}")
    public User getUser(@PathVariable String username){
        return userService.getUser(username);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/id/{id}")
    public User getUser(@PathVariable Long id){
        return userService.getUser(id);
    }

    @GetMapping("/current")
    public String getCurrent(Principal principal){
        return  principal.getName();
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{username}")
    public void deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/update")
    public String updateUser(@RequestBody UpdateUser updateUser) {
        return userService.updateRoleOrPassword(updateUser);
    }

    @PostMapping("/newpw")
    public String changePassword(@RequestBody UpdateUser updateUser, Principal principal) {
        return userService.updateRoleOrPassword(
                new UpdateUser(principal.getName(), "p", null, updateUser.getNewPassword()));
    }

    @Secured("ROLE_ADMIN")
    @GetMapping()
    public List<User> getUsers() {
        return userService.getAll();
    }

    @GetMapping("paged")
    public PageRecords<User> getResourceOnPage(@RequestParam int page, @RequestParam int limit){
        return userService.getUsersOnPage(page, limit);
    }

    @PostMapping("/create")
    public String createNewUser(
            @RequestParam String username,
            @RequestParam String password1,
            @RequestParam String password2) {
        return userService.createNewUserByUsernameAndPassword(username, password1, password2);
    }
}
