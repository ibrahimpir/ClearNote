package de.clearnote.iboknowsbest.clearnote.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoteErrorResponse {
    private int status;
    private String message;
    private Long timeStamp;

    public NoteErrorResponse() {

    }

    public NoteErrorResponse(int status, String message, Long timeStamp) {
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
    }
}
