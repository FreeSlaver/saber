package com.song.power.common;

import com.song.power.blog.vo.BlogVo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 001844 on 2018/1/12.
 */
public class DocUtil {


    public static BlogVo readMDBlog(String mdBlogFilePath) throws IOException {
        return readMDBlog(new File(mdBlogFilePath));
    }

    public static BlogVo readMDBlog(File mdBlogFile) throws IOException {
        if (mdBlogFile == null || !mdBlogFile.exists()) {
            return null;
        }
        if (!mdBlogFile.getName().endsWith(".md")) {
            throw new IllegalArgumentException("not a md file:" + mdBlogFile.getAbsolutePath());
        }
        List<String> lines = FileUtils.readLines(mdBlogFile, "utf-8");
        if (lines == null || lines.isEmpty()) {
            return null;
        }
        int[] index = new int[2];
        int j = 0;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (StringUtils.isNotEmpty(line) && line.equals("---")) {
                index[j++] = i;
                if (j == 2) {
                    break;
                }
            }
        }
        List<String> headers = lines.subList(index[0] + 1, index[1]);
        List<String> content = lines.subList(index[1] + 1, lines.size());

        BlogVo result = new BlogVo();
        for (String header : headers) {
            if (StringUtils.isNotEmpty(header)) {
                String[] str = header.split(":");
                String field = str[0].trim();
                String val = str[1].trim();
                if (field.equals("layout")) {
                    result.setLayout(val);
                } else if (field.equals("title")) {
                    result.setTitle(val);
                } else if (field.equals("category")) {
//                "c1,c2"也可能是[c1,c2]
                    String[] categories = val.split(",|[|]| ");
                    result.setCategory(Arrays.asList(categories));
                } else if (field.equals("tags")) {
                    String[] tags = val.split(",|[|]| ");
                    result.setTags(Arrays.asList(tags));
                } else if (field.equals("keywords")) {
                    String[] keywords = val.split(",|[|]| ");
                    result.setKeywords(Arrays.asList(keywords));
                } else if (field.equals("description")) {
                    result.setDescription(val);
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (String line : content) {
            if (StringUtils.isNotEmpty(line)) {
                sb.append(line);
            }
        }
        result.setContent(sb.toString());
        return result;
    }

}
