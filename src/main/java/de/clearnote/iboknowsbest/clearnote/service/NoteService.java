package de.clearnote.iboknowsbest.clearnote.service;

import de.clearnote.iboknowsbest.clearnote.dto.NoteDTO;
import de.clearnote.iboknowsbest.clearnote.entity.Note;

public interface NoteService {
    void deleteNoteWithPermission(Long noteId);
    NoteDTO createNote(NoteDTO noteDTO, Long userId);
    NoteDTO updateNoteWithPermission(Long noteId, NoteDTO noteDTO, Long userId);
}
