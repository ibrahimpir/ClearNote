package de.clearnote.iboknowsbest.clearnote.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public JdbcUserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager();
        userDetailsManager.setDataSource(dataSource);

        // SQL-Abfrage zum Abrufen eines Benutzers nach Benutzername
        userDetailsManager.setUsersByUsernameQuery(
                "SELECT username, password, enabled FROM users WHERE username = ?");

        // SQL-Abfrage zum Abrufen der Berechtigungen/Rollen eines Benutzers nach Benutzername
        userDetailsManager.setAuthoritiesByUsernameQuery(
                "SELECT u.username, r.name FROM user_roles ur " +
                        "JOIN users u ON ur.user_id = u.id " +
                        "JOIN roles r ON ur.role_id = r.id " +
                        "WHERE u.username = ?");

        return userDetailsManager;
    }

    /**
     * Diese Methode hat mich einige Stunden gekostet. CSRF macht, wenn es nicht deaktiviert ist, Probleme mit Postman.
     * Das war der Grund warum die ganze Zeit angezeigt wurde, dass ich nicht auf den Pfad zugreifen kann.
     * Nachdem das aber deaktiviert wurde, musste die Basic Authentication aktiviert werden damit es endgültig keine Probleme mehr gibt.
     * Diese zwei Punkte haben im Anschluss dafür gesorgt, dass ich endlich die createNote Methode aus dem NoteController aufrufen konnte.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // CSRF-Schutz deaktivieren
                .authorizeRequests()
                .anyRequest().authenticated()  // Alle Anfragen müssen authentifiziert werden
                .and()
                .httpBasic(Customizer.withDefaults());  // Basic Authentication aktivieren

        return http.build();
    }
}
