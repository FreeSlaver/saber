package com.song.saber.office.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Created by 00013708 on 2017/6/9.
 */
public class ExcelUtil {
  public Workbook getWorkbook(String excelFilePath)
      throws IOException {
    if (StringUtils.isEmpty(excelFilePath)) {
      throw new IllegalArgumentException("excelFilePath empty");
    }
    File file = new File(excelFilePath);
    return file.exists() ? getWorkbook(file) : null;
  }

  public Workbook getWorkbook(File file) throws IOException {
    if (file == null) {
      throw new IllegalArgumentException("file null");
    }
    if (!file.exists()) {
      throw new IllegalArgumentException("file not exists");
    }
    FileInputStream fileInputStream = new FileInputStream(file);
    Workbook workbook = null;
    String fileName = file.getName();
    if (fileName.endsWith("xlsx")) {
      workbook = new XSSFWorkbook(fileInputStream);
    } else if (fileName.endsWith("xls")) {
      workbook = new HSSFWorkbook(fileInputStream);
    } else {
      throw new IllegalArgumentException("The specified file is not Excel file");
    }
    return workbook;
  }

  private Object getVal(Cell cell) {
    switch (cell.getCellTypeEnum()) {
      case STRING:
        return cell.getStringCellValue();

      case BOOLEAN:
        return cell.getBooleanCellValue();

      case NUMERIC:
        return cell.getNumericCellValue();
    }

    return null;
  }

  //这里还缺一个参数，描述对象在sheet中的位置的
/*  public List<T> readBooksFromExcelFile(String excelFilePath,int sheetIndex,T t) throws IOException {
    Workbook workbook = getWorkbook(excelFilePath);

    Sheet firstSheet = workbook.getSheetAt(sheetIndex);
    Iterator<Row> rowIteratoriterator = firstSheet.iterator();

    while (iterator.hasNext()) {
      Row nextRow = iterator.next();
      Iterator<Cell> cellIterator = nextRow.cellIterator();
      Book aBook = new Book();


      while (cellIterator.hasNext()) {
        Cell nextCell = cellIterator.next();
        int columnIndex = nextCell.getColumnIndex();
        nextCell.ge
        switch (columnIndex) {
          case 1:
            aBook.setTitle((String) getCellValue(nextCell));
            break;
          case 2:
            aBook.setAuthor((String) getCellValue(nextCell));
            break;
          case 3:
            aBook.setPrice((double) getCellValue(nextCell));
            break;
        }


      }
      listBooks.add(aBook);
    }

    workbook.close();
    inputStream.close();

    return listBooks;
  }*/
}
