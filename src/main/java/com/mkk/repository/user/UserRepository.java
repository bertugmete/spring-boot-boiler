package com.mkk.repository.user;

import com.mkk.entity.user.MkkUser;
import com.mkk.repository.BaseJpaRepository;


public interface UserRepository extends BaseJpaRepository<MkkUser, Integer> {
    MkkUser findByUsername(String username);
}
