package kanfu.old_city_memo.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import kanfu.old_city_memo.model.User;
import kanfu.old_city_memo.payload.response.JwtResponse;
import kanfu.old_city_memo.payload.response.MessageResponse;
import kanfu.old_city_memo.repo.UserRepo;
import kanfu.old_city_memo.security.jwt.JwtUtils;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@Valid @RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User authenticatedUser = (User) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(authentication);
        return ResponseEntity.ok(new JwtResponse(authenticatedUser.getId(),
                authenticatedUser.getUsername(), authenticatedUser.getRole(), jwt, "Bearer"));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody User user) {
        if (userRepo.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("User already exists!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userRepo.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
