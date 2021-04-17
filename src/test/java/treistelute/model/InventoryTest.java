package treistelute.model;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import treistelute.repository.InventoryRepository;
import treistelute.service.InventoryService;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {
    private InventoryRepository inventoryRepository;

    @BeforeEach
    void setUp(){
        inventoryRepository = new InventoryRepository();
    }

    @Test
    @DisplayName("Cautarea unui produs")
    void lookupProduct(){
        lookupProduct_success();
        lookupProduct_empty_name();
        lookupProduct_no_such_product();
        lookupProduct_empty_inventory();
    }

    @Test
    @DisplayName("Cautarea unui produs cand inventarul este gol")
    void lookupProduct_empty_inventory(){
        String name = "product2";
        Inventory inventory = new Inventory();
        Product product = inventory.lookupProduct(name);
        if(product != null)
            System.out.println(product);
        else
            System.out.println("No product was found");
        Product expected_product = new Product(0,null,0.0,0,0,0,null);
        assertEquals(expected_product.getName(),product.getName());
        assertEquals(expected_product.getAssociatedParts(),product.getAssociatedParts());
        assertEquals(expected_product.getInStock(),product.getInStock());
        assertEquals(expected_product.getMax(),product.getMax());
        assertEquals(expected_product.getMin(),product.getMin());
        assertEquals(expected_product.getPrice(),product.getPrice());
    }

    @Test
    @DisplayName("Cautarea unui produs cand numele dat este gol")
    void lookupProduct_empty_name(){
        String name = "";
        Inventory inventory = new Inventory();
        Product product = inventory.lookupProduct(name);
        if(product != null)
            System.out.println(product);
        else
            System.out.println("No product was found");
        assertNull(product);
    }

    @Test
    @DisplayName("Cautarea unui produs (succes)")
    void lookupProduct_success(){
        String name = "product2";
        Inventory inventory = inventoryRepository.getInventory();
        Product product = inventory.lookupProduct(name);
        if(product != null)
            System.out.println(product);
        else
            System.out.println("No product was found");
        ObservableList<Part> parts = product.getAssociatedParts();
        Product expected_product = new Product(2,"product2",12.34,12,1,12,parts);
        assertEquals(expected_product.getName(),product.getName());
        assertEquals(expected_product.getAssociatedParts(),product.getAssociatedParts());
        assertEquals(expected_product.getInStock(),product.getInStock());
        assertEquals(expected_product.getMax(),product.getMax());
        assertEquals(expected_product.getMin(),product.getMin());
        assertEquals(expected_product.getPrice(),product.getPrice());
    }

    @Test
    @DisplayName("Cautarea unui produs care nu exista")
    void lookupProduct_no_such_product(){
        String name = "produscarenuexista";
        Inventory inventory = inventoryRepository.getInventory();
        Product product = inventory.lookupProduct(name);
        if(product != null)
            System.out.println(product);
        else
            System.out.println("No product was found");
        assertNull(product);
    }
}