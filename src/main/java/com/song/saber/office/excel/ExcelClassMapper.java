package com.song.saber.office.excel;

import java.lang.reflect.Field;
import java.util.Iterator;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * Created by 00013708 on 2017/6/9.
 */
public class ExcelClassMapper {

  public T convet(Row row,Class<T> clazz) {
    T t = null;
    try {
      t = clazz.newInstance();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }

    Iterator<Cell> cellIterator = row.cellIterator();

    while (cellIterator.hasNext()) {
      Cell nextCell = cellIterator.next();
      int columnIndex = nextCell.getColumnIndex();

      //根据映射关系得到字段名
      String fieldName = getFieldName(columnIndex);
      if (StringUtils.isEmpty(fieldName)) {
        break;
      }
      try {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(t, getVal(nextCell));
      } catch (NoSuchFieldException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }

      //那么这里设计的应该是位置1，2，3对应到name，age等。
    }
    return t;
  }

  private String getFieldName(int columnIndex) {
    return null;
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
}



