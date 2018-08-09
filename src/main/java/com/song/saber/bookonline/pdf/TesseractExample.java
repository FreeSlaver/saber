package com.song.bookonline.pdf;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class TesseractExample {

    public static void main(String[] args) throws TesseractException {
        String pdfFilePaht = "D:\\Users\\001844\\Downloads\\learn_to_ask-page-005.jpg";
        File imageFile = new File(pdfFilePaht);
        ITesseract instance = new Tesseract();  // JNA Interface Mapping
        // ITesseract instance = new Tesseract1(); // JNA Direct Mapping
//        instance.setLanguage("zho");
        /*List<ITesseract.RenderedFormat> renderedFormatList = new ArrayList<ITesseract.RenderedFormat>();
        renderedFormatList.add(ITesseract.RenderedFormat.TEXT);
        instance.createDocuments(pdfFilePaht,"透明.txt",renderedFormatList);*/

        try {
            String result = instance.doOCR(imageFile);
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
    }
}