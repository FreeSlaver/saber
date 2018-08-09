package com.song.bookonline.txt;

import com.song.bookonline.ChapterVo;
import com.song.bookonline.Utils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 001844 on 2018/4/6.
 */
public class TxtProcessor {

    public static List<ChapterVo> process(File txtFile,String author,String category) throws IOException {
        if (txtFile == null || !txtFile.exists()) {
            throw new IllegalArgumentException("txt file null");
        }
        if (!txtFile.getName().endsWith(".txt")) {
            throw new IllegalArgumentException("not txt file");
        }
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(txtFile));
            List<ChapterVo> chapters = new ArrayList<ChapterVo>();
            StringBuilder sb = null;
            ChapterVo chapterVo = null;
            String nextLine = null;
            Integer chapterNo = 0;

            while ((nextLine = br.readLine()) != null) {
                System.out.println(nextLine);
                System.out.println("-----------------------------------------------");
                if (Utils.isChapterName(nextLine)) {//这个判断用正则表达式重构下
                    if (sb != null && sb.length() != 0) {
                        chapterVo.setChapterContent(sb.toString());
                        chapters.add(chapterVo);

                        sb = null;
                    }

                    chapterVo = makeNew(nextLine, author, category, txtFile.getName(), ++chapterNo);
                } else {
                    if (chapterVo != null && StringUtils.isNotEmpty(nextLine)) {
                        if (sb == null) {
                            sb = new StringBuilder("    ");//他妈的，第一个还是有问题
                        }
                        sb.append(nextLine).append("\r\n").append("\r\n");
                        System.out.println(nextLine);
                        System.out.println("-----------------------------------------------");
                    }
                }
            }
            chapterVo.setChapterContent(sb.toString());
            chapters.add(chapterVo);
            sb = null;
            return chapters;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static ChapterVo makeNew(String nextLine, String author, String category, String title, Integer chapterNo) {
        if (StringUtils.isEmpty(nextLine)) {
            return null;
        }
        ChapterVo chapterVo = new ChapterVo();
        chapterVo.setChapterNo(chapterNo);
        chapterVo.setChapterTitle(nextLine);
        chapterVo.setAuthor(author);
        chapterVo.setCategory(category);
        chapterVo.setTitle(title);
        return chapterVo;
    }
}
