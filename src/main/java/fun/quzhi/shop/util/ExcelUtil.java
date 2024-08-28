package fun.quzhi.shop.util;

import org.apache.poi.ss.usermodel.Cell;

/**
 * excel工具类
 */
public class ExcelUtil {
    public static Object getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return cell.getNumericCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
        }
        return null;
    }
}
