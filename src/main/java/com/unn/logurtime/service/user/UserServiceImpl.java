package com.unn.logurtime.service.user;

import com.unn.logurtime.model.User;
import com.unn.logurtime.client.input.UpdateUser;
import com.unn.logurtime.client.output.PageRecords;
import com.unn.logurtime.repository.RoleRepository;
import com.unn.logurtime.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public User addUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User getUser(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public void deleteUser(String username) {
        User user = getUser(username);
        if(user != null){
            userRepository.delete(user);
        }
    }

    @Override
    public User updateUser(User user) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) userDetails.getAuthorities();
        if (userDetails.getUsername().equals(user.getUsername())
            || authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
            return userRepository.saveAndFlush(user);
        else
            return null;
    }

    @Override
    public String updateRoleOrPassword(UpdateUser userInput) {
        String status = "ERROR";
        if(userInput.getChangeableAttrs() != null && userInput.getChangeableAttrs().length() > 0) {
            User user = getUser(userInput.getUsername());
            switch (userInput.getChangeableAttrs()) {
                case "r":
                    user.setRole(roleRepository.getRoleByName(userInput.getNewRole()));
                    status = "OK";
                    break;
                case "p":
                    if (userInput.getNewPassword().length() >= 4 && userInput.getNewPassword().length() < 25) {
                        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                        user.setPassword(encoder.encode(userInput.getNewPassword()));
                        status = "OK";
                    }
                    break;
                case "rp":
                    if (userInput.getNewPassword().length() >= 4 && userInput.getNewPassword().length() < 25) {
                        user.setRole(roleRepository.getRoleByName(userInput.getNewRole()));
                        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                        user.setPassword(encoder.encode(userInput.getNewPassword()));
                        status = "OK";
                    }
                    break;
            }
            if(status.equals("OK") && userRepository.saveAndFlush(user) == null)
                status = "ERROR";
        }
        return status;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public PageRecords<User> getUsersOnPage(int page, int limit) {
        return new PageRecords<User>(getAll(), page, limit);
    }

    @Override
    public String createNewUserByUsernameAndPassword(String username, String pword1, String pword2) {
        System.out.println(username+"  "+ pword1+" "+ pword2);
        if(username != null && username.length() > 3 && pword1 != null && pword1.equals(pword2)){
            if(userRepository.findByUsername(username) != null)
                return "/signup?duplicate";
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            User user = new User(username, encoder.encode(pword1), roleRepository.getOne(4L));
            addUser(user);
            return "/login?new";
        }
        return "/signup?error";
    }
}
