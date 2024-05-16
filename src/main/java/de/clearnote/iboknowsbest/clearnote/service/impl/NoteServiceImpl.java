package de.clearnote.iboknowsbest.clearnote.service.impl;

import de.clearnote.iboknowsbest.clearnote.repository.NoteRepository;
import de.clearnote.iboknowsbest.clearnote.dto.NoteDTO;
import de.clearnote.iboknowsbest.clearnote.entity.Note;
import de.clearnote.iboknowsbest.clearnote.entity.User;
import de.clearnote.iboknowsbest.clearnote.service.NoteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NoteServiceImpl implements NoteService {
    @Autowired
    private final NoteRepository noteRepository;
    @Autowired
    private UserServiceImpl userServiceImpl;

    private final ModelMapper modelMapper;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository, ModelMapper modelMapper) {
        this.noteRepository = noteRepository;
        this.modelMapper = modelMapper;
    }

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteNoteWithPermission(Long noteId) {
        noteRepository.deleteById(noteId);
    }

    public NoteDTO createNote(NoteDTO noteDTO, Long userId) {
        User user = userServiceImpl.getUserById(userId); // Benutzer aus der Datenbank laden
        Note note = modelMapper.map(noteDTO, Note.class);
        note.setUser(user); // Setze den geladenen Benutzer
        Note savedNote = noteRepository.save(note);
        return modelMapper.map(savedNote, NoteDTO.class);
    }

    /**
     * Update eine Notiz wenn der Nutzer sie erstellt hat und somit auch die Rechte besitzt, diese
     * Notiz zu bearbeiten.
     * @param noteId
     * @param noteDTO
     * @param userId
     * @return
     */
    @Override
    public NoteDTO updateNoteWithPermission(Long noteId, NoteDTO noteDTO, Long userId) {
        // Holen der zu aktualisierenden Notiz aus der Datenbank.
        Note noteToUpdate = noteRepository.findById(noteId).get();
        // Prüft ob die Notiz existiert und dem Nutzer gehört.
        if (noteToUpdate != null && noteToUpdate.getUser().getId().equals(userId)) {
            // Aktualisieren der Notizdaten mit den neuen Werten aus dem übergebenen NoteDTO
            noteToUpdate.setTitle(noteDTO.getTitle());
            noteToUpdate.setContent(noteDTO.getContent());
            // Speichern der aktualisierten Notiz in der Datenbank
            Note noteToSave = noteRepository.save(noteToUpdate);
            // Rückgabe des aktualisierten Notiz-DTO
            return modelMapper.map(noteToSave, NoteDTO.class);
        } else {
            // Wenn die Notiz nicht gefunden wurde oder nicht dem Benutzer gehört,
            // kannst du eine Ausnahme werfen oder null zurückgeben, je nachdem, wie du es behandeln möchtest.
            return null;
        }

    }

    public List<NoteDTO> getAllNotesByUserId(Long userId) {
        List<Note> notes = noteRepository.findByUserId(userId);
        return notes.stream()
                .map(note -> modelMapper.map(note, NoteDTO.class))
                .collect(Collectors.toList());
    }

}
