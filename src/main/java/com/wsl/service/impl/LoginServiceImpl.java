package com.wsl.service.impl;

import com.wsl.bean.Permissions;
import com.wsl.bean.Role;
import com.wsl.bean.User;
import com.wsl.service.LoginService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author tan
 * @version 1.0
 * @date 2020/9/24 14:49
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Override
    public User getUserByName(String getMapByName) {
        return getMapByName(getMapByName);
    }

    private User getMapByName(String userName){

        Permissions permissions1 = new Permissions("1", "query");
        Permissions permissions2 = new Permissions("2", "add");

        Set<Permissions> permissionsSet = new HashSet<>();
        permissionsSet.add(permissions1);
        permissionsSet.add(permissions2);

        Role role = new Role("1", "admin", permissionsSet);
        Set<Role> roleSet = new HashSet<>();

        roleSet.add(role);
        User user = new User("1", "wsl", "123456", roleSet);
        Map<String, User> map = new HashMap<>();
        map.put(user.getUserName(),user);

        Set<Permissions> permissionsSet1 = new HashSet<>();
        permissionsSet1.add(permissions1);
        Role role1 = new Role("2", "user", permissionsSet1);
        Set<Role> roleSet1 = new HashSet<>();
        roleSet1.add(role1);
        User user1 = new User("2", "zhangsan", "123456", roleSet1);
        map.put(user1.getUserName(), user1);
        return map.get(userName);
    }
}