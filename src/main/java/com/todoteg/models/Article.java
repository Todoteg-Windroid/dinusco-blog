package com.todoteg.models;

import java.time.LocalDateTime;

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
    private String contentPreview; 
    private String contentFull;
    private String summary;
    @Builder.Default private boolean enabled =  true;
    @Builder.Default private LocalDateTime createDate = LocalDateTime.now();
    @Builder.Default private LocalDateTime updateDate = LocalDateTime.now();
    
}
