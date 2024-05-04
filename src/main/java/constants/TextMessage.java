package constants;

public class TextMessage {
    public final static boolean CREATE_USER_SUCCESS_200 = true;
    public final static String CREATE_USER_DOUBLE_403 = "User already exists";
    public final static String CREATE_USER_ONE_FIELD_EMPTY_403 = "Email, password and name are required fields";
    public final static boolean CREATE_USER_FAILED_403 = false;
    public final static String LOGIN_USER_INCORRECT_DATA_401 = "email or password are incorrect";
    public final static boolean LOGIN_USER_FAILED_401 = false;
    public final static boolean LOGIN_USER_SUCCESSFUL_200 = true;
    public final static String USER_EMPTY = "Пользователь уже удален или он не был создан";
    public final static boolean DELETE_USER_SUCCESS_202 = true;
    public final static String DELETE_USER_202 = "User successfully removed";
    public final static boolean UPDATE_USER_DATE_200 = true;
}