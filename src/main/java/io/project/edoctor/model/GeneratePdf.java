package io.project.edoctor.model;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import io.project.edoctor.model.entity.User;
import io.project.edoctor.model.entity.UserDiagnosis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.Stream;

public class GeneratePdf {

    public static ByteArrayInputStream generatePdf(User user, List<UserDiagnosis> userDiagnosisList) throws DocumentException {

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Font bigFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD,16, BaseColor.BLACK);
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 14, BaseColor.BLACK);
        Font smallFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
        Paragraph title = new Paragraph("E-Doctor", bigFont); title.setAlignment(Element.ALIGN_CENTER);
        Paragraph title2 = new Paragraph("Results for preliminary diagnosis", bigFont); title2.setAlignment(Element.ALIGN_CENTER);

        addEmptyLine(title2,2);

        Paragraph chunk = new Paragraph("Patient: " + user.getUserData().getName() + ", age: " + user.getUserData().getAge() + ", " + user.getUserData().getGender(), font);
        Paragraph chunk2 = new Paragraph("Height: " + user.getUserData().getHeight() + "cm, weight: " + user.getUserData().getWeight() + "kg", font);
        Paragraph chunk3 = new Paragraph("Please note that this is not a medical diagnosis. Consult your doctor to confirm your results.", smallFont);

        addEmptyLine(chunk2,2);

        PdfPTable table = new PdfPTable(2);
        addTableHeader(table);
        for (UserDiagnosis userDiagnosis: userDiagnosisList) {
            table.addCell(userDiagnosis.getName());
            table.addCell(userDiagnosis.getProbability().toString());
        }

        PdfWriter.getInstance(document, out);

        document.open();

        document.add(title); document.add(title2);
        document.add(chunk); document.add(chunk2);
        document.add(table); document.add(new Paragraph(" "));
        document.add(chunk3);

        document.close();


        return new ByteArrayInputStream(out.toByteArray());
    }

    private static void addTableHeader(PdfPTable table) {
        Stream.of("Illness", "Probability")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
