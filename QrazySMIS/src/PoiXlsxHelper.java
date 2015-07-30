import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Excel ��ȡ��2007+�¸�ʽ��
 * @author	chengesheng
 * @date	2012-4-27 ����03:39:01
 * @note	PoiExcel2k7Helper
 */
public class PoiXlsxHelper extends PoiExcelHelper {
	/** ��ȡsheet�б� */
	public ArrayList<String> getSheetList(String filePath) {
		ArrayList<String> sheetList = new ArrayList<String>(0);
		try {
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(filePath));
			Iterator<XSSFSheet> iterator = wb.iterator();
			while (iterator.hasNext()) {
				sheetList.add(iterator.next().getSheetName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sheetList;
	}

	/** ��ȡExcel�ļ����� */
	public ArrayList<ArrayList<String>> readExcel(String filePath, int sheetIndex, String rows, String columns) {
		ArrayList<ArrayList<String>> dataList = new ArrayList<ArrayList<String>> ();
		try {
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(filePath));
			XSSFSheet sheet = wb.getSheetAt(sheetIndex);
			
			dataList = readExcel(sheet, rows, getColumnNumber(sheet, columns));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataList;
	}
	
	/** ��ȡExcel�ļ����� */
	public ArrayList<ArrayList<String>> readExcel(String filePath, int sheetIndex, String rows, int[] cols) {
		ArrayList<ArrayList<String>> dataList = new ArrayList<ArrayList<String>> ();
		try {
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(filePath));
			XSSFSheet sheet = wb.getSheetAt(sheetIndex);
			
			dataList = readExcel(sheet, rows, cols);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataList;
	}
}
