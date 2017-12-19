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
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.microware.intrahealth.dataprovider.DataProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Createpdfall {



    static String sHeader[] = new String[3];
    static String sHeader2[] = new String[3];
    static int iflag=0;
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
    String IIHSPdf = "/Pdf";

    @SuppressLint("SdCardPath")
    public boolean write(String fname, String []Header1, String []Page1, String []Page1Value, String heading1, String Page1Remark,

                         String []Header2, String []Page2_1, String []Page2_1source, String []Page2_1value,
                         String []Page2_2, String []Page2_2source, String []Page2_2value,
                         String []Page2_3, String []Page2_3source, String []Page2_3value,
                         String heading2, String heading3, String heading4, String heading5, String Remark2Value,

                         String []Header3, String []Page31, String []Page3Census1, String []Page3Value1, String []Page32, String []Page3Census2, String []Page3Value2,
                         String heading6, String heading7, String heading8, String Page3Remark,

                         String []Header4, String []Page4_1, String []Page4Value1,String []Page4_2,String []Page4Value2,String []Page4_3,String []Page4Value3,
                         String heading9, String heading10, String heading11, String heading12, String Page4Remark1, String Page4Remark3,

                         String []Header5,String [] Page5Value1,String [] Page5Value2,String [] Page5Value3,String [] Page5Value4,String [] Page5_2,String [] Page5Value5,String [] Page5_3,String [] Page5Value6,
                         String text5, String value5, String heading13, String heading14, String heading15, String Page5Remark1, String Page5Remark2,

                         String []Header6, String []Page6Value1, String []Page6Value2, String []Page6Value3, String []Page6Value4, String text6, String value6, String heading16,

                         String []Header7_1,String [] Page7Value1,String [] Page7Value2,String [] Page7Value3,String [] Page7Value4,String [] Page7Value5,String [] Header7_2,String [] Page7Value6,String [] Page7Value7,String [] Page7Value8,String [] Page7Value9,String [] Page7Value10,String [] Page7Value11,
                         String text7_1, String value7_1, String text7_2, String value7_2, String heading17, String heading18, String heading19, String pdf1, String pdf2, int Flag)throws Exception {
        try {
            //           file = new File(Environment.getExternalStorageDirectory()+IIHSPdf+ "/"+fname+".pdf");

            BaseFont urName = BaseFont.createFont("assets/baamini.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            urFontName = new Font(urName, 12);

            catFont2 = new Font(Font.FontFamily.TIMES_ROMAN, 16,
                    Font.BOLD, new BaseColor(0, 85, 133));

            if(Flag == 2) {

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

            String fpath = "/sdcard/IIHS/Pdfs" ;
            File path = new File(fpath);
            File file = new File(path, fname + ".pdf");

            if(!path.exists()) {
                path.mkdirs();
                if (!file.exists()) {
                    file.createNewFile();
                }
            }else{
                if (!file.exists()) {
                    file.createNewFile();
                }
            }

            //			Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
            //			Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);


            Document document = new Document();

            PdfWriter.getInstance(document,
                    new FileOutputStream(file.getAbsoluteFile()));


            document.open();
            //			addMetaData(document);
            //          addTitlePage(document);
            addContent(document,Header1,Page1, Page1Value, heading1, Page1Remark,

                    Header2, Page2_1, Page2_1source, Page2_1value,
                    Page2_2, Page2_2source, Page2_2value,
                    Page2_3, Page2_3source, Page2_3value,
                    heading2, heading3, heading4, heading5, Remark2Value,

                    Header3, Page31, Page3Census1, Page3Value1, Page32, Page3Census2, Page3Value2,
                    heading6, heading7, heading8, Page3Remark,

                    Header4, Page4_1, Page4Value1,Page4_2,Page4Value2,Page4_3,Page4Value3,
                    heading9, heading10, heading11, heading12, Page4Remark1, Page4Remark3,

                    Header5, Page5Value1, Page5Value2, Page5Value3, Page5Value4, Page5_2, Page5Value5, Page5_3, Page5Value6,
                    text5, value5, heading13, heading14, heading15, Page5Remark1, Page5Remark2,

                    Header6, Page6Value1, Page6Value2, Page6Value3, Page6Value4, text6, value6, heading16,

                    Header7_1, Page7Value1, Page7Value2, Page7Value3, Page7Value4, Page7Value5, Header7_2, Page7Value6, Page7Value7, Page7Value8, Page7Value9, Page7Value10, Page7Value11,
                    text7_1, value7_1, text7_2, value7_2, heading17, heading18, heading19, pdf1, pdf2, Flag);
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

    private static void addContent(Document document,String Header1[], String []Page1, String []Page1Value, String heading1, String Page1Remark,

                                   String [] Header2, String []Page2_1, String []Page2_1source, String []Page2_1value,
                                   String []Page2_2, String []Page2_2source, String []Page2_2value,
                                   String []Page2_3, String []Page2_3source, String []Page2_3value,
                                   String heading2, String heading3, String heading4, String heading5, String Remark2Value,

                                   String []Header3, String []Page31, String []Page3Census1, String []Page3Value1, String []Page32, String []Page3Census2, String []Page3Value2,
                                   String heading6, String heading7, String heading8, String Page3Remark,

                                   String []Header4, String []Page4_1, String []Page4Value1,String []Page4_2,String []Page4Value2,String []Page4_3,String []Page4Value3,
                                   String heading9, String heading10, String heading11, String heading12, String Page4Remark1, String Page4Remark3,

                                   String []Header5,String [] Page5Value1,String [] Page5Value2,String [] Page5Value3,String [] Page5Value4,String [] Page5_2,String [] Page5Value5,String [] Page5_3,String [] Page5Value6,
                                   String text5, String value5, String heading13, String heading14, String heading15, String Page5Remark1, String Page5Remark2,

                                   String []Header6, String []Page6Value1, String []Page6Value2, String []Page6Value3, String []Page6Value4, String text6, String value6, String heading16,

                                   String []Header7_1,String [] Page7Value1,String [] Page7Value2,String [] Page7Value3,String [] Page7Value4,String [] Page7Value5,String [] Header7_2,String [] Page7Value6,String [] Page7Value7,String [] Page7Value8,String [] Page7Value9,String [] Page7Value10,String [] Page7Value11,
                                   String text7_1, String value7_1, String text7_2, String value7_2, String heading17, String heading18, String heading19, String pdf1, String pdf2, int Flag) throws Exception {


        Paragraph preface = new Paragraph();
        Image img;
        sHeader[0] = "Question";
        sHeader[1] = "Source";
        sHeader[2] = "Data";


        // We add one empty line

        if(iflag == 26) {
            iflag = 0;
        }

        if(Flag == 2) {

        }

        if(iflag==0) {
            addEmptyLine(preface, 1);
            preface.add(new Paragraph(pdf1, catFont1));
            addEmptyLine(preface, 1);
            preface.add(new Paragraph(pdf2, catFont2));
            addEmptyLine(preface, 1);
            preface.add(new Paragraph(heading1, catFont));
            addEmptyLine(preface, 1);
            createTable1(preface, Header1, Page1, Page1Value);
            addEmptyLine(preface, 1);
            iflag=1;
        }
        if(iflag==1) {
            addEmptyLine(preface, 1);
            createTable7(preface, text7_2, Page1Remark);
            addEmptyLine(preface, 1);
            iflag=2;
        }

        if(iflag==2) {
            preface.add(new Paragraph(heading2, catFont));
            addEmptyLine(preface, 1);
            createTable2(preface, Header2, Page2_1, Page2_1source, Page2_1value);
            addEmptyLine(preface, 1);
            iflag=3;
        }
        if(iflag==3) {
            addEmptyLine(preface, 1);
            preface.add(new Paragraph(heading3, catFont));
            addEmptyLine(preface, 1);
            preface.add(new Paragraph(heading4, subFont));
            addEmptyLine(preface, 1);
            createTable2(preface, Header2, Page2_2, Page2_2source, Page2_2value);
            addEmptyLine(preface, 1);
            iflag=4;
        }
        if(iflag==4) {
            preface.add(new Paragraph(heading5, subFont));
            addEmptyLine(preface, 1);
            createTable2(preface, Header2, Page2_3, Page2_3source, Page2_3value);
            addEmptyLine(preface, 1);
            iflag=5;
        }
        if(iflag==5) {
            addEmptyLine(preface, 1);
            createTable7(preface, text7_2, Remark2Value);
            addEmptyLine(preface, 1);
            iflag=6;
        }

        if(iflag==6) {
            addEmptyLine(preface, 1);
            preface.add(new Paragraph(heading6, catFont));
            addEmptyLine(preface, 1);
            preface.add(new Paragraph(heading7, subFont));
            addEmptyLine(preface, 1);
            createTable2(preface, Header3, Page31, Page3Census1, Page3Value1);
            addEmptyLine(preface, 1);
            iflag=7;
        }
        if(iflag==7) {
            addEmptyLine(preface, 1);
            createTable7(preface, text7_2, Page3Remark);
            addEmptyLine(preface, 1);
            iflag=8;
        }
        if(iflag==8) {
            preface.add(new Paragraph(heading8, subFont));
            addEmptyLine(preface, 1);
            createTable2(preface, Header3, Page32, Page3Census2, Page3Value2);
            addEmptyLine(preface, 1);
            iflag=9;
        }

        if(iflag==9) {
            addEmptyLine(preface, 1);
            preface.add(new Paragraph(heading9, catFont));
            addEmptyLine(preface, 1);
            createTable1(preface, Header1, Page4_1, Page4Value1);
            addEmptyLine(preface, 1);
            iflag=10;
        }
        if(iflag==10) {
            addEmptyLine(preface, 1);
            createTable7(preface, text7_2, Page4Remark1);
            addEmptyLine(preface, 1);
            iflag=11;
        }
        if(iflag==11) {
            addEmptyLine(preface, 1);
            preface.add(new Paragraph(heading10, catFont));
            addEmptyLine(preface, 1);
            preface.add(new Paragraph(heading11, subFont));
            addEmptyLine(preface, 1);
            createTable1(preface, Header1, Page4_2, Page4Value2);
            addEmptyLine(preface, 1);
            iflag=12;
        }
        if(iflag==12) {
            preface.add(new Paragraph(heading12, subFont));
            addEmptyLine(preface, 1);
            createTable1(preface, Header1, Page4_3, Page4Value3);
            addEmptyLine(preface, 1);
            iflag=13;
        }
        if(iflag==13) {
            addEmptyLine(preface, 1);
            createTable7(preface, text7_2, Page4Remark3);
            addEmptyLine(preface, 1);
            iflag=14;
        }

        if(iflag==14) {
            preface.add(new Paragraph(heading13, catFont));
            addEmptyLine(preface, 1);
            createTable3(preface, Header5, Page5Value1, Page5Value2, Page5Value3, Page5Value4);
            addEmptyLine(preface, 1);
            iflag=15;
        }
        if(iflag==15) {
            createTable7(preface, text5, value5);
            addEmptyLine(preface, 1);
            iflag=16;
        }
        if(iflag==16) {
            addEmptyLine(preface, 1);
            preface.add(new Paragraph(heading14, catFont));
            addEmptyLine(preface, 1);
            createTable1(preface, Header1, Page5_2, Page5Value5);
            addEmptyLine(preface, 1);
            iflag=17;
        }
        if(iflag==17) {
            addEmptyLine(preface, 1);
            createTable7(preface, text7_2, Page5Remark1);
            addEmptyLine(preface, 1);
            iflag=18;
        }
        if(iflag==18) {
            preface.add(new Paragraph(heading15, catFont));
            addEmptyLine(preface, 1);
            createTable1(preface, Header1, Page5_3, Page5Value6);
            addEmptyLine(preface, 1);
            iflag=19;
        }
        if(iflag==19) {
            addEmptyLine(preface, 1);
            createTable7(preface, text7_2, Page5Remark2);
            addEmptyLine(preface, 1);
            iflag=20;
        }

        if(iflag==20) {
            addEmptyLine(preface, 1);
            preface.add(new Paragraph(heading16, catFont));
            addEmptyLine(preface, 1);
            createTable3(preface, Header6, Page6Value1, Page6Value2, Page6Value3, Page6Value4);
            addEmptyLine(preface, 1);
            iflag=21;
        }
        if(iflag==21) {
            createTable7(preface, text6, value6);
            addEmptyLine(preface, 1);
            iflag=22;
        }

        if(iflag==22) {
            addEmptyLine(preface, 1);
            preface.add(new Paragraph(heading17, catFont));
            addEmptyLine(preface, 1);
            preface.add(new Paragraph(heading18, subFont));
            addEmptyLine(preface, 1);
            createTable4(preface, Header1, text7_1, value7_1);
            addEmptyLine(preface, 1);
            iflag=23;
        }
        if(iflag==23) {
            createTable5(preface, Header7_1, Page7Value1, Page7Value2, Page7Value3, Page7Value4, Page7Value5);
            addEmptyLine(preface, 1);
            iflag=24;
        }
        if(iflag==24) {
            preface.add(new Paragraph(heading19, subFont));
            addEmptyLine(preface, 1);
            createTable6(preface, Header7_2, Page7Value6, Page7Value7, Page7Value8, Page7Value9, Page7Value10, Page7Value11);
            addEmptyLine(preface, 1);
            iflag=25;
        }
        if(iflag==25) {
            createTable7(preface, text7_2, value7_2);
            addEmptyLine(preface, 1);
            iflag=26;
        }

        document.add(preface);

    }

    private static void createTable1(Paragraph preface, String []Header, String []Text, String []Value)
            throws BadElementException {

        Context _con = null;

        PdfPTable table = new PdfPTable(new float[] { 2, 1 });
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        for(int i=0;i<Header.length;i++) {
            //      table.addCell(new PdfPCell(new Phrase(Header[i], smallBold)));
            PdfPCell c1 = new PdfPCell(new Phrase(Header[i],smallBold));
            c1.getBorder();
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
        }

        table.setHeaderRows(1);

        PdfPCell[] cells = table.getRow(0).getCells();
        for (int j=0;j<cells.length;j++){
            cells[j].setBackgroundColor(new BaseColor(0,85,133));

            if (j == 0) {
                cells[j].setHorizontalAlignment(Element.ALIGN_LEFT);
            } else {
                cells[j].setHorizontalAlignment(Element.ALIGN_CENTER);
            }
        }

        dataprovider = new DataProvider(_con);

        if(Text.length==Value.length) {
            for (int i = 0; i < Value.length; i++) {
                Phrase phrase = new Phrase();
                phrase.add(new Chunk(Text[i], subFont));
                table.addCell(phrase);
                table.addCell(Value[i]);
                PdfPCell[] cells1 = table.getRow(i + 1).getCells();
                cells1[0].setHorizontalAlignment(Element.ALIGN_LEFT);
                cells1[1].setHorizontalAlignment(Element.ALIGN_CENTER);
            }
        }else{
            for (int i = 0; i < Value.length; i++) {
                Phrase phrase = new Phrase();
                phrase.add(new Chunk(Text[i], subFont));
                table.addCell(phrase);
                table.addCell("");
                PdfPCell[] cells1 = table.getRow(i + 1).getCells();
                cells1[0].setHorizontalAlignment(Element.ALIGN_LEFT);
            }
        }

        preface.add(table);

    }

    private static void createTable2(Paragraph preface, String []Header, String []Text, String []Source, String []Value)
            throws BadElementException {

        Context _con = null;

        PdfPTable table = new PdfPTable(new float[] { 2, 1,1 });
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        for(int i=0;i<Header.length;i++) {
            //      table.addCell(new PdfPCell(new Phrase(Header[i], smallBold)));
            PdfPCell c1 = new PdfPCell(new Phrase(Header[i],smallBold));
            c1.getBorder();
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
        }

        table.setHeaderRows(1);

        PdfPCell[] cells = table.getRow(0).getCells();
        for (int j=0;j<cells.length;j++){
            cells[j].setBackgroundColor(new BaseColor(0,85,133));

            if (j == 0) {
                cells[j].setHorizontalAlignment(Element.ALIGN_LEFT);
            } else {
                cells[j].setHorizontalAlignment(Element.ALIGN_CENTER);
            }
        }

        dataprovider = new DataProvider(_con);

        if(Text.length==Value.length) {
            for (int i = 0; i < Value.length; i++) {
                Phrase phrase = new Phrase();
                phrase.add(new Chunk(Text[i], subFont));
                table.addCell(phrase);
                table.addCell(Source[i]);
                table.addCell(Value[i]);
                PdfPCell[] cells1 = table.getRow(i + 1).getCells();
                cells1[0].setHorizontalAlignment(Element.ALIGN_LEFT);
                cells1[1].setHorizontalAlignment(Element.ALIGN_CENTER);
                cells1[2].setHorizontalAlignment(Element.ALIGN_CENTER);
            }
        }else{
            for (int i = 0; i < Value.length; i++) {
                Phrase phrase = new Phrase();
                phrase.add(new Chunk(Text[i], subFont));
                table.addCell(phrase);
                table.addCell("");
                table.addCell("");
                PdfPCell[] cells1 = table.getRow(i + 1).getCells();
                cells1[0].setHorizontalAlignment(Element.ALIGN_LEFT);
            }
        }

        preface.add(table);

    }

    private static void createTable3(Paragraph preface, String []Header, String []Text, String []Value, String []Value1, String []Value2)
            throws BadElementException {

        Context _con = null;

        PdfPTable table = new PdfPTable(new float[] { 1, 2, 1, 1});
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        for(int i=0;i<Header.length;i++) {
            //      table.addCell(new PdfPCell(new Phrase(Header[i], smallBold)));
            PdfPCell c1 = new PdfPCell(new Phrase(Header[i],smallBold));
            c1.getBorder();
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
        }

        table.setHeaderRows(1);

        PdfPCell[] cells = table.getRow(0).getCells();
        for (int j=0;j<cells.length;j++){
            cells[j].setBackgroundColor(new BaseColor(0,85,133));

            if (j == 0) {
                cells[j].setHorizontalAlignment(Element.ALIGN_LEFT);
            } else {
                cells[j].setHorizontalAlignment(Element.ALIGN_CENTER);
            }
        }

        dataprovider = new DataProvider(_con);

        if(Text != null && Text.length > 0) {
            if (Text.length == Value.length) {
                for (int i = 0; i < Value.length; i++) {
                    table.addCell(Text[i]);
                    table.addCell(Value[i]);
                    table.addCell(Value1[i]);
                    table.addCell(Value2[i]);
                }
            } else {
                for (int i = 0; i < Value.length; i++) {
                    table.addCell("");
                    table.addCell("");
                    table.addCell("");
                    table.addCell("");
                }
            }
        } else {
            table.addCell("");
            table.addCell("");
            table.addCell("");
            table.addCell("");
        }

        preface.add(table);

    }

    private static void createTable4(Paragraph preface, String []Header, String Text, String Value)
            throws BadElementException {

        Context _con = null;

        PdfPTable table = new PdfPTable(new float[] { 1, 1});
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        for(int i=0;i<Header.length;i++) {
            //      table.addCell(new PdfPCell(new Phrase(Header[i], smallBold)));
            PdfPCell c1 = new PdfPCell(new Phrase(Header[i],smallBold));
            c1.getBorder();
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
        }

        table.setHeaderRows(1);

        PdfPCell[] cells = table.getRow(0).getCells();
        for (int j=0;j<cells.length;j++){
            cells[j].setBackgroundColor(new BaseColor(0,85,133));

            if (j == 0) {
                cells[j].setHorizontalAlignment(Element.ALIGN_LEFT);
            } else {
                cells[j].setHorizontalAlignment(Element.ALIGN_CENTER);
            }
        }

        dataprovider = new DataProvider(_con);

        if(Text.length() > 0 && Value.length() > 0) {
            Phrase phrase = new Phrase();
            phrase.add(new Chunk(Text, subFont));
            table.addCell(phrase);
            table.addCell(Value);
            PdfPCell[] cells1 = table.getRow(1).getCells();
            cells1[0].setHorizontalAlignment(Element.ALIGN_LEFT);
            cells1[1].setHorizontalAlignment(Element.ALIGN_CENTER);
        } else {
            Phrase phrase = new Phrase();
            phrase.add(new Chunk(Text, subFont));
            table.addCell(phrase);
            table.addCell("");
            PdfPCell[] cells1 = table.getRow(1).getCells();
            cells1[0].setHorizontalAlignment(Element.ALIGN_LEFT);
        }

        preface.add(table);

    }

    private static void createTable5(Paragraph preface, String []Header, String []Text, String []Value, String []Value1, String []Value2, String []Value3)
            throws BadElementException {

        Context _con = null;
        
        PdfPTable table = new PdfPTable(new float[] { 1, 2, 1, 1, 1});
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        for(int i=0;i<Header.length;i++) {
            //      table.addCell(new PdfPCell(new Phrase(Header[i], smallBold)));
            PdfPCell c1 = new PdfPCell(new Phrase(Header[i],smallBold));
            c1.getBorder();
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
        }
        
        table.setHeaderRows(1);

        PdfPCell[] cells = table.getRow(0).getCells();
        for (int j=0;j<cells.length;j++){
            cells[j].setBackgroundColor(new BaseColor(0,85,133));
        }

        dataprovider = new DataProvider(_con);

        if(Text != null && Text.length > 0) {
            if (Text.length == Value.length) {
                for (int i = 0; i < Value.length; i++) {
                    table.addCell(Text[i]);
                    table.addCell(Value[i]);
                    table.addCell(Value1[i]);
                    table.addCell(Value2[i]);
                    table.addCell(Value3[i]);
                }
            } else {
                for (int i = 0; i < Value.length; i++) {
                    table.addCell("");
                    table.addCell("");
                    table.addCell("");
                    table.addCell("");
                    table.addCell("");
                }
            }
        } else {
            table.addCell("");
            table.addCell("");
            table.addCell("");
            table.addCell("");
            table.addCell("");
        }

        preface.add(table);

    }

    private static void createTable6(Paragraph preface, String []Header, String []Text, String []Value, String []Value1, String []Value2, String []Value3, String []Value4)
            throws BadElementException {

        Context _con = null;
        
        PdfPTable table = new PdfPTable(new float[] { 1, 2, 1, 1, 1, 1});
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        for(int i=0;i<Header.length;i++) {
            //      table.addCell(new PdfPCell(new Phrase(Header[i], smallBold)));
            PdfPCell c1 = new PdfPCell(new Phrase(Header[i],smallBold));
            c1.getBorder();
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
        }
        
        table.setHeaderRows(1);

        PdfPCell[] cells = table.getRow(0).getCells();
        for (int j=0;j<cells.length;j++){
            cells[j].setBackgroundColor(new BaseColor(0,85,133));
        }

        dataprovider = new DataProvider(_con);

        if(Text != null && Text.length > 0) {
            if (Text.length == Value.length) {
                for (int i = 0; i < Value.length; i++) {
                    table.addCell(Text[i]);
                    table.addCell(Value[i]);
                    table.addCell(Value1[i]);
                    table.addCell(Value2[i]);
                    table.addCell(Value3[i]);
                    table.addCell(Value4[i]);
                }
            } else {
                for (int i = 0; i < Value.length; i++) {
                    table.addCell("");
                    table.addCell("");
                    table.addCell("");
                    table.addCell("");
                    table.addCell("");
                    table.addCell("");
                }
            }
        } else {
            table.addCell("");
            table.addCell("");
            table.addCell("");
            table.addCell("");
            table.addCell("");
            table.addCell("");
        }

        preface.add(table);

    }

    private static void createTable7(Paragraph preface, String Text, String Value)
            throws BadElementException {

        Context _con = null;

        PdfPTable table = new PdfPTable(new float[]{1, 2});
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
