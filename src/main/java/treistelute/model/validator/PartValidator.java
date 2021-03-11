package treistelute.model.validator;

import treistelute.model.Part;

public class PartValidator implements Validator<Part> {
    @Override
    public void validate(Part part) throws ValidationException {
        if(part.getPartId() < 0)
            throw new ValidationException("invalid id");
        if(part.getName() == null || part.getName().equals(""))
            throw new ValidationException("name cannot be empty");
    }
}
