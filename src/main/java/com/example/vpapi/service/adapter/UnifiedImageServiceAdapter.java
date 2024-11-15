package com.example.vpapi.service.adapter;

import com.example.vpapi.dto.ImageDTO;
import com.example.vpapi.dto.ProfileImageDTO;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Map;

@Transactional
public interface UnifiedImageServiceAdapter {

    ImageDTO getImage(Long ino);

    ProfileImageDTO getProfileImage(Long pino);

    Boolean existsProfileImageByMno(Long mno);

    ProfileImageDTO getProfileImageByMno(Long mno);

    Map<String, String> viewImage(Long ino) throws IOException;

    Map<String, String> viewImageThumbnail(Long ino) throws IOException;

    ResponseEntity<Resource> viewProfileImage(Long pino);

    ResponseEntity<Resource> viewProfileImageThumbnail(Long pino);

    ResponseEntity<Resource> viewProfileImageByMno(Long mno);

    ResponseEntity<Resource> viewProfileImageThumbnailByMno(Long mno);

    Map<String, Long> registerImage(ImageDTO imageDTO) throws IOException;

    Map<String, Long> registerProfileImage(ProfileImageDTO profileImageDTO);

    void removeImage(Long ino);

    void removeProfileImage(Long pino);

    void removeProfileImageByMno(Long mno);

}
