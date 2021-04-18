package treistelute.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import treistelute.model.InhousePart;
import treistelute.repository.InventoryRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class InventoryServiceTestStep2 {

    private InventoryRepository inventoryRepository;

    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        inventoryRepository = new InventoryRepository();
        inventoryService = new InventoryService(inventoryRepository);
    }

    @Test
    void addPart_success(){
        InhousePart part = mock(InhousePart.class);
        Mockito.when(part.getPartId()).thenReturn(1);
        Mockito.when(part.getName()).thenReturn("part_mock");
        Mockito.when(part.getPrice()).thenReturn(10.0);
        Mockito.when(part.getInStock()).thenReturn(2);
        Mockito.when(part.getMin()).thenReturn(1);
        Mockito.when(part.getMax()).thenReturn(3);
        Mockito.when(part.getMachineId()).thenReturn(11);

        try {
            inventoryService.addInhousePart(part);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        assertEquals(part.getName(),inventoryService.lookupPart("part_mock").getName());
    }

    @Test
    void addPart_fail(){
        InhousePart part = mock(InhousePart.class);
        Mockito.when(part.getPartId()).thenReturn(1);
        Mockito.when(part.getName()).thenReturn("");
        Mockito.when(part.getPrice()).thenReturn(0.0);
        Mockito.when(part.getInStock()).thenReturn(4);
        Mockito.when(part.getMin()).thenReturn(5);
        Mockito.when(part.getMax()).thenReturn(3);
        Mockito.when(part.getMachineId()).thenReturn(11);

        String expected_exception = "A name has not been entered. The price must be greater than 0. The Min value must be less than the Max value. Inventory level is lower than minimum value. Inventory level is higher than the maximum value. ";
        try {
            inventoryService.addInhousePart(part);
        }catch (Exception e){
            assertEquals(expected_exception,e.getMessage());
        }


    }

}