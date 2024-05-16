package de.clearnote.iboknowsbest.clearnote.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class NoteDTO {

    private Long id;
    private String title;
    private String content;
    private Date createdAt;
    private Date updatedAt;

}
