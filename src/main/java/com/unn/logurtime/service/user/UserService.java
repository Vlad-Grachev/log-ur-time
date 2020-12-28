package com.unn.logurtime.service.user;

import com.unn.logurtime.model.User;
import com.unn.logurtime.client.input.UpdateUser;
import com.unn.logurtime.client.output.PageRecords;

import java.util.List;

public interface UserService {
    User addUser(User user);
    User getUser(String username);
    User getUser(Long id);
    void deleteUser(String username);
    User updateUser(User user);
    String updateRoleOrPassword(UpdateUser userInput);
    List<User> getAll();
    PageRecords<User> getUsersOnPage(int page, int limit);
    String createNewUserByUsernameAndPassword(String username, String pword1, String pword2);
}
