package com.driving.controller;

import com.driving.controller.mapper.AssigmentMapper;
import com.driving.datatransferobject.AssigmentDTO;
import com.driving.domainobject.AssigmentDO;
import com.driving.exception.EntityNotFoundException;
import com.driving.service.car.AssigmentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;

@RestController
@RequestMapping("/v1/assigment")
public class AssigmentController {

    private final AssigmentMapper assigmentMapper;
    private final AssigmentService assigmentService;

    public AssigmentController(AssigmentMapper assigmentMapper,
                               AssigmentService assigmentService) {
        this.assigmentMapper = assigmentMapper;
        this.assigmentService = assigmentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AssigmentDTO createAssigment(@RequestBody AssigmentDTO assigmentDTO) {
        AssigmentDO assigmentDO = assigmentMapper.toAssigmentDO(assigmentDTO);

        return assigmentMapper.toAssigmentDTO(assigmentService.createAssigment(assigmentDO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void finishAssigment(@PathVariable Long id) {
        assigmentService.finishAssigment(id);
    }

    @ExceptionHandler({
            ConstraintViolationException.class,
            EntityNotFoundException.class})
    public void handleException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.CONFLICT.value());
    }
}
