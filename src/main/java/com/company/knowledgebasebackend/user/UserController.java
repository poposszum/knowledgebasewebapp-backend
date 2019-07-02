package com.company.knowledgebasebackend.user;

import com.company.knowledgebasebackend.exception.EntityException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public List<UserEntity> getAll(){
        List<UserEntity> allUsers = userRepository.findAll();

        if (allUsers.isEmpty())
            throw new EntityException("There are no users in the Data-base.");

        return allUsers;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Map<String, Boolean> updateUser(@RequestBody UserEntity userEntity){

        userRepository.save(userEntity);
        Map<String, Boolean> response = new HashMap<>();
        response.put("POST", Boolean.TRUE);
        return response;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable(value = "id") ObjectId id) throws
            EntityException {
        UserEntity userEntity = userRepository.findById(id).
                orElseThrow(() -> new EntityException("User with '" + id + "' ID does not exist."));

                return ResponseEntity.ok().body(userEntity);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<UserEntity> updateUserEntity(@PathVariable(value = "id") ObjectId id, @Valid @RequestBody UserEntity userDetails) throws
            EntityException {
        UserEntity userEntity = userRepository.findById(id).
                orElseThrow(() -> new EntityException("User with '" + id + "' ID does not exist."));

        userEntity.setFirstName(userDetails.getFirstName());
        userEntity.setLastName(userDetails.getLastName());
        userEntity.setPassword(userDetails.getPassword());

        final UserEntity updatedUser = userRepository.save(userEntity);
        return ResponseEntity.ok(updatedUser);

    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public Map<String, Boolean> deleteUserEntity(@PathVariable(value = "id") ObjectId id) throws EntityException {
        UserEntity userEntity = userRepository.findById(id).
            orElseThrow(() -> new EntityException("User with '" + id + "' ID does not exist."));

        userRepository.deleteById(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("DELETED", Boolean.TRUE);
        return response;
    }
}
