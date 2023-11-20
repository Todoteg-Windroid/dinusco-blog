package com.todoteg.models;

import java.util.UUID;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageArticle {
	@Id
	private Integer id;
	private UUID imageId;
	private Long articleId;
}
