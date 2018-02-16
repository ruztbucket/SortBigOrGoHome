package rusty;

import java.io.File;
import java.io.FileInputStream;

import java.io.InputStream;

import java.io.RandomAccessFile;

import java.net.URL;

import java.sql.Time;

public class Test {    
    public static void main(String[] zz) throws Exception{
        long start = System.nanoTime();
        
        LargeSort A = new LargeSort("D:\\SORT_THIS\\data\\decent.txt");
        A.sort();
        
        long end = System.nanoTime();
        System.out.print("\n\nTime take is - ");
        System.out.print((end-start)/60000000000.0);        
        System.out.println(" mins");
        
       /*
        *   Python code to Create sample input
        * 
            import random
            f = open('D:\\SORT_THIS\\big.txt','w')

            try:
                for i in range(100000000):
                f.write(str(random.randint(1,1000000000))+'\n')
            finally:
                f.close()        
        */
    }
}
