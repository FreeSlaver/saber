package com.song.saber.office.word;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 00013708 on 2017/12/3.
 */
public class Test {

    public static void main(String[] args) throws IOException {
        File file = new File("E:\\Dropbox\\doc\\其他\\个人简历2.docx");
        InputStream in = new FileInputStream(file);
        /*XWPFHeaderFooter headerFooter = new XWPFHeaderFooter() {
            public BodyType getPartType() {
                return BodyType.HEADER;
            }
        };*/


        XWPFDocument doc = new XWPFDocument(in);

        XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(doc);
        XWPFHeader header = policy.getDefaultHeader();
        System.out.println(header.getText());


//        List<XWPFHeader> headerList = doc.getHeaderList();
//        System.out.println(Arrays.toString(headerList.toArray()));

        XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
        String text = extractor.getText();
        System.out.println(text);


    }

}
