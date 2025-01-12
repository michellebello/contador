package com.cuenta.contador.service.user;

import com.cuenta.contador.service.user.User.UserID;
import com.cuenta.contador.store.user.UserStore;

import javax.inject.Inject;

public class UserServiceImpl implements UserService {
    private final UserStore userStore;

    @Inject
    public UserServiceImpl(UserStore userStore) {
        this.userStore = userStore;
    }

    @Override
    public User getUser(UserID id) {
        return userStore.getUser(id);
    }

    @Override
    public void createUser(User user) {
        userStore.storeUser(user);
    }
}
