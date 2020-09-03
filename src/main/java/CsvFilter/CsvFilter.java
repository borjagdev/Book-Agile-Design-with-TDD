package CsvFilter;

import java.util.ArrayList;
import java.util.List;

public class CsvFilter {

    private static final int LIST_PRICE_FIELD_INDEX = 2;
    private static final int NET_PRICE_INDEX = 3;
    private static final int IVA_FIELD_INDEX = 4;
    private static final int IGIC_FIELD_INDEX = 5;

    private CsvFilter() {
    }

    public static List<String> filter(List<String> originalContent) {
        List<String> filteredContent = new ArrayList<>();
        filteredContent.add(originalContent.get(0));
        String[] invoiceElements = originalContent.get(1).split(",");
        String listPriceField = invoiceElements[LIST_PRICE_FIELD_INDEX];
        String netPriceField = invoiceElements[NET_PRICE_INDEX];
        String ivaField = invoiceElements[IVA_FIELD_INDEX];
        String igicField = invoiceElements[IGIC_FIELD_INDEX];
        if (taxFieldsAreMutuallyExclusive(ivaField, igicField) &&
                netPriceIsCorrect(listPriceField, netPriceField, getAppliedTax(ivaField, igicField))) {
            filteredContent.add(originalContent.get(1));
        }
        return filteredContent;
    }

    private static boolean taxFieldsAreMutuallyExclusive(String ivaField, String igicField) {
        final String NUMERIC_REGEX = "-?\\d+(\\.\\d+)?";
        return (ivaField.isEmpty() || igicField.isEmpty()) &&
                (ivaField.matches(NUMERIC_REGEX) || igicField.matches(NUMERIC_REGEX));
    }

    private static boolean netPriceIsCorrect(String listPriceField, String netPriceField, String appliedTax) {
        int listPrice = Integer.parseInt(listPriceField);
        int netPrice = Integer.parseInt(netPriceField);
        int tax = Integer.parseInt(appliedTax);
        return netPrice == listPrice + (listPrice * tax / 100);
    }

    private static String getAppliedTax(String ivaField, String igicField) {
        return !ivaField.isEmpty() ? ivaField : igicField;
    }

}
