package com.xaviervinicius.labschedule.repository.SchedulerRepositoy;

import com.xaviervinicius.labschedule.models.labModel.LabModel;
import com.xaviervinicius.labschedule.models.labModel.LabState;
import com.xaviervinicius.labschedule.models.scheduleModel.ScheduleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;


@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleModel, UUID> {
    @Query(value = """
        SELECT
            CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END
        FROM ScheduleModel s
        WHERE
            s.lab.id = :labId AND
            s.start >= :start AND
            s.end <= :end AND
           s.date = :date
    """)
    boolean thereIsConflict(@Param("labId") Long labId, @Param("start") LocalTime start, @Param("end") LocalTime end, @Param("date") LocalDate date);


    @Query("""
        SELECT s FROM ScheduleModel s
        LEFT JOIN FETCH s.lab
        WHERE
            s.lab.state = :state AND
            s.scheduler IS NULL
    """)
    List<ScheduleModel> findAllAvailableWithLabLoaded(@Param("state") LabState labState);

    LabState Lab(LabModel lab);
}
