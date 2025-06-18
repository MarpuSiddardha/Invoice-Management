package com.siddardha.InvoiceManagement.Service;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.siddardha.InvoiceManagement.Model.Invoice;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.stream.Stream;

@Service
public class PdfGeneratorService {

    public ByteArrayInputStream generateInvoicePdf(Invoice invoice) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            Paragraph title = new Paragraph("Invoice #" + invoice.getId(), titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" ")); // spacer

            document.add(new Paragraph("Client: " + invoice.getClient().getName(), bodyFont));
            document.add(new Paragraph("Email: " + invoice.getClient().getEmail(), bodyFont));
            document.add(new Paragraph("Date: " + invoice.getDate(), bodyFont));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{4, 2, 2});

            Stream.of("Description", "Quantity", "Price").forEach(header -> {
                PdfPCell cell = new PdfPCell(new Phrase(header, bodyFont));
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(cell);
            });

            if (invoice.getItems() != null) {
                for (var item : invoice.getItems()) {
                    table.addCell(new Phrase(item.getDescription(), bodyFont));
                    table.addCell(new Phrase(item.getQuantity().toString(), bodyFont));
                    table.addCell(new Phrase(item.getPrice().toString(), bodyFont));
                }
            }

            document.add(table);
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Total: ₹" + invoice.getTotal(), bodyFont));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

}
