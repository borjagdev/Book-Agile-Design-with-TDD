package CsvFilter;

import java.util.ArrayList;
import java.util.List;

public class CsvFilter {

    private CsvFilter() {
    }

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
        final int IVA_FIELD_INDEX = 4;
        final int IGIC_FIELD_INDEX = 5;
        final String NUMERIC_REGEX = "-?\\d+(\\.\\d+)?";
        return (invoiceElements[IVA_FIELD_INDEX].matches(NUMERIC_REGEX) ||
                invoiceElements[IGIC_FIELD_INDEX].matches(NUMERIC_REGEX)) &&
                !(invoiceElements[IVA_FIELD_INDEX].matches(NUMERIC_REGEX) &&
                        invoiceElements[IGIC_FIELD_INDEX].matches(NUMERIC_REGEX));
    }

}
