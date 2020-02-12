package com.freenow.service.car;

import com.freenow.dataaccessobject.AssigmentRepository;
import com.freenow.domainobject.AssigmentDO;
import com.freenow.domainvalue.AssigmentStatus;
import com.freenow.exception.AssigmentNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@DisplayName("When an assigment is being finished")
class AssigmentServiceFinishTest {

    private final AssigmentRepository assigmentRepository = mock(AssigmentRepository.class);

    private final AssigmentService assigmentService = new AssigmentService(assigmentRepository);

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("it should be updated when present")
    void shouldFinishExistentAssigment() {
        //Given
        AssigmentDO assigment = AssigmentDO.builder()
                .id(1L)
                .build();

        when(assigmentRepository.findByIdAndStatus(1L, AssigmentStatus.ACTIVE))
                .thenReturn(Optional.of(assigment));

        when(assigmentRepository.save(assigment))
                .thenReturn(assigment);

        //When
        assigmentService.finishAssigment(1L);

        //then
        verify(assigmentRepository).save(assigment);
        assertThat(assigment.getStatus()).isEqualTo(AssigmentStatus.FINISHED);
    }

    @Test
    @DisplayName("it should throw an exception when absent")
    void throwExceptionWhenAbsent() {
        //Given
        when(assigmentRepository.findByIdAndStatus(1L, AssigmentStatus.ACTIVE))
                .thenReturn(Optional.empty());

        //When
        assertThatThrownBy(() ->
                assigmentService.finishAssigment(1L))
                .isInstanceOf(AssigmentNotFoundException.class);

        //then
        verify(assigmentRepository, never()).save(any());
    }
}