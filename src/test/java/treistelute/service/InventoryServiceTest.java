package treistelute.service;

import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;
import treistelute.model.Inventory;
import treistelute.model.Part;
import treistelute.model.Product;
import treistelute.repository.InventoryRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayNameGeneration(DisplayNameGenerator.Standard.class)
class InventoryServiceTest {

    private InventoryRepository inventoryRepository;
    private InventoryService inventoryService;

    @BeforeEach
    void setUp(){
        inventoryRepository = new InventoryRepository();
        inventoryService = new InventoryService(inventoryRepository);
    }

    @Test
    void addInhousePart() throws Exception {
        addInhousePart_BVA_success1();
        addInhousePart_ECP_success1();
        addInhousePart_MinIsGreaterThanMax_fail();
        addInhousePart_MinIsLessThanZero_fail();
        addInhousePart_PriceIsZero_fail();
        addInhousePart_StockIsLessThanMin_fail();
    }



    @Test
    @DisplayName("Adaugare ECP Valid 1")
    @Order(1)
    void addInhousePart_ECP_success1() throws Exception {
        int size = inventoryRepository.getAllParts().size();
        inventoryService.addInhousePart("part1",12.0,90,90,150,12321);
        assert(size+1 == inventoryRepository.getAllParts().size());
    }

    @Test
    @DisplayName("Adaugare ECP Valid 2")
    @Order(3)
    void addInhousePart_ECP_success2() throws Exception {
        int size = inventoryRepository.getAllParts().size();
        inventoryService.addInhousePart("2",1.1,999,0,999,2);
        assertEquals(size+1, inventoryRepository.getAllParts().size());
    }

    @Test
    @DisplayName("Adaugare ECP Non Valid 1")
    @Order(2)
    void addInhousePart_PriceIsZero_fail(){
        try {
            inventoryService.addInhousePart("fail1",0.0,1,0,1,1);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            assert(ex.getMessage().equals("The price must be greater than 0. "));
        }
    }

    //ECP Non Valid 2
    @Test
    @Tag("Tag")
    void addInhousePart_StockIsLessThanMin_fail(){
        try {
            inventoryService.addInhousePart("fail2",10.10,1,2,5,123);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            assert(ex.getMessage().equals("Inventory level is lower than minimum value. "));
        }
    }

    @Test
    @DisplayName("Adaugare BVA Non Valid 1")
    void addInhousePart_MinIsGreaterThanMax_fail(){
        try {
            inventoryService.addInhousePart("fail1",10.0,2,2,1,132);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            assert(ex.getMessage().equals("The Min value must be less than the Max value. Inventory level is higher than the maximum value. "));
        }
    }

    @Test
    @DisplayName("Adaugare BVA Valid 1")
    void addInhousePart_BVA_success1() throws Exception {
        int size = inventoryRepository.getAllParts().size();
        inventoryService.addInhousePart("BVA1",0.1,100,10,111,132);
        assert(size+1 == inventoryRepository.getAllParts().size());
    }

    @Test
    @DisplayName("Adaugare BVA Valid 2")
    void addInhousePart_BVA_success2() throws Exception {
        int size = inventoryRepository.getAllParts().size();
        inventoryService.addInhousePart("BVA2",1.1,1,0,1,132);
        assert(size+1 == inventoryRepository.getAllParts().size());
    }

    @Disabled
    @Test
    @DisplayName("Adaugare BVA Valid 2")
    void addInhousePart_BVA_success3() throws Exception {
        int size = inventoryRepository.getAllParts().size();
        inventoryService.addInhousePart("BVA1",1.1,1,0,1,132);
        assert(size+1 == inventoryRepository.getAllParts().size());
    }

    @Test
    @DisplayName("Adaugare BVA Non Valid 2")
    void addInhousePart_MinIsLessThanZero_fail(){
        try {
            inventoryService.addInhousePart("fail2",10.0,2,-1,10,132);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            assert(ex.getMessage().equals("The Min value must be at least 0. "));
        }
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