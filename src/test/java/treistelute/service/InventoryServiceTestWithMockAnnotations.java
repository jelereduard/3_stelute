package treistelute.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import treistelute.model.InhousePart;
import treistelute.model.Part;
import treistelute.model.Product;
import treistelute.repository.InventoryRepository;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class InventoryServiceTestWithMockAnnotations {

    @Mock
    InventoryRepository inventoryRepository;

    @InjectMocks
    InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void addInhousePart_success() {
        InhousePart part = new InhousePart(1,"part",1.0,2,1,3,11);
        Mockito.when(inventoryRepository.getAllParts()).thenReturn(FXCollections.observableArrayList(part));
        try {
            inventoryService.addInhousePart(part);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        assertEquals(1, inventoryService.getAllParts().size());
    }

    @Test
    void lookupProduct(){
        Product product = new Product(1,"product",10.0,2,1,3,null);
        Mockito.when(inventoryRepository.lookupProduct(product.getName())).thenReturn(product);
        Mockito.when(inventoryRepository.getAllProducts()).thenReturn(FXCollections.observableArrayList(product));
        inventoryService.addProduct(product);

        assertEquals(1, inventoryService.getAllProducts().size());
        assertEquals(product,inventoryService.lookupProduct(product.getName()));

    }

    @Test
    void getAllProducts_empty(){
        Mockito.when(inventoryRepository.getAllProducts()).thenReturn(FXCollections.observableArrayList());
        assertEquals(0,inventoryService.getAllProducts().size());
        Mockito.verify(inventoryRepository,times(1)).getAllProducts();
    }

    @Test
    void getAllParts_empty(){
        Mockito.when(inventoryRepository.getAllParts()).thenReturn(FXCollections.observableArrayList());
        assertEquals(0,inventoryService.getAllParts().size());
        Mockito.verify(inventoryRepository,times(1)).getAllParts();


    }
}