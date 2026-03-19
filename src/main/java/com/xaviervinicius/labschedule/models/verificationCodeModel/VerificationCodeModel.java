package com.xaviervinicius.labschedule.models.verificationCodeModel;

import com.xaviervinicius.labschedule.utils.DateUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Entity
@Table(name = "verification_code_tb")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class VerificationCodeModel {
    public static final byte MINUTES_TO_LIVE = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
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
        this.code = UUID.randomUUID().toString().substring(0, 8);
    }
}
