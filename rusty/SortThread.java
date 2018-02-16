package rusty;

import java.io.File;
import java.io.RandomAccessFile;

import java.util.ArrayList;
import java.util.Collections;

public class SortThread extends Thread {
    long id;
    File in = null, out = null;
    long _start, _end;    
    RandomAccessFile fin = null;
    RandomAccessFile fout = null;
    ArrayList<Integer> data;
    double progress = 0;
    SortEngine main = null;
    
    public SortThread(File in, File out, long start, long end, long id, SortEngine main){
        this.in = in;
        this.out = out;
        _start = start;
        _end = end;
        this.id = id;
        this.main = main;
    }
    
    public void run(){
        //System.out.println("Thread-"+Long.toString(id)+" starting, with start-"+Long.toString(_start)+" and end="+Long.toString(_end));        
        
        data = new ArrayList<Integer>();
        try{
            fin = new RandomAccessFile(in,"r");
            fin.seek(_start);
            String line = null;
            while(fin.getFilePointer() != _end){                
                if(id == 1){
                    setProgress(fin.getFilePointer(),1);
                }
                line = fin.readLine();                
                if(line != null){
                    data.add(Integer.parseInt(line.trim()));
                }
            }
            Collections.sort(data);
            setProgress(0,2);
            
            fout = new RandomAccessFile(out,"rw");
            for(int i=0;i<data.size();++i){
                String number = data.get(i).toString()+"\r\n";
                fout.writeBytes(number);                
                setProgress(i,3);
            }
            main.threadDone();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try{
                if(fin != null)
                    fin.close();
                if(fout != null)
                    fout.close();
            } catch(Exception e){
                e.printStackTrace();
            }
        }        
    }

    public void setProgress(long fp, int part) {
        if(part == 1){
            this.progress = (((double)fp-_start)/(_end-_start))*45;
        }
        else if(part == 3){
            this.progress = 55 + (((double)fp)/data.size())*45;
        }
        else
            this.progress = 50.0;
    }

    public double getProgress() {
        return progress;
    }
}
