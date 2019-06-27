/*
 * Authors: Andrea Papiri,Giacomo Ciucaloni
 * Version: 1.0
 * Update: 21/6/19
 */
//non usato nel programma finale ma è servito per fare il test in lettura
package programma;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;

public class test_csv {
	public test_csv()throws Exception {
		File file=new File("./csv");
		InputStream in=new FileInputStream(file);
		String data="";
		byte buffer[]=new byte[1024];
		int reads=0;
		while(reads!=-1) {
			data+=new String(Arrays.copyOfRange(buffer, 0, reads));
			reads=in.read(buffer);
		}
		System.out.println(data);
	}

}
