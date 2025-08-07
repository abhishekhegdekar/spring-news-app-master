package com.emesall.news.service;

import org.springframework.stereotype.Service;

import com.emesall.news.model.User;
import com.emesall.news.model.UserList;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserListService {
    
    public UserList createUserList(User user) {
        UserList userList = new UserList();
        userList.setUser(user);
        log.info("Created new user list for user: {}", user.getEmail());
        return userList;
    }
    
    public void save(UserList userList) {
        log.info("Saving user list with ID: {}", userList.getId());
        // Implementation would go here
    }
}
