package com.eyetrade.backend.constants;

public class ErrorConstants {
    public static final String INVALID_TOKEN = "Invalid Token!";
    public static final String MAIL_ALREADY_EXISTS = "Mail already exists!";
    public static final String EXPIRED_TOKEN = "Token is expired!";
    public static final String USER_NOT_EXIST = "User could not be found!";
    public static final String MAIL_SEND_FAILED = "Mail couldn't be sent!";
    public static final String IBAN_AND_IDENTITY_SHOULD_BE_PROVIDED = "Iban No and identity no should not be blank!";
    public static final String WRONG_EMAIL_OR_PASSWORD = "The given email or password is wrong!";
    public static final String NOT_TRADER_USER = "The user is not a trader user";
    public static final String NOT_AUTHORIZED_FOR_OPERATION = "You are not authorized for this operation!";
    public static final String NO_SUCH_FOLLOWING = "There isn't exist such following relation";
    public static final String NO_SUCH_CURRENCY_TYPES = "There isn't exist such currency type";
    public static final String FOLLOWING_RELATION_ALREADY_EXISTS = "The following relation already exists.";
    public static final String CURRENCIES_COULD_NOT_BE_UPDATED = "Currencies could not be updated ";
    public static final String POINT_SHOULD_BE_INSIDE_RANGE = "The given point should be inside [0,5] range.";
    public static final String EVENTS_CANNOT_BE_UPLOADED = "Events cannot be uploaded!";
    public static final String NO_SUCH_TRADING_ACCOUNT = "There is no such trading Account!";
    public static final String A_USER_CAN_HAVE_ONLY_AN_ACCOUNT = "A user can have only an acoount!";
    public static final String USER_CANNOT_GIVE_POINT_SELF = "A user cannot give point to himself/herself!";
    public static final String USER_CANNOT_DELETE_ANOTHER_USERS_COMMENT = "A user cannot delete to another user's comment!";
    public static final String USER_CANNOT_DELETE_ANOTHER_USERS_ANNOTATION = "A user cannot delete another user's annotation!";
    public static final String USER_CANNOT_DELETE_ANOTHER_USERS_ORDER = "A user cannot delete another user's order!";
    public static final String USER_CANNOT_UPDATE_ANOTHER_USERS_ORDER = "A user cannot update another user's order!";
    public static final String MIN_RATE_CAN_NOT_BE_BIGGER_THAN_MAX = "Minimum Rate cannot be bigger than maximum rate";
    public static final String FUND_IS_NOT_ENOUGH_FOR_THIS_OPERATION = "Fund is not enough!";
}
