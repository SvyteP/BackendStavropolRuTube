package com.example.Backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class PythonService {
    @Value("${pyMl.path}")
    private String pathPyMl;

    @Value("${pyName.file}")
    private String pythonScript;
    @Value("${upload.path}")
    private  String pathVideo;
    @Value("${send.file}")
    private String sendFile;

    private String textSendFile;


    private BufferedReader readerSend;
    private List<String> listForDelFile = new ArrayList<>();
    public String startMl(){



        System.out.println("send: "+pathVideo+sendFile);
        try{
            readerSend =new BufferedReader(new FileReader(pathVideo+sendFile));

            String tmpTextSendFile=null;
            while (  (tmpTextSendFile = readerSend.readLine()) !=null) {

                // read next line
                textSendFile = tmpTextSendFile;

            }
            System.out.println("textSendFile "+textSendFile);
            Process process  = new ProcessBuilder("python",pathPyMl+pythonScript, textSendFile).start();



            int exitCode = process.waitFor();// Ожидание завершения скрипта
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader((process.getInputStream())));

            String line;

            while ((line = reader.readLine()) != null){
                System.out.println(line); //Вывод результата
            }

            System.out.println("Выполнение Python-скрипта завершено с кодом: " + exitCode);
             String tmpFileName = "";

           for (int i = 0; i<textSendFile.length();i++)
            {
                if (textSendFile.charAt(i) != '|'){
                    tmpFileName+=textSendFile.charAt(i);
                }
                else {
                    listForDelFile.add(tmpFileName);
                    tmpFileName = "";
                }
            }

           for (String a:
                 listForDelFile) {

               File file = new File(pathVideo+"\\"+a);
              if (file.delete())
               {
                   System.out.println("Oke Del");


               }
               else
                   System.out.println("Err Del");


           }
            listForDelFile.clear();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Ok";
    }


}
