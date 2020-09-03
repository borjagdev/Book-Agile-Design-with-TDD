package CsvFilter;

import java.util.ArrayList;
import java.util.List;

public class CsvFilter {
    public static List<String> filter(List<String> originalContent) {
        List<String> filteredContent = new ArrayList<>();
        filteredContent.add(originalContent.get(0));
        String[] invoiceElements = originalContent.get(1).split(",");
        if ((invoiceElements[4].isEmpty() || invoiceElements[5].isEmpty()) &&
                !(invoiceElements[4].isEmpty() && invoiceElements[5].isEmpty())) {
            filteredContent.add(originalContent.get(1));
        }
        return filteredContent;
    }
}
