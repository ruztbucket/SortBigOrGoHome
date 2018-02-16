package rusty;

import java.io.File;

import java.io.RandomAccessFile;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class MergeEngine {
    File out;
    LargeSort core;    
    long totalSize;
    long currentSize=0;
    boolean done = false;
    
    private double getProgress(){
        return (((double)currentSize)/totalSize)*100;
    }
    public void threadDone()    {
        this.done = true;
    }
    public void setSize(long value){
        currentSize = value;
    }
    
    public MergeEngine(File file, LargeSort core){        
        this.out = file;
        this.core = core;        
        this.totalSize = file.length();
    }
    
    public void startProcess() throws Exception{
        MergeThread asyncProcess = new MergeThread(out,core,this);
        asyncProcess.start();
        
        long startTime = System.nanoTime()-10000000000L;
        while(done == false){
            long now = System.nanoTime();
            if(now - startTime > 10000000000L){
                System.out.print("      Progress - ");
                System.out.printf("%.2f ",this.getProgress());        
                System.out.println("%");
                startTime = now;                
            }
        }
        System.out.println("      Progress - 100.00 % complete");
        System.out.println("   All chunks merged together.");
    }
}
