package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestData {

    final static String headerLine = "Num_factura, Fecha, Bruto, Neto, IVA, IGIC, Concepto, CIF_cliente, NIF_cliente";

    public static List<String> emptyInvoiceList() {
        return Arrays.asList(headerLine);
    }

    public static List<String> oneValidLine() {
        String invoiceLine = "1,02/05/2019,1000,1190,19,,ACER Laptop,B76430134,";
        return Arrays.asList(headerLine, invoiceLine);
    }

    public static List<String> fileWithNoHeader() {
        String invoiceLine = "1,02/05/2019,1000,1190,19,,ACER Laptop,B76430134,";
        return Arrays.asList(invoiceLine, invoiceLine);
    }

    public static List<String> lineWithInvalidTaxFieldsBeing(String iva, String igic) {
        String invoiceLine = "1,02/05/2019,1000,1190," +
                iva + "," + igic +
                ",ACER Laptop,B76430134,";
        return Arrays.asList(headerLine, invoiceLine);
    }

    public static List<String> lineWithWrongNetPriceField() {
        String invoiceLine = "1,02/05/2019,1000,810,,19,ACER Laptop,B76430134,";
        return Arrays.asList(headerLine, invoiceLine);
    }

    public static List<String> lineWithInvalidIdFieldsBeing(String cif, String nif) {
        String invoiceLine = "1,02/05/2019,1000,1190,19,,ACER Laptop," +
                cif + "," + nif;
        return Arrays.asList(headerLine, invoiceLine);
    }

    public static List<String> multilineValidFile() {
        ArrayList<String> fileContent = new ArrayList<>();
        fileContent.add(headerLine);
        for(int i = 1; i < 5; i++) {
            fileContent.add(oneValidLineWithInvoiceNumber(i));
        }
        return fileContent;
    }

    private static String oneValidLineWithInvoiceNumber(int invoiceNumber) {
        return invoiceNumber + ",02/05/2019,1000,1190,19,,ACER Laptop,B76430134,";
    }

}
