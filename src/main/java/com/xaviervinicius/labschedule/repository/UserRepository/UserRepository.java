package com.xaviervinicius.labschedule.repository.UserRepository;

import com.xaviervinicius.labschedule.models.UserModel.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
}
