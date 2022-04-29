package com.ecommerce.api;

import com.ecommerce.dto.domain.*;
import com.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserResource {
    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<PageUserDTO> findAll(@RequestParam(required = false,defaultValue = "1") int page,
                                              @RequestParam(required = false,defaultValue = "10") int size){
        return  ResponseEntity.ok().body(userService.findAll(page,size));
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> findByID(@PathVariable("id") Long id){
        return  ResponseEntity.ok().body(userService.findById(id));
    }
    @PostMapping(value = "/user",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> saveUser(@Valid @RequestBody RegisterDTO register){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/api/user").toString());
        return ResponseEntity.created(uri).body(userService.save(register));
    }

    @GetMapping("/user/{id}/orders")
    public ResponseEntity<PageOrderDTO> getListOrders(@PathVariable("id")Long id, @RequestParam(required = false,defaultValue = "1") int page,
                                                      @RequestParam(required = false,defaultValue = "10") int size){
        return  ResponseEntity.ok().body(userService.getListOrders(id,page,size));
    }

    @PutMapping(value = "/user/update",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateUser(@Valid @RequestBody UserDTO userDTO){
        return ResponseEntity.ok().body(userService.update(userDTO));
    }

    @PostMapping(value = "/user/{userId}/add/role/{roleId}",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addRoleToUser(@PathVariable("userId") Long userId,
                                                    @PathVariable("roleId") Long roleId){
        return ResponseEntity.ok().body(userService.addRoleToUser(userId,roleId));
    }

    @PostMapping(value = "/user/{userId}/remove/role/{roleId}",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> removeRoleToUser(@PathVariable("userId") Long userId,
                                                @PathVariable("roleId") Long roleId){
        return ResponseEntity.ok().body(userService.removeRoleInUser(userId,roleId));
    }

    @PostMapping(value="user/changePassword",produces =  APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,String>> changePassword(@Valid @RequestBody PasswordDTO passwordDTO){
        return ResponseEntity.ok().body(userService.changePassword(passwordDTO));
    }

}
