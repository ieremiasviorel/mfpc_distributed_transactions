package com.mfpc.mfpc_distributed_transactions.mapper;

import com.mfpc.mfpc_distributed_transactions.business_model.User;
import com.mfpc.mfpc_distributed_transactions.data_model.UserDb;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User userDbToUser(UserDb userDb);

    UserDb userToUserDb(User user);
}
