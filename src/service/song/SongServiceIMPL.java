package service.song;

import config.Config;
import model.Song;

import java.util.ArrayList;
import java.util.List;

public class SongServiceIMPL implements ISongService {

    static String PATH_SONG = "src/database/song.txt";
    static Config<List<Song>> config = new Config<>();
    static List<Song> songList = config.read(PATH_SONG);

    static {
        if (songList == null) {
            songList = new ArrayList<>();
        }
    }

    @Override
    public List<Song> findAll() {
        return songList;
    }

    @Override
    public void save(Song song) {
        songList.add(song);
        updateData();
    }

    @Override
    public void remove(int id) {
        songList.remove(findById(id));
        updateData();
    }

    @Override
    public Song findById(int id) {
        for (Song song : songList) {
            if (song.getId() == id) {
                return song;
            }
        }
        return null;
    }

    @Override
    public void updateData() {
        config.write(PATH_SONG, songList);
    }
}
