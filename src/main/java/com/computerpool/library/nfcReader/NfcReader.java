package com.computerpool.library.nfcReader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class NfcReader {

        public static String nfcReader(){

                String command = "python3";
                String line="";
                
            if(checkNFCReaderStatus()){
                ProcessBuilder processBuilder = new ProcessBuilder(command, "/home/gstark/nfcWriter.py");
                
            try{
                Process process = processBuilder.start();
                InputStream inputStream = process.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                
                line = reader.readLine();
    
            }
            catch (Exception e){
                System.out.println(e);
                
            }

            return line;
        }
        else{
            return line;
        }

            
        }
                
                
    


    public static Boolean checkNFCReaderStatus(){

                String command = "python3";
                
                
            
                ProcessBuilder processBuilder = new ProcessBuilder(command, "/home/gstark/nfcStatus.py");
                
            try{
                Process process = processBuilder.start();

                int exitcode=process.waitFor();
                
                if (exitcode==0){
                return true;    

                }
                else{
                    return false;
                }
    
            }
            catch (Exception e){
                System.out.println(e);
                return false;
            }

            
                
    }


}

