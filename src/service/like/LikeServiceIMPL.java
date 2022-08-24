package service.like;

import config.Config;
import model.Like;

import java.util.ArrayList;
import java.util.List;

public class LikeServiceIMPL implements ILikeService {

    static String PATH_LIKE = "src/database/like.txt";

    static Config<List<Like>> config = new Config<>();

    static List<Like> likeList = config.read(PATH_LIKE);

    static {
        if (likeList == null) {
            likeList = new ArrayList<>();
        }
    }

    @Override
    public List<Like> findAll() {
        return likeList;
    }

    @Override
    public void save(Like like) {
        likeList.add(like);
        updateData();
    }

    @Override
    public void remove(int id) {
        likeList.remove(findById(id));
        updateData();
    }

    @Override
    public Like findById(int id) {
        for (Like like : likeList) {
            if (like.getId() == id) {
                return like;
            }
        }
        return null;
    }

    @Override
    public void updateData() {
        config.write(PATH_LIKE, likeList);
    }
}
