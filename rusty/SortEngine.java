package rusty;

import java.io.File;

import java.io.RandomAccessFile;

import java.util.ArrayList;
import java.util.List;

public class SortEngine {
    RandomAccessFile fin = null;
    private LargeSort core = null;
    private File mainFile = null;
    private List<SortThread> sorters = null;
    int count = 0;
    
    public SortEngine(File file, LargeSort ref){
        mainFile = file;
        core = ref;        
        sorters = new ArrayList<SortThread>();        
    }
    synchronized public void threadDone(){
        count++;
    }
    public void startProcess() throws Exception{
        preProcess();                
        
        System.out.println("   Waiting for all Threads to Complete...");
        
        for(int i=0;i<4;++i){                     
            sorters.get(i).start();                                 
        }
        long startTime = System.nanoTime()-10000000000L;
        while(count != 4){
            long now = System.nanoTime();
            if(now - startTime > 10000000000L){
                System.out.print("      Progress - ");
                System.out.printf("%.2f ",sorters.get(0).getProgress());        
                System.out.println("%");
                startTime = now;                
            }
        }
        System.out.println("      Progress - 100.00 % complete");
        System.out.println("   All chunks Sorted.");
    }    
    private void preProcess() throws Exception{        
        
        long m1,m2,m3,m4;
        try{
            fin = new RandomAccessFile(mainFile,"r");
            
            m2 = getFilePointer(mainFile.length()/2);
            m1 = getFilePointer(mainFile.length()/4);
            m3 = getFilePointer((mainFile.length()*3)/4);
            m4 = mainFile.length();
            
            makeThread(mainFile,new File("_temp1.txt"),0,m1);
            makeThread(mainFile,new File("_temp2.txt"),m1,m2);
            makeThread(mainFile,new File("_temp3.txt"),m2,m3);
            makeThread(mainFile,new File("_temp4.txt"),m3,m4);
            
        } 
        finally{
            if(fin != null)   
                fin.close();
        }       
    }
    
    private void makeThread(File in, File out, long start, long end){
        sorters.add(new SortThread(in,out,start,end,sorters.size()+1,this));
        core.addFile(out);
    }
    
    private long getFilePointer(long somePoint) throws Exception{        
        String line = null;        
        fin.seek(somePoint);
        line = fin.readLine();
        long result =  fin.getFilePointer();
        //System.out.println("in-"+Long.toString(somePoint)+" out-"+Long.toString(result));
        return result;
    }
}
