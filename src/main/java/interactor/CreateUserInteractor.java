package interactor;

import base.Callback;
import controller.UserController;

public class CreateUserInteractor {
    private final UserController userController;

    public CreateUserInteractor(UserController userController) {
        this.userController = userController;
    }

    public void createUser(Long userId, String userName, Callback callback) {
        userController.createUser(userId, userName, callback);
    }
}
