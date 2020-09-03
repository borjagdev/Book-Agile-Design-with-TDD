import CsvFilter.CsvFilter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/* TODO:
 - A file with a single invoice where everything is correct should output the same line
 - A file with a single invoice where IVA and IGIC (exclusive taxes) are filled should delete the line
 - A file with a single invoice where the net price is wrong calculated should delete the line
 - A file with a single invoice where CIF and NIF are filled should delete the line
 - A file with just 1 line is not valid because it has no header
 - If the invoice number is repeated, all the lines where it appears should be deleted
 - An empty or null list should output an empty list
*/

class CsvFilterShould {

    String headerLine = "Num_factura, Fecha, Bruto, Neto, IVA, IGIC, Concepto, CIF_cliente, NIF_cliente";

    @Test
    void allow_for_correct_lines_only() {
        String invoiceLine = "1,02/05/2019,1000,1190,19,,ACER Laptop,B76430134,";
        List<String> csvContent = Arrays.asList(headerLine, invoiceLine);

        List<String> result = CsvFilter.filter(csvContent);

        assertThat(result).isEqualTo(csvContent);
    }

    @Test
    void exclude_lines_with_both_tax_fields_populated_as_they_are_exclusive() {
        String invoiceLine = "1,02/05/2019,1000,810,19,8,ACER Laptop,B76430134,";

        List<String> result = CsvFilter.filter(Arrays.asList(headerLine, invoiceLine));

        assertThat(result).isEqualTo(Arrays.asList(headerLine));
    }

    @Test
    void exclude_lines_with_both_tax_fields_empty_as_one_is_required() {
        String invoiceLine = "1,02/05/2019,1000,810,,,ACER Laptop,B76430134,";

        List<String> result = CsvFilter.filter(Arrays.asList(headerLine, invoiceLine));

        assertThat(result).isEqualTo(Arrays.asList(headerLine));
    }

    @Test
    void exclude_lines_with_non_decimal_tax_fields() {
        String invoiceLine = "1,02/05/2019,1000,810,NON_DECIMAL,,ACER Laptop,B76430134,";

        List<String> result = CsvFilter.filter(Arrays.asList(headerLine, invoiceLine));

        assertThat(result).isEqualTo(Arrays.asList(headerLine));
    }

    @Test
    void exclude_lines_with_both_tax_fields_populated_even_if_non_decimal() {
        String invoiceLine = "1,02/05/2019,1000,810,XYZ,12,ACER Laptop,B76430134,";

        List<String> result = CsvFilter.filter(Arrays.asList(headerLine, invoiceLine));

        assertThat(result).isEqualTo(Arrays.asList(headerLine));
    }

    @Test
    void exclude_lines_with_the_net_price_wrongly_calculated () {
        String invoiceLine = "1,02/05/2019,1000,1190,,19,ACER Laptop,B76430134,";
        List<String> csvContent = Arrays.asList(headerLine, invoiceLine);

        List<String> result = CsvFilter.filter(csvContent);

        assertThat(result).isEqualTo(csvContent);
    }

}
