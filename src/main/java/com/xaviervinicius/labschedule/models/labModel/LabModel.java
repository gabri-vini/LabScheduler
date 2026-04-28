package com.xaviervinicius.labschedule.models.labModel;

import com.xaviervinicius.labschedule.models.scheduleModel.ScheduleModel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lab_tb")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class LabModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lab_model_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LabState state;

    @OneToMany(mappedBy = "lab", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ScheduleModel> schedules = new ArrayList<>();

    @Column
    private Instant operationReturnTime;

    @CreationTimestamp
    private Instant createdAt;

    public boolean canSchedule(){
        return this.state == LabState.AVAILABLE;
    }

    public LabModel(Long id){
        this.id = id;
    }
}
