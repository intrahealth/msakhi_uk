package com.microware.intrahealth;

import android.annotation.SuppressLint;
import android.content.Context;


import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import com.microware.intrahealth.dataprovider.DataProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.itextpdf.text.PageSize.A4;

public class Createpdf2 {

    public static final String HINDI_FONT = "Lohit-Devanagari.ttf";
    //private static String FILE = "c:/temp/FirstPdf.pdf";
    static String sParameter;
    static String sUnit;
    static String sResult;
    static String sHeader[] = new String[3];
    static String sHeader2[] = new String[4];
    static int iflag = 0;
    private static Font catFont;
    private static Font catFont1;
    private static Font catFont2;
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont;
    private static Font smallBold;
    private static Font urFontName;
    static Global g;
    File file;
    static DataProvider dataprovider;
    Context context;
    private static Font urName1;
//    static ArrayList<tblMstParameter> Parameter = new ArrayList<tblMstParameter>();
//    static ArrayList<tblTSTSampleTestResult> SampleTestResult = new ArrayList<tblTSTSampleTestResult>();

    @SuppressLint("SdCardPath")
    public boolean write(Context context, String fname, String[] Header, ArrayList<HashMap<String, String>> data, String[] Page2, int Flag) throws Exception {
        try {
            //           file = new File(Environment.getExternalStorageDirectory()+IIHSPdf+ "/"+fname+".pdf");

            this.context = context;
            g = (Global) context.getApplicationContext();
            BaseFont urName = BaseFont.createFont("assets/FreeSans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            urFontName = new Font(urName, 12);
            urName1 = FontFactory.getFont("assets/mangal.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            // urFontName = new Font(urName, 12);
            sHeader[0] = context.getResources().getString(R.string.hrpreport);
            sHeader[1] = context.getResources().getString(R.string.anmname) + " " + g.getsGlobalANMName() + " " + context.getResources().getString(R.string.distname);
            sHeader[2] = context.getResources().getString(R.string.Identificationcode);
            sHeader2[0] = context.getResources().getString(R.string.Identificationcode1);
            sHeader2[1] = context.getResources().getString(R.string.totalhrp);
            sHeader2[2] = context.getResources().getString(R.string.totalcheckup);
            sHeader2[3] = context.getResources().getString(R.string.anmsign);
            catFont2 = new Font(Font.FontFamily.TIMES_ROMAN, 16,
                    Font.BOLD, new BaseColor(0, 85, 133));

            if (Flag == 2) {

                catFont = new Font(urName, 16,
                        Font.BOLD);
                catFont1 = new Font(urName, 16,
                        Font.BOLD, new BaseColor(0, 85, 133));
                subFont = new Font(urName, 14);
                smallBold = new Font(urName, 12,
                        Font.BOLD, BaseColor.WHITE);
            } else {

                catFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
                        Font.BOLD);
                catFont1 = new Font(Font.FontFamily.TIMES_ROMAN, 16,
                        Font.BOLD, new BaseColor(0, 85, 133));
                subFont = new Font(Font.FontFamily.TIMES_ROMAN, 14);
                smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
                        Font.BOLD, BaseColor.WHITE);
            }


            String fpath = "/sdcard/msakhi/Pdf";
            File path = new File(fpath);
            File file = new File(path, fname + ".pdf");

            if (!path.exists()) {
                path.mkdirs();
                if (!file.exists()) {
                    file.createNewFile();
                }
            } else {
                if (!file.exists()) {
                    file.createNewFile();
                }
            }


            //			Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
            //			Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);


            Document document = new Document(A4.rotate());

            PdfWriter.getInstance(document,
                    new FileOutputStream(file.getAbsoluteFile()));


            document.open();
            //			addMetaData(document);
            //          addTitlePage(document);
            addContent(document, Header, data, sHeader2, Flag);
            document.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    // iText allows to add metadata to the PDF which can be viewed in your Adobe
    // Reader
    // under File -> Properties
    private static void addMetaData(Document document) {
        document.addTitle("My first PDF");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("Lars Vogel");
        document.addCreator("Lars Vogel");
    }

    private static void addTitlePage(Document document)
            throws DocumentException {

        Paragraph preface = new Paragraph();
        // We add one empty line
        addEmptyLine(preface, 1);
        // Lets write a big header
        preface.add(new Paragraph("Break Even Master Input", catFont));

        addEmptyLine(preface, 1);
        // Will create: Report generated by: _name, _date
        preface.add(new Paragraph("Community profile", subFont));
        //		addEmptyLine(preface, 3);
        //		preface.add(new Paragraph("This document describes something which is very important ",
        //				smallBold));
        //
        //		addEmptyLine(preface, 8);
        //
        //		preface.add(new Paragraph("This document is a preliminary version and not subject to your license agreement or any other agreement with vogella.com ;-).",
        //				redFont));

        document.add(preface);
        // Start a new page
        document.newPage();
    }

    public static Paragraph mypara(String ab, int flag) {
        Paragraph preface1 = new Paragraph(ab, subFont);
        if (flag == 1)
            preface1.setAlignment(Element.ALIGN_CENTER);
        if (flag == 2)
            preface1.setAlignment(Element.ALIGN_LEFT);
        if (flag == 3)
            preface1.setAlignment(Element.ALIGN_RIGHT);
        return preface1;
    }

    private static void addContent(Document document, String Header[], ArrayList<HashMap<String, String>> data, String[] sHeader2, int Flag) throws Exception {


        Paragraph preface = new Paragraph();

        //  preface.setAlignment(Element.ALIGN_CENTER);


        addEmptyLine(preface, 1);
        preface.add(mypara(sHeader[0], 1));
        addEmptyLine(preface, 1);
        preface.add(mypara(sHeader[1], 1));
        addEmptyLine(preface, 1);
        preface.add(mypara(sHeader[2], 2));
        addEmptyLine(preface, 1);
        createTable(preface, Header, data);
        preface.add(mypara(sHeader2[0], 2));
        addEmptyLine(preface, 1);
        preface.add(mypara(sHeader2[1]+" "+data.size(), 2));
        addEmptyLine(preface, 1);
        preface.add(mypara(sHeader2[2]+" "+data.size(), 2));
        addEmptyLine(preface, 1);
        addEmptyLine(preface, 1);
        preface.add(mypara(sHeader2[3], 3));
        addEmptyLine(preface, 1);


//

        document.add(preface);

    }

    private static void createTable(Paragraph preface, String[] Header, ArrayList<HashMap<String, String>> data)
            throws BadElementException {

        Context _con = null;
        //       PdfPTable table = new PdfPTable(new float[] { 2, 1 });

        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);
        PdfPTable table = new PdfPTable(new float[]{1, 3, 3, 5, 5, 2, 3, 2, 2, 2});

        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        for (int i = 0; i < Header.length; i++) {
            //      table.addCell(new PdfPCell(new Phrase(Header[i], smallBold)));
            PdfPCell c1 = new PdfPCell(new Phrase(Header[i], smallBold));
            c1.getBorder();
            c1.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(c1);
        }
//        table.addCell("Value");
//        table.addCell("Location");
        table.setHeaderRows(1);

//        PdfPCell c1 = new PdfPCell(new Phrase("Text",smallBold));
//        c1.getBorder();
//        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//        table.addCell(c1);
//
//        c1 = new PdfPCell(new Phrase("Value",smallBold));
//        c1.getBorder();
//        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//        table.addCell(c1);
//
//        table.setHeaderRows(1);
        PdfPCell[] cells = table.getRow(0).getCells();
        for (int j = 0; j < cells.length; j++) {
            cells[j].setBackgroundColor(new BaseColor(0, 85, 133));

            if (j == 0) {
                cells[j].setHorizontalAlignment(Element.ALIGN_LEFT);
            } else {
                cells[j].setHorizontalAlignment(Element.ALIGN_CENTER);
            }
        }

        dataprovider = new DataProvider(_con);
//
//        Font f = FontFactory.getFont(getFilesDir() + "/" + HINDI_FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//        PdfPCell eCell = new PdfPCell(new Phrase(entry, f));


        for (int i = 0; i < data.size(); i++) {
            Phrase phrase = new Phrase();
            phrase.add(new Chunk(data.get(i).get("ASHAName"), subFont));

            table.addCell(String.valueOf((i + 1)));
            table.addCell(data.get(i).get("ASHAName"));
            table.addCell(data.get(i).get("VillageName"));
            table.addCell(data.get(i).get("PWName"));
            table.addCell(data.get(i).get("MotherMCTSID"));
            table.addCell(data.get(i).get("HusbandName"));
            table.addCell(Validate.changeDateFormat(data.get(i).get("CheckupVisitDate")));
            table.addCell(data.get(i).get("DangerSign"));
            table.addCell(data.get(i).get("CheckupPlace"));
            table.addCell("");

            PdfPCell[] cells1 = table.getRow(i + 1).getCells();
            cells1[0].setHorizontalAlignment(Element.ALIGN_LEFT);
//            cells1[1].setHorizontalAlignment(Element.ALIGN_CENTER);
//            cells1[2].setHorizontalAlignment(Element.ALIGN_CENTER);

        }


        //		table.addCell("Colour");
        //		table.addCell("Hazen Unit");
        //		table.addCell("1.2");
        //		table.addCell("Turbidity");
        //		table.addCell("NTU");
        //		table.addCell("2.3");

        preface.add(table);

    }

    private static void createTable1(Paragraph preface, String Text, String Value)
            throws BadElementException {

        Context _con = null;

        PdfPTable table = new PdfPTable(new float[]{1, 3});
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        for (int i = 0; i < 2; i++) {
            //      table.addCell(new PdfPCell(new Phrase(Header[i], smallBold)));
            PdfPCell c1 = new PdfPCell(new Phrase());
            table.addCell(c1);
        }
        PdfPCell[] cells = table.getRow(0).getCells();
//        for (int j=0;j<cells.length;j++){
        cells[0].setHorizontalAlignment(Element.ALIGN_LEFT);
//        }

        dataprovider = new DataProvider(_con);

        if (Text.length() > 0 && Value.length() > 0) {
            Phrase phrase = new Phrase();
            phrase.add(new Chunk(Text, subFont));
            table.addCell(phrase);
            table.addCell(Value);
        } else {
            Phrase phrase = new Phrase();
            phrase.add(new Chunk(Text, subFont));
            table.addCell(phrase);
            table.addCell("");
        }


        preface.add(table);

    }


    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
