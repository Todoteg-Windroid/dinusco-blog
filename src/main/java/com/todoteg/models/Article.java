package com.todoteg.models;

import java.time.LocalDateTime;

import org.postgresql.util.PGobject;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article {
	private Long id;
	private Long author;
	private String authorUsername;
	private String slug;
    private String title;
    private JsonNode contentPreview; 
    private PGobject contentFull;
    private String summary;
    @Builder.Default private boolean enabled =  true;
    private LocalDateTime createDate;
    @Builder.Default private LocalDateTime updateDate = LocalDateTime.now();
    
    
}
