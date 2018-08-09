package com.song.bookonline;

import com.song.bookonline.html.HTMLUtils;
import com.song.bookonline.pdf.PDFConverter;
import org.apache.commons.io.FileUtils;

import java.io.File;

/**
 * Created by 001844 on 2018/4/2.
 */
public class App {
    public static void main(String[] args) throws Exception {
//        String dir = args[0];//需要从目录中读取，并且txt和pdf做不同处理
//        String dir = "D:\\Users\\001844\\Downloads\\金庸全集TXT\\金庸全集TXT";
//        String dir = "F:\\story\\【人物传记】pdf格式";
//        String dir = "F:\\story\\【历史军事】txt格式";
        String dir = "F:\\\\story\\【名著文学】txtpdf格式";
        /*String xx ="F:\\\\story\\【名著文学】txtpdf格式\\第30部_钢铁是怎样炼成的.txt";
        String str = FileUtils.readFileToString(new File(xx),"UTF-8");
        HTMLUtils.render(new File(xx));*/

        File file = new File(dir);//这里TM还有遍历，烦心，为毛今天这么多人？

        String category = file.getName().substring(1,5);
        if (file == null || !file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null && files.length != 0) {
                for (File tempFile : files) {
                    String fileName = tempFile.getName();
                    String title = fileName.replace(".pdf","");
                    if (!fileName.endsWith(".txt") && !fileName.endsWith(".pdf")) {
                        System.out.println(fileName);
                    }
                    //是pdf先转成txt
                    if (fileName.endsWith(".pdf")) {
                        PDFConverter.toTxt(tempFile, null, true);
                    }
                }



                for (File tempFile : files) {
                    //将文件分成一章章的
//                    List<ChapterVo> chapterVos = TxtProcessor.process(tempFile,null,category);
//                    HTMLUtils.render(chapterVos);
                    String strx = FileUtils.readFileToString(tempFile,"UTF-8");
                    HTMLUtils.render(tempFile);
//                    HTMLUtils.mkCatalog(chapterVos);
                    //已处理的要记录到文件中或者数据库中？草，发现什么都不是那么简单啊。

                }
            }
        }
    }



}