package utilities;

import java.util.ArrayList;

/**
 * ExcelͳһPOI��������ࣨ���2003��ǰ��2007�Ժ����ָ�ʽ�ļ��ݴ���
 * @author	chengesheng
 * @date	2012-5-3 ����03:10:23
 * @note	PoiHelper
 */
public abstract class PoiExcelTest {
	// *************************************************
	// ================����Ϊ���Դ���====================
	// *************************************************
	public static void main(String[] args){
		// ��ȡExcel�ļ���sheet�б�
		testGetSheetList("C:\\Users\\����\\OneDrive\\��Ŀ\\S-ɽ����Դ\\99�м���Ŀ\\�ൺ\\�ൺ�б��۵�v1.0.xlsx");
		
//		// ��ȡExcel�ļ��ĵ�1��sheet������
//		testReadExcel("C:\\Users\\����\\OneDrive\\�ĵ�\\��˾����\\�ֹ�˾��Ӫ\\01.�칫�����嵥.xlsx", 0);
		
		// ��ȡExcel�ļ��ĵ�2��sheet�ĵ�2��4-7�к͵�10�м��Ժ������
		testReadExcel("C:\\Users\\����\\OneDrive\\��Ŀ\\S-ɽ����Դ\\99�м���Ŀ\\�ൺ\\�ൺ�б��۵�v1.0.xlsx", 
				1, "2,4-7,10-");
//		
//		// ��ȡExcel�ļ��ĵ�3��sheet��a,b,g,h,i,j���е���������
//		testReadExcel("c:/test.xls", 2, new String[] {"a","b","g","h","i","j"});
//		
//		// ��ȡExcel�ļ��ĵ�4��sheet�ĵ�2��4-7�к͵�10�м��Ժ�a,b,g,h,i,j���е�����
//		testReadExcel("c:/test.xlsx", 3, "2,4-7,10-", new String[] {"a","b","g","h","i","j"});
	}
	
	// ���Ի�ȡsheet�б�
	private static void testGetSheetList(String filePath) {
		PoiExcelHelper helper = getPoiExcelHelper(filePath);
		
		// ��ȡSheet�б�
		ArrayList<String> sheets = helper.getSheetList(filePath);
		
		// ��ӡExcel��Sheet�б�
		printList(filePath, sheets);
	}
	
	// ����Excel��ȡ
	private static void testReadExcel(String filePath, int sheetIndex) {
		PoiExcelHelper helper = getPoiExcelHelper(filePath);
		
		// ��ȡexcel�ļ�����
		ArrayList<ArrayList<String>> dataList = helper.readExcel(filePath, sheetIndex);
		
		// ��ӡ��Ԫ������
		printBody(dataList);
	}
	
	// ����Excel��ȡ
	private static void testReadExcel(String filePath, int sheetIndex, String rows) {
		PoiExcelHelper helper = getPoiExcelHelper(filePath);
		
		// ��ȡexcel�ļ�����
		ArrayList<ArrayList<String>> dataList = helper.readExcel(filePath, sheetIndex, rows);
		
		// ��ӡ��Ԫ������
		printBody(dataList);
	}
	
	// ����Excel��ȡ
	private static void testReadExcel(String filePath, int sheetIndex, String[] columns) {
		PoiExcelHelper helper = getPoiExcelHelper(filePath);
		
		// ��ȡexcel�ļ�����
		ArrayList<ArrayList<String>> dataList = helper.readExcel(filePath, sheetIndex, columns);
		
		// ��ӡ�б���
		printHeader(columns);
		
		// ��ӡ��Ԫ������
		printBody(dataList);
	}
	
	// ����Excel��ȡ
	private static void testReadExcel(String filePath, int sheetIndex, String rows, String[] columns) {
		PoiExcelHelper helper = getPoiExcelHelper(filePath);
		
		// ��ȡexcel�ļ�����
		ArrayList<ArrayList<String>> dataList = helper.readExcel(filePath, sheetIndex, rows, columns);
		
		// ��ӡ�б���
		printHeader(columns);
		
		// ��ӡ��Ԫ������
		printBody(dataList);
	}
	
	// ��ȡExcel������
	private static PoiExcelHelper getPoiExcelHelper(String filePath) {
		PoiExcelHelper helper;
		if(filePath.indexOf(".xlsx")!=-1) {
			helper = new PoiXlsxHelper();
		}else {
			helper = new PoiXlsHelper();
		}
		return helper;
	}

	// ��ӡExcel��Sheet�б�
	private static void printList(String filePath, ArrayList<String> sheets) {
		System.out.println();
		for(String sheet : sheets) {
			System.out.println(filePath + " ==> " + sheet);
		}
	}

	// ��ӡ�б���
	private static void printHeader(String[] columns) {
		System.out.println();
		for(String column : columns) {
			System.out.print("\t\t" + column.toUpperCase());
		}
	}

	// ��ӡ��Ԫ������
	private static void printBody(ArrayList<ArrayList<String>> dataList) {
		int index = 0;
		for(ArrayList<String> data : dataList) {
			index ++;
			System.out.println();
			System.out.print(index);
			for(String v : data) {
				System.out.print("\t\t" + v);
			}
		}
	}
}
