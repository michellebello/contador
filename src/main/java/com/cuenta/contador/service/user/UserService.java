package com.cuenta.contador.service.user;

import com.cuenta.contador.service.user.User.UserID;

public interface UserService {
    User getUser(UserID id);

    void createUser(User user);
}
