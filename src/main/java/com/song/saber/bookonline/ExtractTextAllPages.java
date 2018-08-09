package com.song.bookonline;

import com.snowtide.PDF;
import com.snowtide.pdf.Document;
import com.snowtide.pdf.OutputTarget;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class ExtractTextAllPages {
  public static void main (String[] args){
    String pdfFilePath = "F:\\story\\";
    File dir = new File(pdfFilePath);
    File[] files = dir.listFiles();
    for(File file:files){
      if(file.getName().endsWith(".pdf")){

        Document pdf = PDF.open(file);
        StringBuilder text = new StringBuilder(1024);

        try {
          pdf.pipe(new OutputTarget(text));
          pdf.close();
          /*System.out.println(text);*/
          FileUtils.write(new File(file.getName().replace(".pdf",".txt")),text.toString(),"UTF-8");
        } catch (IOException e) {
          e.printStackTrace();
        }

      }
    }


  }
}