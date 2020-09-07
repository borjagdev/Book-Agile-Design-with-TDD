package CsvFilter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CsvFilter {

    private static final int LIST_PRICE_FIELD_INDEX = 2;
    private static final int NET_PRICE_INDEX = 3;
    private static final int IVA_FIELD_INDEX = 4;
    private static final int IGIC_FIELD_INDEX = 5;
    private static final int CIF_FIELD_INDEX = 7;
    private static final int NIF_FIELD_INDEX = 8;

    private CsvFilter() {
    }

    public static List<String> filter(List<String> originalContent) {
        if (fileHeaderIsNotValid(originalContent)) {
            throw new HeaderNotPresentException("Invalid file header");
        }
        List<String> filteredContent = new ArrayList<>();
        filteredContent.add(originalContent.get(0));
        for (int i = 1; i < originalContent.size(); i++) {
            String[] invoiceElements = originalContent.get(i).split(",");
            if (invoiceHasEnoughPopulatedFields(invoiceElements)) {
                String listPriceField = invoiceElements[LIST_PRICE_FIELD_INDEX];
                String netPriceField = invoiceElements[NET_PRICE_INDEX];
                String ivaField = invoiceElements[IVA_FIELD_INDEX];
                String igicField = invoiceElements[IGIC_FIELD_INDEX];
                String cifField = "";
                if (cifFieldIsNotEmpty(invoiceElements)) {
                    cifField = invoiceElements[CIF_FIELD_INDEX];
                }
                String nifField = "";
                if (nifFieldIsNotEmpty(invoiceElements)) {
                    nifField = invoiceElements[NIF_FIELD_INDEX];
                }
                if (taxFieldsAreMutuallyExclusive(ivaField, igicField) &&
                        netPriceIsCorrect(listPriceField, netPriceField, getAppliedTax(ivaField, igicField)) &&
                        idFieldsAreMutuallyExclusive(cifField, nifField)) {
                    filteredContent.add(originalContent.get(i));
                }
            }
        }
        return filteredContent;
    }

    private static boolean fileHeaderIsNotValid(List<String> originalContent) {
        final String HEADER_FORMAT = "Num_factura, Fecha, Bruto, Neto, IVA, IGIC, Concepto, CIF_cliente, NIF_cliente";
        return (originalContent.size() < 2) || (!originalContent.get(0).equals(HEADER_FORMAT));
    }

    private static boolean invoiceHasEnoughPopulatedFields(String[] invoiceElements) {
        return invoiceElements.length >= 7;
    }

    private static boolean cifFieldIsNotEmpty(String[] invoiceElements) {
        return invoiceElements.length >= CIF_FIELD_INDEX + 1;
    }

    private static boolean nifFieldIsNotEmpty(String[] invoiceElements) {
        return invoiceElements.length == NIF_FIELD_INDEX + 1;
    }

    private static boolean taxFieldsAreMutuallyExclusive(String ivaField, String igicField) {
        final String NUMERIC_REGEX = "-?\\d+(\\.\\d+)?";
        return (ivaField.isEmpty() || igicField.isEmpty()) &&
                (ivaField.matches(NUMERIC_REGEX) || igicField.matches(NUMERIC_REGEX));
    }

    private static boolean netPriceIsCorrect(String listPriceField, String netPriceField, String appliedTax) {
        BigDecimal listPrice = new BigDecimal(listPriceField);
        BigDecimal netPrice = new BigDecimal(netPriceField);
        BigDecimal tax = new BigDecimal(appliedTax);
        return netPrice.equals(listPrice.add(listPrice.multiply(tax).divide(new BigDecimal(100))));
    }

    private static String getAppliedTax(String ivaField, String igicField) {
        return !ivaField.isEmpty() ? ivaField : igicField;
    }

    private static boolean idFieldsAreMutuallyExclusive(String cifField, String nifField) {
        final String CIF_REGEX = "^[a-zA-Z]{1}\\d{7}[a-zA-Z0-9]{1}$";
        final String NIF_REGEX = "^\\d{8}[a-zA-Z]{1}$";
        return (cifField.isEmpty() || nifField.isEmpty()) &&
                (cifField.matches(CIF_REGEX) || nifField.matches(NIF_REGEX));
    }

}
