package com.eyetrade.backend.constants;

/**
 * Created by Emir Gökdemir
 * on 12 Eki 2019
 */

public class MailConstants {
    //HEADER
    public static final String CONFIRM_ACCOUNT_HEADER = "Email Confirmation";
    public static final String RESET_PASSWORD_HEADER  = "Reset Password";

    //BODY
    public static final String CONFIRM_ACCOUNT_BODY = "To confirm your account, please click here : ";
    public static final String RESET_PASSWORD_BODY  = "To reset your password, please click here : ";

    //URL
    public static final String CONFIRM_ACCOUNT_URL = "/registration/confirm-register?token=";
    public static final String RESET_PASSWORD_URL  = "/registration/confirm-reset-password?token=" ;

}
