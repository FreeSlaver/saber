package com.song.bookonline;

import com.snowtide.PDF;
import com.snowtide.pdf.Document;
import com.snowtide.pdf.OutputTarget;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by 001844 on 2018/4/7.
 */
public class App2 {

    public static void main(String[] args) throws IOException {
        String xx = "H:\\小说\\【名著文学】txtpdf格式\\第30部_钢铁是怎样炼成的.pdf";
        String txtFile = "H:\\小说\\【名著文学】txtpdf格式\\第30部_钢铁是怎样炼成的.txt";

        Document pdf = PDF.open(new File(xx));
        StringBuilder text = new StringBuilder(1024);
        pdf.pipe(new OutputTarget(text));
        pdf.close();
          System.out.println(text);
        FileUtils.write(new File(txtFile), text.toString(), "UTF-8");

//        PDFConverter.toTxt(new File(xx), new File(txtFile), false);
//        HTMLUtils.render(new File(txtFile));
    }
}
