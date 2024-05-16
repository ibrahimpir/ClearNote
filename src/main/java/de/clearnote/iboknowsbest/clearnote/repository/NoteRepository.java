package de.clearnote.iboknowsbest.clearnote.repository;

import de.clearnote.iboknowsbest.clearnote.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * In dieser Klasse sind nur CRUD Operationen ohne Prüfung oder sonstiges.
 * Alles was weiteren Code Bedarf wird in dem NoteService Interface festgelegt und
 * in der NoteServiceImpl Klasse implementiert.
 */
@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    // Hier können bei Bedarf benutzerdefinierte Methoden hinzugefügt werden
    List<Note> findByUserId(Long userId);
}