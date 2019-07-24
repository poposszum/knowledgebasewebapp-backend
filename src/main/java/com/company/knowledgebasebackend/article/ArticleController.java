package com.company.knowledgebasebackend.article;

import com.company.knowledgebasebackend.common.ApiResponse;
import com.company.knowledgebasebackend.user.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Handles all the article related request.
 */
@RestController
@RequestMapping("api/v1/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity<ApiResponse> createNewArticle(@RequestBody ArticleEntity newArticleRequest, HttpServletRequest request) throws ArticleException, UserException {

        try {
            articleService.createArticle(newArticleRequest, request);
        } catch (ArticleException e) {
            throw new ArticleException(e.getMessage());
        } catch (UserException e) {
            throw new UserException(e.getMessage());
        }

        return new ResponseEntity<>(new ApiResponse(true, "Article was created successfully"), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/edit")
    public ResponseEntity<ApiResponse> editArticle(@RequestBody ArticleEntity editArticleRequest, HttpServletRequest request) throws ArticleException, UserException {
        try {
            articleService.editArticle(editArticleRequest, request);
        } catch (ArticleException e) {
           throw new ArticleException(e.getMessage());
        } catch (UserException e) {
            throw new UserException(e.getMessage());
        }

        return new ResponseEntity<>(new ApiResponse(true, "Article was edited successfully"), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    public ResponseEntity<ApiResponse> deleteArticle(@RequestBody ArticleEntity deleteArticleRequest, HttpServletRequest request) throws ArticleException, UserException {
        try {
            articleService.deleteArticle(deleteArticleRequest, request);
        } catch (ArticleException e) {
            throw new ArticleException(e.getMessage());
        } catch (UserException e) {
            throw new UserException(e.getMessage());
        }

        return new ResponseEntity<>(new ApiResponse(true, "Article was deleted successfully"), HttpStatus.OK);
    }
}
