package controller;

import model.Song;
import service.song.ISongService;
import service.song.SongServiceIMPL;

import java.util.List;

public class SongController {

    ISongService songService = new SongServiceIMPL();

    public List<Song> getSongList() {
        return songService.findAll();
    }

    public void createSong(Song song) {
        songService.save(song);
    }

    public Song findById(int id) {
        return songService.findById(id);
    }
}
