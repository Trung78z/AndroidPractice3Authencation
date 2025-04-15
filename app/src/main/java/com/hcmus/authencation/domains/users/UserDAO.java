package com.hcmus.authencation.domains.users;

import java.util.HashMap;
import java.util.Map;

public class UserDAO {
    private Map<String, User> userMap;
    private static UserDAO instance;

    public UserDAO() {
        userMap = new HashMap<>();
        saveTempUserMap();
    }

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    private void saveTempUserMap() {
        userMap.put("user1", new User("user1", "password@123"));
        userMap.put("admin", new User("admin", "admin123"));
        userMap.put("test", new User("test", "test123"));
    }

    public void addUser(User user) {
        userMap.put(user.getUsername().toLowerCase(), user);
    }

    public User findUserByUsername(String username) {
        return userMap.get(username.toLowerCase());
    }

    public Map<String, User> getAllUsers() {
        return userMap;
    }

    public boolean checkLogin(String username, String password) {
        if (username == null || password == null) return false;

        User user = userMap.get(username.toLowerCase());
        return user != null && user.getPassword().equals(password);
    }
}
