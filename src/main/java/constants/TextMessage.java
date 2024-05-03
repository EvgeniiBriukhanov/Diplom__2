package constants;

public class TextMessage {
    public final static String USER_CREATE_DOUBLE_403 = "User already exists";
    public final static String USER_CREATE_ONE_FIELD_EMPTY_403 = "Email, password and name are required fields";
    public final static String LOGIN_USER_INCORRECT_DATA_401 = "Email or password are incorrect";

    public final static String USER_EMPTY = "Пользователь уже удален или он не был создан";
    public final static boolean USER_DELETE_SUCCESS_202 = true;
    public final static String USER_DELETE_202 = "User successfully removed";

    public final static String USER_DELETE_MESSAGE = "Пользователь успешно удален";
    public final static boolean USER_CREATE_SUCCESS_200 = true;
    public final static boolean USER_CREATE_FAILED_403 = false;
    public final static String LOGIN_SUCCESSFUL_200 = null;
    public final static String LOGIN_NON_EXISTENT_PASSWORD_OR_LOGIN_404 = "Учетная запись не найдена";
}

//