package main;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	private static XSSFCell Cell;
	private static XSSFWorkbook excelWBook;
	private static XSSFSheet excelWSheet;
	
	private static final int COLUNA_TEST_CASE_NAME = 1;
	
	private static Map<Integer, Integer> findRow(XSSFSheet excelWSheet2, String delimitador) {
		
		Map <Integer, Integer> tabela = new HashMap<Integer, Integer>();

		for (Row row : excelWSheet2) {
			for (Cell cell : row) {
				
				if (cell.getCellType() == CellType.STRING) {
					if (cell.getRichStringCellValue().getString().trim().equals(delimitador)) {
						
						tabela.put(cell.getColumnIndex(), row.getRowNum());
							
					}
				}
			}
		}
		return tabela;
	}
	
	public static Object[][] getTableArray(String FilePath, String SheetName, String casoDeTeste, String delimitador) throws Exception
	{
		
		try {

			// Open the Excel file
			FileInputStream ExcelFile = new FileInputStream(FilePath);

			// Access the required test data sheet
			excelWBook = new XSSFWorkbook(ExcelFile);
			excelWSheet = excelWBook.getSheet(SheetName);
		
			Map <Integer, Integer> tabela = findRow(excelWSheet, delimitador);
			
			int fimMarcador = 0;
			int inicioMarcador = 0;
			
	//		int totalLinhas = 0;
			int posicaoFinalColuna = 0;
			
			// ler a partir de qual coluna ?
			int posicaoInicioCol = 2;

			int ci = 0;
			int cj = 0;
			
            for (Integer key : tabela.keySet()) {
            	
                Integer value = tabela.get(key);
                
                if(key == 0){
                	inicioMarcador = value;
                }else{
                	fimMarcador = value;
                	posicaoFinalColuna = key;
                }
           //     totalLinhas = (fimMarcador -1 ) - (inicioMarcador); 
            }
	            
            int inicioLinha = inicioMarcador + 1; 
    
            String[][] tabArray=null;
            
        //    System.out.println(totalLinhas);
                  
            
            int totalLinhas = getRowCount(casoDeTeste, inicioLinha, fimMarcador);
        
            tabArray=new String[totalLinhas][posicaoFinalColuna-posicaoInicioCol];
                      
            ci=0;

            for (int i=inicioLinha;i<fimMarcador;i++){
                cj=0;
                
                if(getCellData(i, COLUNA_TEST_CASE_NAME).equalsIgnoreCase(casoDeTeste)){
                	                	
                    //especificar as colunas que irao contar na leitura do data driven, a partir de qual numero de coluna
                    for (int j=posicaoInicioCol;j<posicaoFinalColuna;j++,cj++){
                    	
                    	tabArray[ci][cj]=getCellData(i, j);
                    
                   //    System.out.println("tabArray[" + ci + "][" + cj + "]: " + tabArray[ci][cj]);
               
                    }
                	ci++;
                }
            }	
            
            return (tabArray);

		} catch (Exception e) {
			throw (e);
		}		
	}
	
	// This method is to read the test data from the Excel cell, in this we are passing parameters as Row num and Col num

	public static String getCellData(int RowNum, int ColNum) throws Exception {

		try {

			Cell = excelWSheet.getRow(RowNum).getCell(ColNum);

			String CellData = Cell.getStringCellValue();

			return CellData;

		} catch (Exception e) {

			return "";

		}

	}

    private static int getRowCount(String casoDeTeste, int inicioLinha, int fimMarcador) throws Exception{
        /*
         *  This is the method to find the row number
         */    	
    	//sheet.getCellComment(row, column)
    	
		int contador = 0;

		for (int i = inicioLinha; i < fimMarcador; i++) {

			if (getCellData(i, COLUNA_TEST_CASE_NAME).equalsIgnoreCase(casoDeTeste)) {
				contador++;
			}
		}
        return contador;

    }
	
//	public static void main(String[] args) throws Exception {
//				
//		Object[][] testObjArray = getTableArray("test/resources/ddt/UsuarioData.xlsx", "RealizarLogin", "deveRealizarUmLogin", "UC001");
//		
//		System.out.println(testObjArray.length);
//		
//	}

}
