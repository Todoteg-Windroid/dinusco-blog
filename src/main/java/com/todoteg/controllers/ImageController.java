package com.todoteg.controllers;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.todoteg.security.SecurityUser;
import com.todoteg.services.impl.FileStorageService;

@RestController
public class ImageController {
	@Autowired
	FileStorageService service;
	
	@PostMapping({"/upload/","upload"})
    public ResponseEntity<String> save(@RequestParam("image") MultipartFile image) throws IOException {
		SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Path urlFile = service.fileSave(image, user.getId());
		Path folderName = urlFile.getParent();
		
		String rutaRelativa = "/image/" + folderName.getFileName()  +"/"+urlFile.getFileName().normalize();
		
		return ResponseEntity.ok().body("{\"imageUrl\": \"" + rutaRelativa + "\"}");

    }
	
	@DeleteMapping("/delete/{fileName}")
	public ResponseEntity<String> delete(@PathVariable String fileName) {
	    service.deleteFile(fileName);
	    return ResponseEntity.ok().body("Imagen eliminada con éxito");
	}

}
