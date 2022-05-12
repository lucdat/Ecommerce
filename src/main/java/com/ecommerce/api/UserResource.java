package com.ecommerce.api;

import com.ecommerce.dto.domain.*;
import com.ecommerce.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @Operation(summary = "Get list users", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/users")
    public ResponseEntity<PageUserDTO> findAll(@RequestParam(required = false,defaultValue = "1") int page,
                                              @RequestParam(required = false,defaultValue = "10") int size){
        return  ResponseEntity.ok().body(userService.findAll(page,size));
    }
    @Operation(summary = "Find user by id", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> findByID(@PathVariable("id") Long id){
        return  ResponseEntity.ok().body(userService.findById(id));
    }

    @PostMapping(value = "/user",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> saveUser(@Valid @RequestBody RegisterDTO register){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/api/user").toString());
        return ResponseEntity.created(uri).body(userService.save(register));
    }

    @Operation(summary = "Get history orders", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/user/{id}/orders")
    public ResponseEntity<PageOrderDTO> getListOrders(@PathVariable("id")Long id, @RequestParam(required = false,defaultValue = "1") int page,
                                                      @RequestParam(required = false,defaultValue = "10") int size){
        return  ResponseEntity.ok().body(userService.getListOrders(id,page,size));
    }

    @Operation(summary = "Update info", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping(value = "/user/update",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateUser(@Valid @RequestBody UserDTO userDTO){
        return ResponseEntity.ok().body(userService.update(userDTO));
    }

    @Operation(summary = "Add role to user", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/user/{userId}/add/role/{roleId}",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addRoleToUser(@PathVariable("userId") Long userId,
                                                    @PathVariable("roleId") Long roleId){
        return ResponseEntity.ok().body(userService.addRoleToUser(userId,roleId));
    }
    @Operation(summary = "Remove role in user", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/user/{userId}/remove/role/{roleId}",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> removeRoleToUser(@PathVariable("userId") Long userId,
                                                @PathVariable("roleId") Long roleId){
        return ResponseEntity.ok().body(userService.removeRoleInUser(userId,roleId));
    }
}
