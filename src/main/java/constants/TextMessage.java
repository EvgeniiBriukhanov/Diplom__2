package constants;

public class TextMessage {

    public final static boolean CREATE_USER_SUCCESS = true;
    public final static boolean CREATE_USER_FAILED = false;
    public final static String CREATE_USER_DOUBLE_403 = "User already exists";
    public final static String CREATE_USER_ONE_FIELD_EMPTY_403 = "Email, password and name are required fields";

    public final static boolean LOGIN_USER_SUCCESS = true;
    public final static boolean LOGIN_USER_FAILED = false;
    public final static String LOGIN_USER_INCORRECT_DATA_401 = "email or password are incorrect";

    public final static boolean DELETE_USER_SUCCESS = true;
    public final static String DELETE_USER_202 = "User successfully removed";

    public final static boolean UPDATE_USER_DATE_SUCCESS = true;
    public final static boolean UPDATE_USER_DATE_FAILED = false;
    public final static String UPDATE_USER_DATE_FALSE_403 = "User with such email already exists";
    public final static String UPDATE_USER_DATE_FALSE_401 = "You should be authorised";

    public final static boolean CREATE_ORDER_SUCCESS = true;
    public final static boolean CREATE_ORDER_FAILED = false;
    public final static String CREATE_ORDER_WITHOUT_INGREDIENT_FALSE_400 = "Ingredient ids must be provided";

    public final static boolean GET_ORDER_RECEIPT_SUCCESS = true;
    public final static boolean GET_ORDER_RECEIPT_FAILED = false;
    public final static String GET_ORDER_RECEIPT_FAILED_401 = "You should be authorised";

    public final static String USER_EMPTY = "Пользователь уже удален или он не был создан";
}