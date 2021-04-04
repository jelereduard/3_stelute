package treistelute.service;

import org.junit.jupiter.api.*;
import treistelute.repository.InventoryRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        inventoryService.addInhousePart("BVA1",1.1,1,0,1,132);
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
            inventoryService.addInhousePart("fail1",10.0,2,-1,10,132);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            assert(ex.getMessage().equals("The Min value must be at least 0. "));
        }
    }
}