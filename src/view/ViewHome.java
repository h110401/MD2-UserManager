package view;

import config.Config;
import controller.LikeController;
import controller.SongController;
import controller.UserController;
import model.Like;
import model.RoleName;
import model.Song;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class ViewHome {

    UserController userController = new UserController();
    SongController songController = new SongController();
    LikeController likeController = new LikeController();
    List<Like> likeList = likeController.getListLike();
    List<Song> songList = songController.getSongList();
    User currentUser = userController.getCurrentUser();
    RoleName roleName = new ArrayList<>(currentUser.getRoles()).get(0).getRoleName();

    public ViewHome() {
        switch (roleName) {
            case ADMIN:
                menuAdmin();
                break;
            case USER:
                menuUser();
        }
    }


    public void menuUser() {
        System.out.println("Hello USER: " + currentUser.getName());
        System.out.println("1. Log out");

        int choice = Integer.parseInt(Config.scanner().nextLine());

        switch (choice) {
            case 1:
                userController.logout();
                new ViewMainMenu().menu();
                return;
        }
        menuUser();
    }

    public void menuAdmin() {
        System.out.println("Hello ADMIN: " + currentUser.getName());
        System.out.println("1. Show song list");
        System.out.println("2. Create song");
        System.out.println("3. User Manage");
        System.out.println("4. Log out");

        int choice = Integer.parseInt(Config.scanner().nextLine());

        switch (choice) {
            case 1:
                formShowSongList();
                break;
            case 2:
                formCreateSong();
                break;
            case 3:
                formUserManage();
                break;
            case 4:
                userController.logout();
                new ViewMainMenu().menu();
                return;
        }
        menuAdmin();
    }

    private void formShowSongList() {

        // Hiển thị song list và lượt like
        for (Song song : songList) {
            System.out.println(song + " Like: " + likeController.getLikeNumberById(song.getId()));
        }
        System.out.println("Enter song id to show details");

        int idSong = Integer.parseInt(Config.scanner().nextLine());
        int likeNumber = likeController.getLikeNumberById(idSong);

        // Hiển thị chi tiết song đã chọn
        System.out.println(songController.findById(idSong));
        System.out.println("Like: " + likeNumber);

        // Check xem currentUser đã like chưa
        boolean checkLike = likeController.checkLike(idSong);
        System.out.println(checkLike ? "Đã LIKE" : "LIKE");

        // Nhập 1 để like or dislike, nhập khác để thoát
        System.out.println("Enter 1 to like or else to back");
        int choice = Integer.parseInt(Config.scanner().nextLine());
        if (choice == 1) {
            if (checkLike) {
                // Nếu đã like thì dislike
                likeController.deleteLike(idSong);
            } else {
                // Nếu chưa like thì add 1 object Like vào likeList
                int idLike;
                if (likeList.isEmpty()) {
                    idLike = 1;
                } else {
                    idLike = likeList.get(likeList.size() - 1).getId() + 1;
                }
                likeController.createLike(new Like(idLike, idSong, currentUser.getId()));
            }
        }
    }

    private void formCreateSong() {
        // Tạo song bằng name
        System.out.println("Enter song name");
        String name = Config.scanner().nextLine();
        int id;
        if (songList.isEmpty()) {
            id = 1;
        } else {
            id = songList.get(songList.size() - 1).getId() + 1;
        }
        songController.createSong(new Song(id, name));
    }

    private void formUserManage() {

    }


}
