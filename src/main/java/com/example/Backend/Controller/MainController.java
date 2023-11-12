package com.example.Backend.Controller;

import com.example.Backend.Service.DownloadService;
import com.example.Backend.Service.PythonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;



@Controller
@RequestMapping
public class MainController {


    @Value("${upload.path}")
    private  String pathVideo;


    @Value("${result.path}")
    private String pathResult;

    @Autowired
    private PythonService pythonService;
    @Autowired
    private DownloadService downloadService;




    @GetMapping
    public String startPage(){
        return "index";
    }

    // Вывод в плеер видео с сервера
    @GetMapping(value = "/videosrc")
    @ResponseBody
    public FileSystemResource videoResultSource() {
            return new FileSystemResource(new File("C:\\Users\\Octim\\IdeaProjects\\BackendStavropolRuTube\\src\\main\\resVideo\\result.mp4"));
    }
    @GetMapping(value = "/videoload")
    @ResponseBody
    public FileSystemResource videoLoadSource() {
        return new FileSystemResource(new File("C:\\Users\\Octim\\IdeaProjects\\BackendStavropolRuTube\\src\\main\\resVideo\\load.mp4"));
    }

    @PostMapping("/add")
    public String add(@RequestParam(value = "file",required = true) MultipartFile files
    ) throws IOException {
        //загрузка переданных видео
        downloadService.downloadVideo(files);

        //Запуск pyML
        pythonService.startMl();



        return "index";
    }

}
