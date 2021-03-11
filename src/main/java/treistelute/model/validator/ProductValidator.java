package treistelute.model.validator;

import treistelute.model.Product;

public class ProductValidator implements Validator<Product> {
    @Override
    public void validate(Product product) throws ValidationException {
        if(product.getProductId() < 0)
            throw new ValidationException("invalid id");
        if(product.getName() == null || product.getName().equals(""))
            throw new ValidationException("name cannot be empty");
    }
}
