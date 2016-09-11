package io.kndl.notebook.dao;

import io.kndl.notebook.Constants;
import io.kndl.notebook.model.Note;
import io.kndl.notebook.model.Notebook;
import io.kndl.notebook.serialization.Codec;
import io.kndl.notebook.serialization.CodecException;
import org.mapdb.DB;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.function.Function;
import java.util.stream.Collector;

import static java.util.stream.Collectors.toSet;

/**
 * Created by skend on 9/3/2016.
 */
public class MapDBNotebookDAO implements NotebookDAO {

    private final DB db;
    private final Codec<Notebook> notebookCodec;
    private final Codec<Note> noteCodec;

    private final ConcurrentNavigableMap<Long,byte[]> notebooks;
    private final ConcurrentNavigableMap<Long,byte[]> notes;
    private final ConcurrentNavigableMap<String,Long> ids;

    private final Function<byte[],Notebook> bytesToNotebook = new Function<byte[],Notebook>() {
        public Notebook apply(byte[] bytes) {
            Notebook notebook = null;
            try {
                notebook = notebookCodec.deserialize(bytes);
            } catch (CodecException e) {
                e.printStackTrace();
            }
            return notebook;
        }
    };

    public MapDBNotebookDAO(DB db, Codec<Notebook> notebookCodec, Codec<Note> noteCodec) {
        this.db = db;
        this.notebookCodec = notebookCodec;
        this.noteCodec = noteCodec;
        this.notebooks = (ConcurrentNavigableMap<Long, byte[]>) db.treeMap(Constants.NOTEBOOK_KEY).createOrOpen();
        this.notes = (ConcurrentNavigableMap<Long, byte[]>) db.treeMap(Constants.NOTE_KEY).createOrOpen();
        this.ids = (ConcurrentNavigableMap<String, Long>) db.treeMap(Constants.ID_GENERATOR).createOrOpen();
    }

    @Override
    public Set<Notebook> getAllNotebooks() {
        Set<Notebook> nbs = new HashSet<Notebook>();
        nbs = notebooks.entrySet()
                .stream()
                .map(entry -> entry.getValue())
                .map(bytesToNotebook)
                .collect(toSet());
        return nbs;
    }

    @Override
    public Notebook getNotebookById(long id) {
        ConcurrentNavigableMap<Long,byte[]> map =
                (ConcurrentNavigableMap<Long, byte[]>) db.treeMap(Constants.NOTEBOOK_KEY).createOrOpen();
        Notebook notebook = null;
        if(map.containsKey(id)) {
            try {
                notebook = notebookCodec.deserialize(map.get(id));
            } catch (CodecException e) {
                e.printStackTrace();
            }
        }
        return notebook;
    }

    @Override
    public Note getNoteById(long notebookId, long noteId) {
        return null;
    }

    @Override
    public Set<Note> getAllNotes(long notebookId) {
        return null;
    }

    @Override
    public boolean tagNotebook(long notebookId, String tag) {
        return false;
    }

    @Override
    public boolean tagNote(long notebookId, long noteId, String tag) {
        return false;
    }

    @Override
    public Notebook saveNotebook(Notebook notebook) {
        Notebook working = notebook;
        if(notebook.id == 0) {
            working = new Notebook(getNextId(Constants.NOTEBOOK_KEY),notebook.name,notebook.notes,notebook.tags);
        }
        try {
            byte[] bytes = notebookCodec.serialize(working);
            notebooks.put(working.id,bytes);
            db.commit();
            return working;
        } catch (CodecException e) {
            e.printStackTrace();
        }

        return working;
    }

    @Override
    public Note saveNote(Note note) {
        ConcurrentNavigableMap<Long,byte[]> map =
                (ConcurrentNavigableMap<Long, byte[]>) db.treeMap(Constants.NOTE_KEY).createOrOpen();
        try {
            byte[] bytes = noteCodec.serialize(note);
            map.put(note.id,bytes);
            db.commit();
            return note;
        } catch (CodecException e) {
            e.printStackTrace();
        }

        link(note.notebookId,note.id);

        return null;
    }

    @Override
    public boolean deleteNote(long notebookId, long noteId) {
        return false;
    }

    @Override
    public boolean deleteNotebook(long notebookId) {
        return false;
    }

    private long getNextId(String object) {
        long id = 0;
        if(!ids.containsKey(object)) {
            id = 1;
        } else {
            id = ids.get(object)+1;
        }
        ids.put(object,id);
        db.commit();

        return id;
    }

    private void link(long notebookId, long noteId) {
        ConcurrentNavigableMap<Long,Long> noteToNotebook =
                (ConcurrentNavigableMap<Long, Long>) db.treeMap(Constants.NOTE_NOTEBOOK_KEY).createOrOpen();
        noteToNotebook.put(noteId,notebookId);
        ConcurrentNavigableMap<Long,Long> notebookToNote =
                (ConcurrentNavigableMap<Long, Long>) db.treeMap(Constants.NOTEBOOK_NOTE_KEY).createOrOpen();
        notebookToNote.put(notebookId,noteId);
    }

    private void unlink(long notebookId, long noteId) {
        ConcurrentNavigableMap<Long,Long> noteToNotebook =
                (ConcurrentNavigableMap<Long, Long>) db.treeMap(Constants.NOTE_NOTEBOOK_KEY).createOrOpen();
        noteToNotebook.remove(noteId);
        ConcurrentNavigableMap<Long,Long> notebookToNote =
                (ConcurrentNavigableMap<Long, Long>) db.treeMap(Constants.NOTEBOOK_NOTE_KEY).createOrOpen();
        notebookToNote.remove(notebookId);
    }
}
