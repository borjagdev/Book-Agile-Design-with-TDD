package CsvFilter;

import java.util.ArrayList;
import java.util.List;

public class CsvFilter {

    private static final int IVA_FIELD_INDEX = 4;
    private static final int IGIC_FIELD_INDEX = 5;

    private CsvFilter() {}

    public static List<String> filter(List<String> originalContent) {
        List<String> filteredContent = new ArrayList<>();
        filteredContent.add(originalContent.get(0));
        String[] invoiceElements = originalContent.get(1).split(",");
        String ivaField = invoiceElements[IVA_FIELD_INDEX];
        String igicField = invoiceElements[IGIC_FIELD_INDEX];
        if (taxFieldsAreMutuallyExclusive(ivaField, igicField)) {
            filteredContent.add(originalContent.get(1));
        }
        return filteredContent;
    }

    private static boolean taxFieldsAreMutuallyExclusive(String ivaField, String igicField) {
        final String NUMERIC_REGEX = "-?\\d+(\\.\\d+)?";
        return (ivaField.matches(NUMERIC_REGEX) || igicField.matches(NUMERIC_REGEX)) &&
                !(ivaField.matches(NUMERIC_REGEX) && igicField.matches(NUMERIC_REGEX));
    }

}
