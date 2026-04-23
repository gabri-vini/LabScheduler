package com.xaviervinicius.labschedule.services;

import com.xaviervinicius.labschedule.dto.CreateLabDto;
import com.xaviervinicius.labschedule.models.labModel.LabModel;
import com.xaviervinicius.labschedule.models.labModel.LabState;
import com.xaviervinicius.labschedule.repository.LabRepository.LabRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LabService {
    private final LabRepository labRepository;

    @Transactional
    public LabModel createLab(@NonNull CreateLabDto data){
        LabModel lab = new LabModel();
        BeanUtils.copyProperties(data, lab);
        return labRepository.save(lab);
    }

    public List<LabModel> getAvailableLabs(){
        return labRepository.findAllByState(LabState.AVAILABLE);
    }
}
