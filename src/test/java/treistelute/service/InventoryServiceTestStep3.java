package treistelute.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import treistelute.model.Product;
import treistelute.repository.InventoryRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InventoryServiceTestStep3 {

    @Mock
    InventoryRepository inventoryRepository;

    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        inventoryService = new InventoryService(inventoryRepository);
    }

    @Test
    void lookupProduct_success(){
        Product product = new Product(1,"product_test_step3",10.0,2,1,3,null);
        inventoryService.addProduct(product);
        assertEquals(product.getName(),inventoryService.lookupProduct("product_test_step3").getName());
    }

    @Test
    void addPart_fail_invalidPrice(){
        String expected_exception = "The price must be greater than 0. ";
        try{
            inventoryService.addInhousePart("part",0.0,2,1,3,11);
        }catch (Exception e){
            assertEquals(expected_exception,e.getMessage());
        }
    }

    @Test
    void addPart_success() throws Exception {
        int initial_size = inventoryService.getAllParts().size();
        inventoryService.addInhousePart("part",1.0,2,1,3,11);

        assertEquals(initial_size + 1, inventoryService.getAllParts().size());
    }
}