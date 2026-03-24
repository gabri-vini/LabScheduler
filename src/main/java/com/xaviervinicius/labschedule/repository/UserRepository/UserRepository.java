package com.xaviervinicius.labschedule.repository.UserRepository;

import com.xaviervinicius.labschedule.models.UserModel.AccountState;
import com.xaviervinicius.labschedule.models.UserModel.Role;
import com.xaviervinicius.labschedule.models.UserModel.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    Optional<UserModel> findByEmail(String email);

    List<UserModel> findAllByState(AccountState state);

    Boolean existsByEmailAndRoleAndState(String email, Role role, AccountState state);
    Boolean existsByIdAndRoleAndState(UUID id, Role role, AccountState state);
}
