package com.song.bookonline.html;

import com.song.bookonline.ChapterVo;
import com.song.bookonline.Utils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by 001844 on 2018/4/6.
 * 将List chapters转成html文件，
 * 由list chapters得到目录页
 */
public class HTMLUtils {
    private static final String PREFIX_PATH = "F:\\opensource\\bookonline\\src\\main\\webapp\\";
    private static final String CHAPTER_TEMPLATE = "chapter.html";
    private static final String CATALOG_TEMPLATE = "catalog.html";
    private static String CHAPTER_TEMPLATE_INSTANCE = null;
    private static String CATALOG_TEMPLATE_INSTANCE = null;

    private static synchronized String getChapterTemplateInstance() throws IOException {
        if (CHAPTER_TEMPLATE_INSTANCE == null) {
            File htmlTemplate = new File(PREFIX_PATH+CHAPTER_TEMPLATE);
            try {
                CHAPTER_TEMPLATE_INSTANCE = FileUtils.readFileToString(htmlTemplate, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
            return CHAPTER_TEMPLATE_INSTANCE;
        } else {
            return CHAPTER_TEMPLATE_INSTANCE;
        }
    }

    private static synchronized String getCatalogTemplateInstance() throws IOException {
        if (CATALOG_TEMPLATE_INSTANCE == null) {
            File htmlTemplate = new File(PREFIX_PATH+CATALOG_TEMPLATE);
            try {
                CATALOG_TEMPLATE_INSTANCE = FileUtils.readFileToString(htmlTemplate, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
            return CATALOG_TEMPLATE_INSTANCE;
        } else {
            return CATALOG_TEMPLATE_INSTANCE;
        }
    }


    public static void render(final List<ChapterVo> chapterVos) throws IOException {
        if (chapterVos == null || chapterVos.isEmpty()) {
            throw new IllegalArgumentException("chapters null");
        }
        for (ChapterVo chapterVo : chapterVos) {
            render(chapterVo);
        }
    }

    public static void render(final ChapterVo chapterVo) throws IOException {
        if (chapterVo == null) {
            throw new IllegalArgumentException("chapter null");
        }
        String htmlFilePath = htmlFilePath(chapterVo);
        try {
            String template = getChapterTemplateInstance();
            template = template.replace("{{chapterContent}", chapterVo.getChapterContent());
            template = template.replace("{{chapterTitle}}", chapterVo.getChapterTitle());

            FileUtils.write(new File(htmlFilePath), template, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
    public static void render(File tempFile) {
        if(tempFile==null||!tempFile.exists()){
            throw new IllegalArgumentException("file null");
        }
        String htmlFilePath = tempFile.getAbsolutePath().replace(".txt",".html");
        try {
            String template = getChapterTemplateInstance();
            template = template.replace("{{chapterContent}", FileUtils.readFileToString(tempFile,"UTF-8"));
            template = template.replace("{{chapterTitle}}", tempFile.getName());
            FileUtils.write(new File(htmlFilePath), template, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String htmlFilePath(final ChapterVo chapter) {
        String categoryTemp = chapter.getCategory();
        String titleTemp = chapter.getTitle();
        Integer chapterNo = chapter.getChapterNo();

        String htmlFilePath = Utils.pinyin(categoryTemp) + "/" + Utils.pinyin(titleTemp) + "/" + chapterNo + ".html";
        return htmlFilePath;
    }

    public static void mkCatalog(List<ChapterVo> chapterVos) throws IOException {
        if (chapterVos == null || chapterVos.isEmpty()) {
            throw new IllegalArgumentException("chapters null");
        }
        StringBuilder sb = new StringBuilder();
        for (ChapterVo chapterTemp : chapterVos) {
            String chapterName = chapterTemp.getChapterTitle();
            String filePath = htmlFilePath(chapterTemp);
            String fullHref = "<a href='" + filePath + "'>" + chapterName + "</>";
            sb.append(fullHref).append("<br>");
        }

        try {
            String catalogTemplate = getCatalogTemplateInstance();
            catalogTemplate = catalogTemplate.replace("{{chapter-links}}", sb.toString());
            String catalogPath = htmlFilePath(chapterVos.get(0)).replace("1", "index");
            FileUtils.write(new File(catalogPath), catalogTemplate, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }


}
