package two_state_Simulation;

import java.io.File;

import jxl.*;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class exportToExcel {
	
	public exportToExcel(double[][] data) {
		try{
			WritableWorkbook w = Workbook.createWorkbook(new File("data3.xls"));
			WritableSheet sheet = w.createSheet("data", 0);
			sheet.addCell(new Label(1,0,"departure at state 0"));
			sheet.addCell(new Label(2,0,"departure at state 1"));
			sheet.addCell(new Label(3,0,"state transition at state 0 when idle"));
			sheet.addCell(new Label(4,0,"state transition at state 1 when idle"));
			sheet.addCell(new Label(5,0,"state transition at state 0 when busy"));
			sheet.addCell(new Label(6,0,"state transition at state 1 when busy"));
			sheet.addCell(new Label(7,0,"arrival sees queue length"));
			sheet.addCell(new Label(8,0,"departure sees queue length"));
			
			for(int i=1;i<data[0].length+1;i++)
				sheet.addCell(new jxl.write.Number(0,i,0.01*(i-0.5)));
			
			for(int i=0;i<data.length;i++)
				for(int j=1;j<data[i].length+1;j++)
					sheet.addCell(new jxl.write.Number(i+1,j,data[i][j-1]));
				
			w.write();
			w.close();
		
		}catch (Exception e) {
			System.out.println(e);
		}
	}
}
