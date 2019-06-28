package com.company.knowledgebasebackend.user;

import com.company.knowledgebasebackend.exception.EntityException;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserEntityController {
    private UserEntityRepository userEntityRepository;

    public UserEntityController(UserEntityRepository userEntityRepository){
        this.userEntityRepository = userEntityRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public List<UserEntity> getAll(){
        List<UserEntity> allUsers = this.userEntityRepository.findAll();

        if (allUsers.isEmpty())
            throw new EntityException("There are no users in the Data-base.");

        return allUsers;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Map<String, Boolean> updateUser(@RequestBody UserEntity userEntity){

        this.userEntityRepository.save(userEntity);
        Map<String, Boolean> response = new HashMap<>();
        response.put("POST", Boolean.TRUE);
        return response;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable(value = "id") ObjectId id) throws
            EntityException {
        UserEntity userEntity = userEntityRepository.findById(id).
                orElseThrow(() -> new EntityException("User with '" + id + "' ID does not exist."));

                return ResponseEntity.ok().body(userEntity);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<UserEntity> updateUserEntity(@PathVariable(value = "id") ObjectId id, @Valid @RequestBody UserEntity userDetails) throws
            EntityException {
        UserEntity userEntity = this.userEntityRepository.findById(id).
                orElseThrow(() -> new EntityException("User with '" + id + "' ID does not exist."));

        userEntity.setFirstName(userDetails.getFirstName());
        userEntity.setLastName(userDetails.getLastName());
        userEntity.setPassword(userDetails.getPassword());
        userEntity.setArticles(userDetails.getArticles());

        final UserEntity updatedUser = this.userEntityRepository.save(userEntity);
        return ResponseEntity.ok(updatedUser);

    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public Map<String, Boolean> deleteUserEntity(@PathVariable(value = "id") ObjectId id) throws EntityException {
        UserEntity userEntity = this.userEntityRepository.findById(id).
            orElseThrow(() -> new EntityException("User with '" + id + "' ID does not exist."));

        this.userEntityRepository.deleteById(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("DELETED", Boolean.TRUE);
        return response;
    }
}
