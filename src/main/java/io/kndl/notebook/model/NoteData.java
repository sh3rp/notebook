package io.kndl.notebook.model;

import java.util.Arrays;

/**
 * Created by skend on 9/13/2016.
 */
public class NoteData {
    public final long id;
    public final long noteId;
    public final String name;
    public final String mimeType;
    public final byte[] data;

    public NoteData(long id, long noteId, String name, String mimeType, byte[] data) {
        this.id = id;
        this.noteId = noteId;
        this.name = name;
        this.mimeType = mimeType;
        this.data = data;
    }
}
