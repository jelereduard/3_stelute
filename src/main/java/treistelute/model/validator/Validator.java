package treistelute.model.validator;

public interface Validator<E> {
    void validate(E e) throws ValidationException;
}
