package CsvFilter;

import java.util.ArrayList;
import java.util.List;

public class CsvFilter {

    private static final Integer IVA_FIELD_INDEX = 4;
    private static final Integer IGIC_FIELD_INDEX = 5;

    private CsvFilter() {}

    public static List<String> filter(List<String> originalContent) {
        List<String> filteredContent = new ArrayList<>();
        filteredContent.add(originalContent.get(0));
        String[] invoiceElements = originalContent.get(1).split(",");
        if (taxFieldsAreMutuallyExclusive(invoiceElements)) {
            filteredContent.add(originalContent.get(1));
        }
        return filteredContent;
    }

    private static boolean taxFieldsAreMutuallyExclusive(String[] invoiceElements) {
        return (invoiceElements[IVA_FIELD_INDEX].isEmpty() || invoiceElements[IGIC_FIELD_INDEX].isEmpty()) &&
                !(invoiceElements[IVA_FIELD_INDEX].isEmpty() && invoiceElements[IGIC_FIELD_INDEX].isEmpty());
    }

}
