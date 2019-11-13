package com.gzy.im.controller;

import com.gzy.im.service.AppStorageService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    AppStorageService appStorageService;

    // 上传
    @PostMapping("/upload")
    Object upload(@RequestParam("file") MultipartFile file){
        String id = appStorageService.store(file);
        Map<String,String> resp = new HashMap<>();
        resp.put("fileid",id);
        return resp;
    }

    // 下载
    @GetMapping("/{fid}")
    Object upload(@PathVariable String fid){
        org.springframework.core.io.Resource f = appStorageService.load(fid);

        return ResponseEntity.ok().header(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + f.getFilename() + "\"")
                .body(f);

    }


}
