import java.io.File;
import java.io.FileOutputStream;

import jxl.*;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class exportToExcel {
	
	public exportToExcel(double[][] data, String para, String result) {
		try{
			WritableWorkbook w = Workbook.createWorkbook(new FileOutputStream("data.xls"));
			WritableSheet sheet = w.createSheet("data", 0);
			sheet.addCell(new Label(0, 1, para));
			sheet.addCell(new Label(0, 2, result));

			for(int i=0;i<data[0].length;i++)
				sheet.addCell(new jxl.write.Number(i+1, 1, data[0][i]));
			for(int i=0;i<data[1].length;i++)
				sheet.addCell(new jxl.write.Number(i+1, 2, data[1][i]));
				
			w.write();
			w.close();
		
		}catch (Exception e) {
			System.out.println(e);
		}
	}
}
