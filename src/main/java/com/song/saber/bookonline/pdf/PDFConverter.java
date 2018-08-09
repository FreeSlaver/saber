package com.song.bookonline.pdf;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.tools.ExtractText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by 001844 on 2018/4/6.
 */
public class PDFConverter {
    private static final String PASSWORD = "-password";
    private static final String ENCODING = "-encoding";
    private static final String CONSOLE = "-console";
    private static final String START_PAGE = "-startPage";
    private static final String END_PAGE = "-endPage";
    private static final String SORT = "-sort";
    private static final String IGNORE_BEADS = "-ignoreBeads";
    private static final String DEBUG = "-debug";
    private static final String HTML = "-html";
    private static final String VIEW_STRUCTURE = "-viewstructure";
    private static final String STD_ENCODING = "UTF-8";


    public static void toTxt(File pdfFile, File outputTxt, boolean withLayout) throws IOException {
        if (pdfFile == null || !pdfFile.exists()) {
            return;
        }
        if (!pdfFile.getName().endsWith(".pdf")) {
            System.out.println("not a pdf"+pdfFile.getName());
            return;
        }

        if (outputTxt == null || !outputTxt.exists()) {
            /*String abPath = pdfFile.getAbsolutePath();
            abPath = abPath.replace(".pdf", ".txt");*/
            String txtFileName = pdfFile.getName().replace(".pdf", ".txt");
            outputTxt = new File("F:\\story\\【名著文学】txtpdf格式\\"+txtFileName);
        }
        if (withLayout) {
            try {
                PDFParser pdfParser = new PDFParser(new RandomAccessFile(pdfFile, "r"));
                pdfParser.parse();
                PDDocument pdDocument = new PDDocument(pdfParser.getDocument());
                PDFTextStripper pdfTextStripper = new PDFLayoutTextStripper();
                String text = pdfTextStripper.getText(pdDocument);

                FileUtils.write(outputTxt, text, "UTF-8");
                //删掉原pdf
                pdfFile.delete();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
//                throw e;
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("@@@"+pdfFile.getAbsolutePath());
//                throw e;
            }

        } else {
            String[] argsTemp = new String[]{SORT, IGNORE_BEADS, pdfFile.getAbsolutePath(), outputTxt.getAbsolutePath()};
            ExtractText.main(argsTemp);
        }
    }

    public static boolean isNormalTextPDF(File file) throws IOException {
        if (file == null || !file.exists()) {
            throw new IllegalArgumentException("file null");
        }
        PDDocument pddDocument = PDDocument.load(file);

        PDFTextStripper textStripper = new PDFTextStripper();
        String text = textStripper.getText(pddDocument);
        pddDocument.close();

        if (StringUtils.isEmpty(text)) {
            throw new IllegalArgumentException("file no content");
        }
        String str = text.substring(0, 6);
        if (str.equals("\r\n\r\n\r\n")) {
            return false;
        } else {
            return true;
        }
    }
}
