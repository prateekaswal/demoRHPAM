package com.myspace.nsp_registration;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class fileUploadUtility {
    
    public String[] fileUploadToDirecotry(File file,String destPath, String fileExtension,String studentid,String stateshortName) {
        String [] uploadStatus=new String [2];
        uploadStatus[0]= "false";
        String autoFileName="";
        try {
            File files = new File(destPath);
                if (!files.exists()) {
                    if (files.mkdirs()) {
                        autoFileName=generateFileName(fileExtension,studentid);
                        File destFile  = new File(destPath, autoFileName);
                        FileUtils.copyFile(file, destFile);
                        uploadStatus[0]="true";    
                        uploadStatus[1]=destPath.concat("/").concat(autoFileName); 
                    } else {
                        }                                      
                } else {            
                    autoFileName=generateFileName(fileExtension,studentid);
                    File destFile  = new File(destPath, autoFileName);
                    FileUtils.copyFile(file, destFile);
                    uploadStatus[0]="true";    
                    uploadStatus[1]=destPath.concat("/").concat(autoFileName); 
                }
        } catch(IOException ex) {  
            ex.printStackTrace(); 
        
        } catch(Exception e) { 
            e.printStackTrace(); 
        }
        return uploadStatus;
    }
    
    public String generateFileName(String filetype,String studentid) {
        String fileId=studentid+filetype;
        return fileId;
    }
    
    public String deletefilefromDirecotry(String filepath) {
        String deleteStatus="false";
        try {
            File file = new File(filepath);
                if (file.exists()) {  
                    if(file.delete()) {
                        deleteStatus="true";
                    }
                }                 
        } catch(Exception e) { 
            e.printStackTrace(); 
        }
        return deleteStatus;
    }
}