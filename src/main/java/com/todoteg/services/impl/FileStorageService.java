package com.todoteg.services.impl;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
//import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;


import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.todoteg.models.Article;
import com.todoteg.models.Image;
import com.todoteg.models.ImageArticle;
import com.todoteg.repositories.IFileArticleRepo;
import com.todoteg.repositories.IFileRepo;

@Service
public class FileStorageService {

	private final String idTempArticle= "c9093aed-8fff-438b-b31c-30a4a4ef4848";
    private String uploadDir = "/ImageServer/";
    private ArrayList<Image> images = new ArrayList<Image>();
	
	@Autowired
	IFileArticleRepo repoImageArticle;
	
	@Autowired
	IFileRepo repoImage;
	
	
    private final Path fileStorageLocation;

    public FileStorageService() {
    	if (uploadDir == null || uploadDir.isEmpty()) {
            throw new RuntimeException("La propiedad 'ruta.almacenamiento.archivos' no está configurada.");
        }

        this.fileStorageLocation = Path.of(uploadDir).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("No se pudo crear el directorio para almacenar archivos", ex);
        }
    }

    public Path fileSave(MultipartFile archivo, Long userId) {
        try {
        	Path userFolder = this.fileStorageLocation.resolve(userId.toString());
        	if(!Files.exists(userFolder)) Files.createDirectory(userFolder);
            String fileName = archivo.getOriginalFilename().replace(" ", "-");
            Path targetLocation = userFolder.resolve(fileName).normalize();
            if(Files.exists(targetLocation)) {
            	// Calcular el hash SHA-256 de la nueva imagen
                String newImageHash = DigestUtils.sha256Hex(archivo.getInputStream());
                // Calcular el hash SHA-256 de la imagen existente
                String existingImageHash = DigestUtils.sha256Hex(Files.readAllBytes(targetLocation));
                // Comparar los hash
                if (newImageHash.equals(existingImageHash))
                	return targetLocation;
                fileName = idTempArticle.concat("_").concat(fileName);
            	targetLocation = userFolder.resolve(fileName).normalize();
            }
            Files.copy(archivo.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        	Image image = Image.builder()
            		.name(fileName)
            		.userUpdate(userId)
            		.path(targetLocation.toString())
            		.build();
            repoImage.save(image);
            images.add(image);
            
            return targetLocation;
            
        } catch (Exception ex) {
            throw new RuntimeException("Error al almacenar el archivo", ex);
        }
    }
    public void fileSave(Article article) {
    	images.stream()
    			.filter(image-> image.getUserUpdate().equals(article.getAuthor())).toList()
    			.forEach(image -> {
    				ImageArticle imageArticle= ImageArticle.builder()
    		                .articleId(article.getId())
    		                .imageId(image.getId())
    		                .build();
    		                
    		                repoImageArticle.save(imageArticle);
    		                images.remove(image);
    			});
    }
    public void deleteFile(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName);
            Files.deleteIfExists(filePath);
            //if (images.contains(filePath)) images.remove(filePath);
        } catch (IOException ex) {
            throw new RuntimeException("Error al eliminar el archivo", ex);
        }
    }
}
