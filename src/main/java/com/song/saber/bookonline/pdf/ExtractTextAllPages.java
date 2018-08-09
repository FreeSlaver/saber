package pdfts.examples;

import com.snowtide.PDF;
import com.snowtide.pdf.Document;
import com.snowtide.pdf.OutputTarget;

public class ExtractTextAllPages {
    public static void main(String[] args) throws java.io.IOException {
        String pdfFilePath = "D:\\Users\\001844\\Downloads\\透明的红萝卜.pdf";

        Document pdf = PDF.open(pdfFilePath);
//        Collection<Image> images = pdf.getImages();
        /*for(Image image:images){
            image.
        }*/

        /*List<Page> pages = pdf.getPages();
        for(int i=0;i<pages.size();i++){
            Page page = pages.get(i);
            BlockParent blockParent = page.getTextContent();
            Block block = blockParent.getChild(0);
        }*/

        StringBuilder text = new StringBuilder(10240000);
        pdf.pipe(new OutputTarget(text));
        pdf.close();
        System.out.println(text.toString());//也TM不行。
    }
}