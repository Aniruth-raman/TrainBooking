package com.cloudbees.trainbooking;

import com.cloudbees.trainbooking.exception.NoSeatFoundException;
import com.cloudbees.trainbooking.model.Receipt;
import com.cloudbees.trainbooking.model.Seat;
import com.cloudbees.trainbooking.model.User;
import com.cloudbees.trainbooking.repository.ReceiptRepository;
import com.cloudbees.trainbooking.repository.SeatRepository;
import com.cloudbees.trainbooking.repository.UserRepository;
import com.cloudbees.trainbooking.service.PurchaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import com.cloudbees.trainbooking.model.Section;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PurchaseServiceTest {

    @Mock
    private ReceiptRepository receiptRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private PurchaseService purchaseService;

    @Test
    public void testPurchaseTicket_success() {
        // Set up mocks
        Seat availableSeat = new Seat(Section.A, "123");
        availableSeat.setAvailable(true);
        when(seatRepository.findFirstByAvailableIsTrue()).thenReturn(availableSeat);

        User user = new User("John", "Doe","jdoe@temp-mail.com");
        when(userRepository.save(user)).thenReturn(user);

        BigDecimal cost = new BigDecimal("100.00");

        // Call the method under test
        Receipt receipt = purchaseService.purchaseTicket(user, "Chennai", "Bangalore", cost);

        // Assertions
        assertNotNull(receipt);
        assertEquals("Chennai", receipt.getFrom());
        assertEquals("Bangalore", receipt.getTo());
        assertEquals(user, receipt.getUser());
        assertEquals(cost, receipt.getPricePaid());
        assertEquals(availableSeat, receipt.getSeat());

        verify(seatRepository).save(availableSeat);
        verify(receiptRepository).save(receipt);
    }

    @Test(expected = NoSeatFoundException.class)
    public void testPurchaseTicket_noSeatsAvailable() {
        // Set up mocks
        when(seatRepository.findFirstByAvailableIsTrue()).thenReturn(null);

        // Call the method under test
        purchaseService.purchaseTicket(null, "Chennai", "Bangalore", new BigDecimal("100.00"));
    }
}
