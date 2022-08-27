package view;

import config.Config;
import controller.UserController;
import model.RoleName;
import model.User;

import java.util.ArrayList;

public class ViewHome {

    UserController userController = new UserController();
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
        System.out.println("3. User Manage");
        System.out.println("4. Log out");

        int choice = Integer.parseInt(Config.scanner().nextLine());

        switch (choice) {
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



    private void formUserManage() {

    }


}
