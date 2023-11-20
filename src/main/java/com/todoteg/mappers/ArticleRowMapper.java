package com.todoteg.mappers;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.postgresql.util.PGobject;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.todoteg.models.Article;
@Component
public class ArticleRowMapper implements RowMapper<Article>{
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
    public Article mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		LocalDateTime createDate = resultSet.getTimestamp("create_date").toLocalDateTime();
		LocalDateTime updateDate = resultSet.getTimestamp("update_date").toLocalDateTime();

	    String AuthorUsername;
	    try {
	    	AuthorUsername= resultSet.getString("username");	    	
	    }catch(SQLException e) {
	    	AuthorUsername = null;
	    }
		
        Article article =  Article.builder()
        		.id(resultSet.getLong("id"))
        		.author(resultSet.getLong("author"))
        		.authorUsername(AuthorUsername != null ? AuthorUsername : null)
        		.slug(resultSet.getString("slug"))
        		.title(resultSet.getString("title"))
        		.createDate(createDate)
        		.updateDate(updateDate)
        		.enabled(resultSet.getBoolean("enabled"))
        		.build();
        
        PGobject contentFull = new PGobject();
        contentFull.setType("jsonb");
        contentFull.setValue(resultSet.getString("content_full"));

        
        article.setContentFull(contentFull);
        return setPreviewAndSummaryArticle(article);
    }
	
	public Article setPreviewAndSummaryArticle(Article article) {
		PGobject content = article.getContentFull();
		JsonNode contentOpsQuill = parseContentOps(content.getValue());
		
		JsonNode preview = generatePreview(contentOpsQuill);
		String summary = generateSummary(preview, 200);
		
		article.setContentPreview(preview);
		article.setSummary(summary);
        return article;
	}
	public String generateSummary(JsonNode rootNode, int maxCharacters) {
		StringBuilder summary = new StringBuilder();
        int countedCharacters = 0;
        
        for (JsonNode op : rootNode.get("ops")) {
            if (countedCharacters >= maxCharacters) {
                break;
            }
            JsonNode insertNode = op.get("insert");
            if (insertNode != null && insertNode.isTextual()) {
                String text = insertNode.asText();
                int charactersRemaining = maxCharacters - countedCharacters;

                if (text.length() <= charactersRemaining) {
                	summary.append(text);
                	countedCharacters += text.length();
                }else {
                	summary.append(text, 0, charactersRemaining).append("...");
                	countedCharacters += charactersRemaining;
                }
            }
        }

        return summary.toString().trim();
    }
	private JsonNode generatePreview(JsonNode contentOps) {
		ArrayNode firstPart = objectMapper.createArrayNode();
		
        for (JsonNode op : contentOps.get("ops")) {
            if (op.has("insert") && "[MORE]".equals(op.get("insert").asText()) && op.has("attributes")) {
                break;
            }else {
            	firstPart.add(op);
            }
        }
        ObjectNode previewResult = objectMapper.createObjectNode();
        previewResult.set("ops", firstPart);
        return previewResult;
    }
	public JsonNode parseContentOps(String content) {
	    try {
	        return objectMapper.readTree(content);
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
}
