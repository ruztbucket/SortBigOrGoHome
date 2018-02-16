package rusty;

import java.io.File;
import java.io.RandomAccessFile;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class MergeThread extends Thread{
    File out;
    LargeSort core;
    MergeEngine ME;
    List<File> files = null;
    RandomAccessFile fout = null;
    List<RandomAccessFile> streams = null;
    PriorityQueue<Long> pq = null;
    
    public MergeThread(File file, LargeSort core, MergeEngine me){
        pq = new PriorityQueue<Long>();
        streams = new ArrayList<RandomAccessFile>();
        this.out = file;
        this.core = core;
        this.ME = me;
    }
    public void run(){
        try{            
            files = core.getFiles();
            fout = new RandomAccessFile(out,"rw");
            int k = files.size();
            for(int i=0;i<k;++i){
                RandomAccessFile r = new RandomAccessFile(files.get(i),"r");
                String line = r.readLine();
                if(line != null){
                    Long firstValue = Long.parseLong(line.trim());
                    pq.add(firstValue);
                    streams.add(r);            
                }
            }     
        
            while(pq.isEmpty() == false){
                ME.setSize(fout.getFilePointer());
                Long value = pq.poll();
                String svalue = value.toString() + "\r\n";
                fout.writeBytes(svalue);
                for(int i=0;i<k;++i){
                    String line = streams.get(i).readLine();
                    if(line != null){
                        pq.add(Long.parseLong(line.trim()));
                    }
                }
            }            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try{
                for(int i=0;i<streams.size();++i){  
                    if(streams.get(i)!=null)
                        streams.get(i).close();
                }
                fout.close();
            } catch(Exception e){
                e.printStackTrace();
                } finally{
                    ME.threadDone();
                }
        }
    }
}
