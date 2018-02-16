package rusty;

import java.io.File;

import java.util.List;

public class ScraperEngine {
    private LargeSort core = null;
    public ScraperEngine(LargeSort ref){
        core = ref;
    }
    public void startProcess() throws Exception{
        List<File> files = core.getFiles();
        for(int i=0;i<files.size();++i)
            if(files.get(i).delete()==false){               
               System.out.println(files.get(i).getName() + " not deleted! (warning)");
            }
        
        
        System.out.println("   Cleanup over.");
    }
}
