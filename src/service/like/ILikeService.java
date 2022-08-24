package service.like;

import model.Like;
import service.IGenericService;

public interface ILikeService extends IGenericService<Like> {

    int getLikeNumberBySongId(int id);

    boolean checkLike(int id);

}
