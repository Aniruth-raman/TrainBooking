package com.cloudbees.trainbooking;

import com.cloudbees.trainbooking.model.Seat;
import com.cloudbees.trainbooking.model.Section;
import com.cloudbees.trainbooking.repository.ReceiptRepository;
import com.cloudbees.trainbooking.repository.SeatRepository;
import com.cloudbees.trainbooking.service.SeatService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SeatServiceTest {

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private ReceiptRepository receiptRepository;

    @InjectMocks
    private SeatService seatService;

    @Test
    public void getSeatsBySection_emptyResult() {
        // Set up mocks
        Section section = Section.A;
        when(seatRepository.findBySection(section)).thenReturn(Collections.emptyList());

        // Call the method under test
        List<Seat> seats = seatService.getSeatsBySection(section);

        // Assertions
        assertTrue(seats.isEmpty());
        verify(seatRepository).findBySection(section);
    }

    @Test
    public void getSeatById_success() {
        Long id = 1L;
        Seat seat = new Seat(Section.A, "123");
        seat.setAvailable(true); // Check for seat availability
        when(seatRepository.findById(id)).thenReturn(Optional.of(seat));

        Seat retrievedSeat = seatService.getSeatById(id);

        assertNotNull(retrievedSeat);
        assertEquals(seat, retrievedSeat);
        verify(seatRepository).findById(id);
    }

    @Test
    public void getSeatById_notFound() {
        Long id = 1L;
        when(seatRepository.findById(id)).thenReturn(Optional.empty());

        Seat retrievedSeat = seatService.getSeatById(id);

        assertNull(retrievedSeat);
        verify(seatRepository).findById(id);
    }
}
