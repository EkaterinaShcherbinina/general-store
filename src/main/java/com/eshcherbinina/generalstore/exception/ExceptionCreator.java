package com.eshcherbinina.generalstore.exception;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class ExceptionCreator {

    private static MessageSource messageSource;
    public ExceptionCreator(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    public static void throwException(ErrorType errorType, String message, String detail, Object[] detailArgs) {
        switch(errorType) {
            case ENTITY_NOT_FOUND:
                EntityNotFoundException enf =  new EntityNotFoundException(
                        messageSource.getMessage(message, null, Locale.ENGLISH));
                enf.setDetails(messageSource.getMessage(detail, detailArgs, Locale.ENGLISH));
                enf.setType(errorType);
                throw enf;
            case USER_NOT_AUTHORIZED:
                UserNotAuthorized una =  new UserNotAuthorized(
                        messageSource.getMessage(message, null, Locale.ENGLISH));
                una.setDetails(messageSource.getMessage(detail, detailArgs, Locale.ENGLISH));
                una.setType(errorType);
                throw una;
            case ORDER_CREATION_FAILED:
                OrderCreationFailed ocf = new OrderCreationFailed(
                        messageSource.getMessage(message, null, Locale.ENGLISH));
                ocf.setDetails(messageSource.getMessage(detail, detailArgs, Locale.ENGLISH));
                ocf.setType(errorType);
                throw ocf;
            default:
                break;
        }
    }
}
