package com.xaviervinicius.labschedule.models.scheduleModel;

import com.xaviervinicius.labschedule.models.UserModel.UserModel;
import com.xaviervinicius.labschedule.models.labModel.LabModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "schedule_tb")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ScheduleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "schedule_id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scheduler_id")
    private UserModel scheduler;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lab_id")
    private LabModel lab;

    @Column(nullable = false)
    private Instant start;

    @Column(nullable = false)
    private Instant end;

    @CreationTimestamp
    private Instant createdAt;

    public ScheduleModel(UUID id){
        this.id = id;
    }
}
