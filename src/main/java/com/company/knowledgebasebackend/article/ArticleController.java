package com.company.knowledgebasebackend.article;

import com.company.knowledgebasebackend.exception.EntityException;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/articles")
public class ArticleController {

    private ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository){
        this.articleRepository = articleRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public List<ArticleEntity> getAll(){
        List<ArticleEntity> allArticles = this.articleRepository.findAll();

        if (allArticles.isEmpty())
            throw new EntityException("There are no articles in the data base");

        return allArticles;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Map<String, Boolean> updateArticle(@RequestBody ArticleEntity articleEntity){

        this.articleRepository.save(articleEntity);
        Map<String, Boolean> response = new HashMap<>();
        response.put("POST", Boolean.TRUE);
        return response;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<ArticleEntity> getArticleById(@PathVariable(value = "id") ObjectId id) throws
            EntityException {
        ArticleEntity articleEntity = articleRepository.findById(id).
                orElseThrow(() -> new EntityException("Article with '" + id + "' ID does not exist."));

        return ResponseEntity.ok().body(articleEntity);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<ArticleEntity> updateArticleEntity(@PathVariable(value = "id") ObjectId id, @Valid @RequestBody ArticleEntity articleDetails) throws
            EntityException {
        ArticleEntity articleEntity = this.articleRepository.findById(id).
                orElseThrow(() -> new EntityException("Article with '" + id + "' ID does not exist."));

        articleEntity.setTitle(articleDetails.getTitle());
        articleEntity.setContent(articleDetails.getContent());

        final ArticleEntity updateArticle = this.articleRepository.save(articleEntity);
        return ResponseEntity.ok(updateArticle);

    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public Map<String, Boolean> deleteArticleEntity(@PathVariable(value = "id") ObjectId id) throws EntityException {
        ArticleEntity articleEntity = this.articleRepository.findById(id).
                orElseThrow(() -> new EntityException("Article with '" + id + "' ID does not exist."));

        this.articleRepository.deleteById(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("DELETED", Boolean.TRUE);
        return response;
    }

}
