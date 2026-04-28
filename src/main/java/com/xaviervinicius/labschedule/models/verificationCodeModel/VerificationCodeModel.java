package com.xaviervinicius.labschedule.models.verificationCodeModel;

import com.xaviervinicius.labschedule.utils.DateUtils;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Entity
@Table(name = "verification_code_tb")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class VerificationCodeModel {
    public static final byte MINUTES_TO_LIVE = 10;
    final static SecureRandom random = new SecureRandom();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID userId;

    @Column(nullable = false)
    private String code;

    @CreationTimestamp
    @Column(nullable = false)
    private Instant createdAt;

    public boolean isExpired(){
        return DateUtils.now().isAfter(this.createdAt.plus(MINUTES_TO_LIVE, ChronoUnit.MINUTES));
    }

    @PrePersist
    public void generateCode(){
        this.code = String.valueOf(random.nextInt(100000, 999999));
    }

    public VerificationCodeModel(UUID userId) {
        this.userId = userId;
    }
}
