package service.user;

import model.User;
import service.IGenericService;

public interface IUserService extends IGenericService<User> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}
