package com.mfpc.mfpc_distributed_transactions.service;

import com.mfpc.mfpc_distributed_transactions.business_model.User;
import com.mfpc.mfpc_distributed_transactions.data_model.UserDb;
import com.mfpc.mfpc_distributed_transactions.mapper.UserMapper;
import com.mfpc.mfpc_distributed_transactions.repository.UserRepository;
import com.mfpc.mfpc_distributed_transactions.transaction.model.Transaction;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractService<User, UserDb> implements UserService {
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        super(userRepository);
        this.userMapper = userMapper;
    }

    @Override
    protected UserDb tToTDb(User user) {
        return userMapper.userToUserDb(user);
    }

    @Override
    protected User tDbToT(UserDb userDb, Transaction transaction) {
        return userMapper.userDbToUser(userDb);
    }
}
