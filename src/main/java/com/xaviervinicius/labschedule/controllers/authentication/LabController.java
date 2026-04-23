package com.xaviervinicius.labschedule.controllers.authentication;

import com.xaviervinicius.labschedule.dto.CreateLabDto;
import com.xaviervinicius.labschedule.dto.mappers.LabMapper;
import com.xaviervinicius.labschedule.dto.responses.CreateLabResponse;
import com.xaviervinicius.labschedule.services.LabService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/lab-scheduler/labs")
public class LabController {
    private final LabService labService;
    private final LabMapper mapper;

    @PostMapping("/create")
    public ResponseEntity<CreateLabResponse> createLab(@RequestBody @Valid CreateLabDto data){
        return ResponseEntity.ok(new CreateLabResponse(mapper.map(labService.createLab(data))));
    }
}
