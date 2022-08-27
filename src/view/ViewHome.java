package view;

import config.Config;
import controller.UserController;
import dto.response.ResponseMessenger;
import model.RoleName;
import model.User;

import java.util.ArrayList;
import java.util.List;

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
                break;
            case PM:
                menuPm();
        }
    }

    private void menuPm() {
        System.out.println("Menu");
        System.out.println("1. Show list user");
        System.out.println("2. Delete user");
        System.out.println("3. Block user");
        System.out.println("4. Back");

        int choice = Config.getValidInteger();
        if (choice == 0) return;
        switch (choice) {
            case 1:
                formShowListUser();
                break;
            case 2:
                formDeleteUser();
                break;
            case 3:
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid choice");
        }
        menuPm();
    }


    public void menuUser() {
        System.out.println("Hello USER: " + currentUser.getName());
        System.out.println("1. Change password");
        System.out.println("2. Log out");

        int choice = Integer.parseInt(Config.scanner().nextLine());

        switch (choice) {
            case 1:
                formChangePassword();
                break;
            case 2:
                userController.logout();
                new ViewMainMenu().menu();
                return;
        }
        menuUser();
    }

    public void menuAdmin() {
        System.out.println("Hello ADMIN: " + currentUser.getName());
        System.out.println("1. User Manage");
        System.out.println("2. Change Password");
        System.out.println("3. Log out");

        int choice = Integer.parseInt(Config.scanner().nextLine());

        switch (choice) {
            case 1:
                formUserManage();
                break;
            case 2:
                formChangePassword();
                break;
            case 3:
                userController.logout();
                new ViewMainMenu().menu();
                return;
        }
        menuAdmin();
    }

    private void formChangePassword() {
//        System.out.println("Enter your old password: ");
//        String oldPassword = Config.scanner().nextLine();

        String oldPassword;
        while (true) {
            System.out.println("Enter your old password:");
            oldPassword = Config.scanner().nextLine();
            if (oldPassword.matches("[a-zA-Z0-9]{1,10}")) {
                break;
            } else {
                System.out.println("Invalid password, try again!");
            }
        }

        System.out.println("Enter your new password: ");
        String newPassword = Config.scanner().nextLine();

        System.out.println("Repeat the new password: ");
        String newPassword2 = Config.scanner().nextLine();

        if (!newPassword.equals(newPassword2)) {
            System.out.println("Repeat password incorrect");
        } else {
            ResponseMessenger messenger = userController.changePassword(oldPassword, newPassword);
            switch (messenger.getMessage()) {
                case "not_match":
                    System.out.println("Old password does not matches!");
                    break;
                case "success":
                    System.out.println("Change password successfully!");
                    userController.logout();
                    new ViewMainMenu().menu();
            }
        }
    }


    private void formUserManage() {
        System.out.println("Menu");
        System.out.println("1. Show list user");
        System.out.println("2. Delete user");
        System.out.println("3. Change role");
        System.out.println("4. Block user");
        System.out.println("5. Back");

        int choice = Config.getValidInteger();
        if (choice == 0) return;
        switch (choice) {
            case 1:
                formShowListUser();
                break;
            case 2:
                formDeleteUser();
                break;
            case 3:
                formChangeRole();
                break;
            case 4:
                formBlockUser();
                break;
            case 5:
                return;
            default:
                System.out.println("Invalid choice");
        }

        formUserManage();
    }

    private void formBlockUser() {
        formShowListUser();
        System.out.println("Enter id user to block");
        int id = Config.getValidInteger();
        ResponseMessenger messenger = userController.blockUser(id);

        switch (messenger.getMessage()) {
            case "not_found":
                System.err.println("ID not found");
                break;
            case "blocked":
                System.out.println("You just blocked user id " + id);
                break;
            case "unblocked":
                System.out.println("You just unblocked user id " + id);
        }
    }

    private void formChangeRole() {
        formShowListUser();
        System.out.println("Enter id of user to change role");
        int id = Config.getValidInteger();
        System.out.println("Enter role to change (pm / user)");
        String roleName = Config.scanner().nextLine();

        ResponseMessenger messenger = userController.changeRole(id, roleName);

        switch (messenger.getMessage()) {
            case "success":
                System.out.println("Change role successfully!");
                break;
            case "invalid_role":
                System.err.println("Invalid role!");
                break;
            case "not_found":
                System.out.println("ID not found!");
        }

    }

    private void formDeleteUser() {
        formShowListUser();
        System.out.println("Enter id of user to delete");
        int id = Config.getValidInteger();
        ResponseMessenger messenger = userController.deleteUser(id);
        switch (messenger.getMessage()) {
            case "success":
                System.out.println("Delete user successfully");
                break;
            case "not_found":
                System.err.println("ID not found");
        }
    }

    private void formShowListUser() {
        List<User> users = userController.getUserList();
        System.out.println(users);
        System.out.printf("%3s      %-12s %-7s %s\n", "ID", "USERNAME", "ROLE", "STATUS");
        for (User user : users) {
            System.out.printf("%3s      %-12s %-7s %s\n", user.getId(), user.getUsername(), user.getRoleName(), (user.isStatus() ? "BLOCKED" : "NOT BLOCKED"));
        }
    }
}
