package com.example.vpapi.controller;

import com.example.vpapi.dto.VideoDTO;
import com.example.vpapi.service.VideoService;
import com.example.vpapi.util.CustomFileUtil;
import com.example.vpapi.util.ImageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/videos")
public class VideoController {

    private final VideoService videoService;
    private final CustomFileUtil fileUtil;

    @GetMapping("/{vno}")
    public VideoDTO get(@PathVariable("vno") Long vno) {
        return videoService.get(vno);
    }

    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable String fileName) {
        return fileUtil.getFile(fileName);
    }

    @PostMapping("/")
    @PreAuthorize("#videoDTO.uno == authentication.principal.mno")
    public Map<String, Long> register(VideoDTO videoDTO) throws IOException {
        MultipartFile file = videoDTO.getFile();
        String uploadFileName = fileUtil.saveFile(file, ImageType.IMAGE);
        videoDTO.setFileName(uploadFileName);
        log.info(uploadFileName);
        Long vno = videoService.register(videoDTO);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return Map.of("vno", vno);
    }

    @DeleteMapping("/{vno}")
    public Map<String, String> remove(@PathVariable(name="vno") Long vno) {
        String oldFileName = videoService.get(vno).getFileName();
        videoService.remove(vno);
        fileUtil.deleteFile(oldFileName);
        return Map.of("RESULT", "SUCCESS");
    }

}
