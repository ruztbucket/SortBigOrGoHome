package rusty;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

public class LargeSort {
    private File file = null;
    private SortEngine SE = null;
    private MergeEngine ME = null;
    private ScraperEngine ScE = null;
    private List<File> files;
    
    public LargeSort(String filePath){
        file = new File(filePath);
        files = new ArrayList<File>();
        SE = new SortEngine(file,this);
        ME = new MergeEngine(file,this);
        ScE = new ScraperEngine(this);        
    }
    public void sort(){
        try{
            System.out.println("SortEngine Started.");
            SE.startProcess();
            System.out.println("MergeEngine Started.");
            ME.startProcess();
            System.out.println("Data Scraping Started.");
            ScE.startProcess();
            System.out.println("Sort Complete :)");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void addFile(File f){
        files.add(f);
    }
    public List<File> getFiles(){
        return files;
    }
}
