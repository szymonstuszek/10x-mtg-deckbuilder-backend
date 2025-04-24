package devs10x.mtg.devs10x_mtg_deckbuilder.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import com.example.mtgdeckbuilder.dto.UserDto;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser() {
        // For now, return a mock user profile
        UserDto user = new UserDto();
        user.setSub("mock-sub-identifier");
        user.setEmail("user@example.com");
        return ResponseEntity.ok(user);
    }
} 