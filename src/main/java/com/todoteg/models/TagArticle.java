package com.todoteg.models;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagArticle {
	private Long id;
	private Long idTag;
	private Long idArticle;
}
