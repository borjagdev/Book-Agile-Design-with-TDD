import CsvFilter.CsvFilter;
import CsvFilter.HeaderNotPresentException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static util.TestData.*;

/* TODO:
 - A file with a single invoice where everything is correct should output the same line - CHECKED
 - A file with a single invoice where IVA and IGIC (exclusive taxes) are filled should delete the line - CHECKED
 - A file with a single invoice where the net price is wrong calculated should delete the line - CHECKED
 - A file with a single invoice where CIF and NIF are filled should delete the line - CHECKED
 - A file with just 1 line is not valid because it has no header - CHECKED
 - If the invoice number is repeated, all the lines where it appears should be deleted
 - An empty or null list should output an empty list
*/

class CsvFilterShould {

    @Test
    void allow_for_correct_lines_only() {
        List<String> fileContent = oneCorrectLine();

        List<String> result = CsvFilter.filter(fileContent);

        assertThat(result).isEqualTo(fileContent);
    }

    @Test
    void exclude_lines_with_both_tax_fields_populated_as_they_are_exclusive() {
        List<String> fileContent = lineWithInvalidTaxFieldsBeing("19","8");

        List<String> result = CsvFilter.filter(fileContent);

        assertThat(result).isEqualTo(emptyInvoiceList());
    }

    @Test
    void exclude_lines_with_both_tax_fields_empty_as_one_is_required() {
        List<String> fileContent = lineWithInvalidTaxFieldsBeing("","");

        List<String> result = CsvFilter.filter(fileContent);

        assertThat(result).isEqualTo(emptyInvoiceList());
    }

    @Test
    void exclude_lines_with_non_decimal_tax_fields() {
        List<String> fileContent = lineWithInvalidTaxFieldsBeing("NON_DECIMAL","");

        List<String> result = CsvFilter.filter(fileContent);

        assertThat(result).isEqualTo(emptyInvoiceList());
    }

    @Test
    void exclude_lines_with_both_tax_fields_populated_even_if_non_decimal() {
        List<String> fileContent = lineWithInvalidTaxFieldsBeing("NON_DECIMAL","12");

        List<String> result = CsvFilter.filter(fileContent);

        assertThat(result).isEqualTo(emptyInvoiceList());
    }

    @Test
    void exclude_lines_with_the_net_price_wrongly_calculated () {
        List<String> fileContent = lineWithWrongNetPriceField();

        List<String> result = CsvFilter.filter(fileContent);

        assertThat(result).isEqualTo(emptyInvoiceList());
    }

    @Test
    void exclude_lines_with_both_id_fields_populated_as_they_are_exclusive() {
        List<String> fileContent = lineWithInvalidIdFieldsBeing("B76430134", "12345678A");

        List<String> result = CsvFilter.filter(fileContent);

        assertThat(result).isEqualTo(emptyInvoiceList());
    }

    @Test
    void exclude_lines_with_both_id_fields_empty_as_one_is_required() {
        List<String> fileContent = lineWithInvalidIdFieldsBeing("", "");

        List<String> result = CsvFilter.filter(fileContent);

        assertThat(result).isEqualTo(emptyInvoiceList());
    }

    @Test
    void exclude_lines_with_invalid_format_on_id_fields() {
        List<String> fileContent = lineWithInvalidIdFieldsBeing("", "INVALID_NIF");

        List<String> result = CsvFilter.filter(fileContent);

        assertThat(result).isEqualTo(emptyInvoiceList());
    }

    @Test
    void not_allow_files_without_header() {
        List<String> fileContent = fileWithNoHeader();

        assertThrows(HeaderNotPresentException.class, () -> {
            CsvFilter.filter(fileContent);
        });
    }

}
