package com.xaviervinicius.labschedule.repository.LabRepository;

import com.xaviervinicius.labschedule.models.labModel.LabModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabRepository extends JpaRepository<LabModel,Long> {
}
