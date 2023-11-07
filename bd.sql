CREATE TABLE users (
    id serial PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL, 
    enabled BOOLEAN NOT NULL
);

CREATE TABLE articles (
    id serial PRIMARY KEY,
    author BIGINT NOT NULL,
    slug VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    content_preview TEXT NOT NULL,
    content_full TEXT NOT NULL,
    summary TEXT NOT NULL,
    enabled BOOLEAN NOT NULL,
    create_date TIMESTAMP NOT NULL,
    update_date TIMESTAMP NOT NULL,
    FOREIGN KEY (author) REFERENCES users(id) 
);

CREATE TABLE tags (
   id serial PRIMARY KEY,
   name VARCHAR(255) NOT NULL
);

CREATE TABLE tag_article (
    id serial PRIMARY KEY,
    id_tag BIGINT NOT NULL,
    id_article BIGINT NOT NULL,
    FOREIGN KEY (id_tag) REFERENCES tags(id), 
    FOREIGN KEY (id_article) REFERENCES articles(id) 
);
