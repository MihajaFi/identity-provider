package school.hei.identity.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void init() throws IOException {
        String firebaseConfig = System.getenv("GOOGLE_SERVICES");

        if (firebaseConfig == null || firebaseConfig.isEmpty()) {
            throw new IllegalStateException("La variable d'environnement GOOGLE_SERVICES_JSON n'est pas définie !");
        }

        ByteArrayInputStream serviceAccount = new ByteArrayInputStream(firebaseConfig.getBytes());

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
    }
}
