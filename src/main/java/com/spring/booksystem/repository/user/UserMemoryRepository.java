package com.spring.booksystem.repository.user;


import com.spring.booksystem.domain.user.User;
import com.spring.booksystem.domain.user.UserSex;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class UserMemoryRepository implements UserRepository {

    private static Long seq = 0L;
    private static final Map<Long, User> store = new HashMap<>();

    @Override
    public User findById(Long id) {
        return store.get(id);
    }

    @Override
    public List<User> findAll() {
        return store.values().stream().toList();
    }

    @Override
    public User save(User user) {
        user.setId(++seq);
        store.put(user.getId(), user);
        return store.get(seq);
    }

    @Override
    public void update(Long id, User updatedUser) {
        User targetUser = findById(id);
        targetUser.setName(updatedUser.getName());
        targetUser.setAge(updatedUser.getAge());
        targetUser.setPhone(updatedUser.getPhone());
        targetUser.setEmail(updatedUser.getEmail());
        targetUser.setSex(updatedUser.getSex());
    }

    @Override
    public void remove(Long id) {
        store.remove(id);
    }
}
