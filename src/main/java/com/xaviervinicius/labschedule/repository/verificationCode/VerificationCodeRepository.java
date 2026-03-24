package com.xaviervinicius.labschedule.repository.verificationCode;

import com.xaviervinicius.labschedule.models.verificationCodeModel.VerificationCodeModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCodeModel, Long> {
    @Transactional
    @Modifying
    @Query(value = """
            DELETE FROM verification_code_tb
            WHERE NOW() > DATE_ADD(verification_code_tb.created_at, INTERVAL :minutes MINUTE)
        """, nativeQuery = true)
    int deleteAllExpired(@Param("minutes") int minutes);

    Optional<VerificationCodeModel> findByCodeAndUserId(Long code, UUID userId);

    void deleteAllByUserId(UUID userId); //User can have only one verification code at time
}
