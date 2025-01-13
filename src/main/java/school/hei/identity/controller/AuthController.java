package school.hei.identity.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    @GetMapping("/public")
    public ResponseEntity<Void> publicEndpoint() {
        String firebaseAuthUiUrl = "https://identity-983fa.firebaseapp.com/__/auth/handler";
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, firebaseAuthUiUrl)
                .build();
    }


    @GetMapping("/private")
    public ResponseEntity<String> privateEndpoint(@RequestHeader("Authorization") String token) {
        try {
            String idToken = token.replace("Bearer ", "");

            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String uid = decodedToken.getUid();
            return ResponseEntity.ok("Hello, authenticated user! Your UID is: " + uid);
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(401).body("Unauthorized: Invalid or expired token.");
        }
    }
}
