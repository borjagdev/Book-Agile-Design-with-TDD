package CsvFilter;

public class HeaderNotPresentException extends RuntimeException{
    public HeaderNotPresentException(String message) {
        super(message);
    }
}
