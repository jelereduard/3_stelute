package treistelute.model;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PartTest {
    private Part part;

    void setUp(){
        part = new InhousePart(1,"surub",20.0,20,10,30,111);
    }

    @Test
    void getPrice() {
        setUp();
        assert (part.getPrice() > 1.0);
        assert (part.getPrice() < 99999.0);
    }

    @Test
    void getInStock() {
        assert (part.getInStock() > part.getMin());
        assert (part.getInStock() < part.getMax());
    }

    @Test
    void getMin() {
        assert (part.getMin() > 1);
        assert (part.getMin() < part.getMax());
    }

    @Test
    void getMax() {
        assert (part.getMax() > part.getMin());
        assert (part.getMax() < 9999999);
    }
}