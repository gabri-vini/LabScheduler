package com.xaviervinicius.labschedule.repository.verificationCode;

import com.xaviervinicius.labschedule.models.verificationCodeModel.VerificationCodeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCodeModel, Long> {
    Optional<VerificationCodeModel> findByCodeAndUserId(String code, UUID userId);
}
