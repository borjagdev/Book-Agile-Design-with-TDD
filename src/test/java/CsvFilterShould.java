import CsvFilter.CsvFilter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/* TODO:
 - A file with a single bill where everything is correct should output the same line
 - A file with a single bill where IVA and IGIC (exclusive taxes) are filled should delete the line
 - A file with a single bill where the net cost is wrong calculated should delete the line
 - A file with a single bill where CIF and NIF are filled should delete the line
 - A file with just 1 line is not valid because it has no header
 - If the bill number is repeated, all the lines where it appears should be deleted
 - An empty or null list should output an empty list
*/

class CsvFilterShould {

    @Test
    void allow_for_correct_lines_only() {
        List<String> csvContent = new ArrayList<>();
        String headerLine = "Num_factura, Fecha, Bruto, Neto, IVA, IGIC, Concepto, CIF_cliente, NIF_cliente";
        String invoiceLine = "1,02/05/2019,1000,810,19,,ACER Laptop,B76430134,";
        csvContent.add(headerLine);
        csvContent.add(invoiceLine);

        List<String> result = CsvFilter.filter(csvContent);

        assertThat(result).isEqualTo(csvContent);
    }

}
