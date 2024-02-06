package com.cloudbees.trainbooking;

import com.cloudbees.trainbooking.model.Receipt;
import com.cloudbees.trainbooking.repository.ReceiptRepository;
import com.cloudbees.trainbooking.service.ReceiptService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReceiptServiceTest {

    @Mock
    private ReceiptRepository receiptRepository;

    @InjectMocks
    private ReceiptService receiptService;

    @Test
    public void testGetReceiptById_success() {
        // Set up mocks
        Long id = 1L;
        Receipt receipt = new Receipt();
        when(receiptRepository.findById(id)).thenReturn(Optional.of(receipt));

        // Call the method under test
        Receipt retrievedReceipt = receiptService.getReceiptById(id);

        // Assertions
        assertNotNull(retrievedReceipt);
        assertEquals(receipt, retrievedReceipt);

        verify(receiptRepository).findById(id);
    }

    @Test
    public void testGetReceiptById_notFound() {
        // Set up mocks
        Long id = 1L;
        when(receiptRepository.findById(id)).thenReturn(Optional.empty());

        // Call the method under test
        Receipt retrievedReceipt = receiptService.getReceiptById(id);

        // Assertions
        assertNull(retrievedReceipt);

        verify(receiptRepository).findById(id);
    }

}

