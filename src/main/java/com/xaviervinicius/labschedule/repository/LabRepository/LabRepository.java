package com.xaviervinicius.labschedule.repository.LabRepository;

import com.xaviervinicius.labschedule.models.labModel.LabModel;
import com.xaviervinicius.labschedule.models.labModel.LabState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabRepository extends JpaRepository<LabModel,Long> {
    List<LabModel> findAllByState(LabState state);
}
