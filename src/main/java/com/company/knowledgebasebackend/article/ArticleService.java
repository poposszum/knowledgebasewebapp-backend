package com.company.knowledgebasebackend.article;

import com.company.knowledgebasebackend.user.UserEntity;
import com.company.knowledgebasebackend.user.UserException;
import com.company.knowledgebasebackend.user.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Contains all the Article related services.
 */

@Service
public class ArticleService {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleRepository articleRepository;

    private static final String ERROR_SAVING_MESSAGE = "There was a problem saving the article";

    public ArticleEntity save(ArticleEntity articleEntity) {
        return this.articleRepository.save(articleEntity);
    }

    public ArticleEntity findArticleById(ObjectId id) {
        return articleRepository.findArticleById(id);
    }

    public void deleteAll() {
        articleRepository.deleteAll();
    }

    public List<ArticleEntity> getAllArticles() {
        return articleRepository.findAll();
    }

    public void deleteById(ObjectId id){
        articleRepository.deleteById(id);
    }

    /**
     * Creates a new article.
     * Checks if the user from the jwt token exists, then builds a new article entity. Saves to the database.
     * @param newArticleRequest
     * @param request
     * @throws ArticleException
     * @throws UserException
     */
    public void createArticle(ArticleEntity newArticleRequest, HttpServletRequest request) throws ArticleException, UserException {
        UserEntity user = userService.getUserFromJwt(request);
        if (user == null) {
            throw new UserException("User not found");
        }

        ArticleEntity newArticle = ArticleEntity.builder()
                .title(newArticleRequest.getTitle())
                .content(newArticleRequest.getContent())
                .authors(Arrays.asList(user.getId()))
                .dateCreated(new Date())
                .dateModified(new Date())
                .build();
        if (save(newArticleRequest) != null) {
            save(newArticle);
        } else throw new ArticleException(ERROR_SAVING_MESSAGE);
    }

    /**
     * Edits an existing article.
     * Checks if the user from the jwt token exists, then searches by ObjectId for the article which will be edited.
     * Updates the article, then sets the last modification date to the current date.
     * If the user who requested an edit has already edited the article, nothing happens, otherwise the editors ObjectId is added to the authors list.
     * Saves the edited article to the database.
     * @param editArticleRequest
     * @param request
     * @throws UserException
     * @throws ArticleException
     */
    public void editArticle(ArticleEntity editArticleRequest, HttpServletRequest request) throws UserException, ArticleException {
        UserEntity user = userService.getUserFromJwt(request);
        if (user == null) {
            throw new UserException("User not found");
        }

        ArticleEntity currentArticle = findArticleById(editArticleRequest.getId());

        if (currentArticle == null) {
            throw new ArticleException("Article not found");
        }

        currentArticle.setTitle(editArticleRequest.getTitle());
        currentArticle.setContent(editArticleRequest.getContent());
        currentArticle.setDateModified(new Date());

        if (!currentArticle.getAuthors().contains(editArticleRequest.getId())) {
            currentArticle.addAuthor(user.getId());
        }

        if (save(currentArticle) == null) {
            throw new ArticleException(ERROR_SAVING_MESSAGE);
        }
        save(currentArticle);
    }

    /**
     * Deletes an article.
     * Checks if the user from the jwt token exists. Checks if the user has the right to delete the article, if so the article is going to be deleted.
     * @param deleteArticleRequest
     * @param request
     * @throws UserException
     * @throws ArticleException
     */
    public void deleteArticle(ArticleEntity deleteArticleRequest, HttpServletRequest request) throws UserException, ArticleException {
        UserEntity user = userService.getUserFromJwt(request);
        if (user == null) {
            throw new UserException("User not found");
        }

        if (!deleteArticleRequest.getAuthors().contains(user.getId())){
            throw new ArticleException("You have no right to delete this article");
        }

        try {
            deleteById(deleteArticleRequest.getId());
        } catch (IllegalArgumentException e) {
            throw new ArticleException(e.getMessage());
        }
    }
}
