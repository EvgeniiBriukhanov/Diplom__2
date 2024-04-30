package constants;

public class Endpoints {

    public final static String BASE_URL = "https://stellarburgers.nomoreparties.site";

    public final static String POST_USER_CREATE = "/api/auth/register";

    public final static String POST_USER_LOGIN = "/api/auth/login";

    public final static String POST_USER_LOGOUT = "/api/auth/logout";

    public final static String POST_USER_PASSWORD_RECOVERY_AND_RESET = "/api/password-reset";

    public final static String POST_USER_UPDATE_TOKEN = "/api/auth/token";

    public final static String GET_USER_INFO = "/api/auth/user";

    public final static String DELETE_USER = "/api/auth/user";

    public final static String GET_INGREDIENT_INFO = "/api/ingredients";

    public final static String POST_ORDER_CREATE = "/api/orders";

    public final static String GET_ALL_ORDER = "/api/orders/all";

    public final static String GET_USER_ORDER = "/api/orders";
}
