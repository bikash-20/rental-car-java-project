package carrental.service;

import carrental.model.Rental;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PdfService {

    public byte[] generateReceipt(Rental rental) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        doc.add(new Paragraph("Car Rental Receipt").setBold().setFontSize(18));
        doc.add(new Paragraph("Receipt ID: " + rental.getRentalId()));
        doc.add(new Paragraph("Customer: " + rental.getCustomer().getName()));
        doc.add(new Paragraph("Phone: " + rental.getCustomer().getPhone()));
        doc.add(new Paragraph("Car: " + rental.getCar().getBrand() + " " + rental.getCar().getModel()));
        doc.add(new Paragraph("Category: " + rental.getCar().getCategory()));
        doc.add(new Paragraph("Rental period: " + rental.getRentedAtStr()));
        doc.add(new Paragraph("Days: " + rental.getDays()));
        doc.add(new Paragraph("Total price: $" + rental.getTotalPrice()));

        doc.close();
        return baos.toByteArray();
    }
}
