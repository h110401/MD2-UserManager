package service.like;

import config.Config;
import model.Like;
import model.User;
import service.user.IUserService;
import service.user.UserServiceIMPL;

import java.util.ArrayList;
import java.util.List;

public class LikeServiceIMPL implements ILikeService {

    IUserService userService = new UserServiceIMPL();
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
        Like likeDelete = null;

        for (Like like : likeList) {
            if (like.getIdSong() == id && like.getIdUser() == userService.getCurrentUser().getId()) {
                likeDelete = like;
            }
        }
        likeList.remove(likeDelete);

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

    @Override
    public int getLikeNumberBySongId(int id) {
        int count = 0;

        for (Like like : likeList) {
            if (like.getIdSong() == id) {
                count++;
            }
        }

        return count;
    }

    @Override
    public boolean checkLike(int id) {
        User current = userService.getCurrentUser();

        for (Like like : likeList) {
            if (like.getIdSong() == id && like.getIdUser() == current.getId()) {
                return true;
            }
        }
        return false;
    }
}
