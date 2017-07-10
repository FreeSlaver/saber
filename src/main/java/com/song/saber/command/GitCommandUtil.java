package com.song.saber.command;

import com.song.saber.common.date.DateUtil;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

/**
 * Created by 00013708 on 2017/6/14.
 */
public class GitCommandUtil {

  public static void main(String[] args) throws IOException, GitAPIException {
    String comment = "test java execute git command";
    String proPath = "E:\\Dropbox\\songxin1990.github.io";
    commit(comment, proPath);
  }

  public static void commit(String gitRepositoryPath, String comment)
      throws IOException, NoFilepatternException, GitAPIException {
    Git git = null;
    try {
      //这里还可以自动找Git仓库所在文件夹
      Repository existingRepo = new FileRepositoryBuilder()
          .setGitDir(new File(gitRepositoryPath + "\\.git")).build();
      git = new Git(existingRepo);

      //true if no differences exist between the working-tree, the index, and the current HEAD, false if differences do exist
      if (git.status().call().isClean() == true) {
        System.out.println("\n-------code is clean------");
      } else {  //clean
        git.add().addFilepattern(".").call();
        String timeSuffix = DateUtil.format(new Date(), DateUtil.FILE_PATTERN);
        git.commit()
            .setMessage(comment + " " + timeSuffix)
            .call();
        git.push().call();
        System.out.println("------succeed add,commit,push files . to repository at "
            + existingRepo.getDirectory());
      }
    } finally {
      if (git != null) {
        git.close();
      }
    }
  }
}
