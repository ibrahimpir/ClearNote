package de.clearnote.iboknowsbest.clearnote.rest;

import de.clearnote.iboknowsbest.clearnote.dto.NoteDTO;
import de.clearnote.iboknowsbest.clearnote.entity.Note;
import de.clearnote.iboknowsbest.clearnote.service.impl.NoteServiceImpl;
import de.clearnote.iboknowsbest.clearnote.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {
    @Autowired
    private final NoteServiceImpl noteServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;  // Service um Benutzerinformationen abzurufen

    @Autowired
    public NoteController(NoteServiceImpl noteServiceImpl) {
        this.noteServiceImpl = noteServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes() {
        List<Note> notes = noteServiceImpl.getAllNotes();
        return ResponseEntity.ok(notes);
    }

    /**
     * Löscht eine Notiz wenn der Nutzer diese erstellt hat.
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteWithPermission(@PathVariable("id") Long id) {
        noteServiceImpl.deleteNoteWithPermission(id);
        return ResponseEntity.noContent().build();
    }

    // Zeigt nur die Notizen des eingeloggten Users an
    @GetMapping("/get")
    public ResponseEntity<List<NoteDTO>> getAllUserNotes() {
        Long userId = getCurrentUserId();
        List<NoteDTO> notes = noteServiceImpl.getAllNotesByUserId(userId);
        return ResponseEntity.ok(notes);
    }

    /**
     * Erstellt eine Notiz und speichert die ID des Nutzers mit ab, der diese erstellt hat.
     * @param noteDTO
     * @return
     */
    @PostMapping
    public ResponseEntity<NoteDTO> createNote(@RequestBody NoteDTO noteDTO) {
        Long userId = getCurrentUserId();
        NoteDTO createdNote = noteServiceImpl.createNote(noteDTO, userId);
        return ResponseEntity.ok(createdNote);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteDTO> updateNoteWithPermission(@PathVariable("id") Long noteId, @RequestBody NoteDTO noteDTO) {
        Long userId = getCurrentUserId();
        NoteDTO updatedNoteDTO = noteServiceImpl.updateNoteWithPermission(noteId, noteDTO, userId);
        if (updatedNoteDTO != null) {
            return ResponseEntity.ok(updatedNoteDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Hilfsmethode, um die Benutzer-ID des aktuellen Benutzers abzurufen
    private Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userServiceImpl.findUserIdByUsername(username);
    }
}
