package edu.duke.cabig.c3pr.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Aug 23, 2007 Time: 1:45:43 PM To change this template
 * use File | Settings | File Templates.
 */
public class C3PRExceptionHelper {

    private static MessageSource errorMessages;

    public void setErrorMessages(MessageSource errorMessages) {
        this.errorMessages = errorMessages;
    }

    public C3PRCodedException getException(int code) {
        return new C3PRCodedException(code, getExceptionMessageFromCode(code, null, null));
    }

    public C3PRCodedException getException(int code, Throwable throwable) {
        return new C3PRCodedException(code, getExceptionMessageFromCode(code, null, null),
                        throwable);
    }

    public C3PRCodedException getException(int code, Object[] params, Throwable throwable) {
        return new C3PRCodedException(code, getExceptionMessageFromCode(code, params, null),
                        throwable);
    }

    public C3PRCodedException getException(int code, Object[] params) {
        return new C3PRCodedException(code, getExceptionMessageFromCode(code, params, null));
    }

    public C3PRCodedException getException(int code, Object[] params, Throwable throwable,
                    Locale locale) {
        return new C3PRCodedException(code, getExceptionMessageFromCode(code, params, locale),
                        throwable);
    }

    private String getExceptionMessageFromCode(int code, Object[] params, Locale locale) {
        String msg = "";
        try {
            msg = errorMessages.getMessage(code + "", params, locale);
        }
        catch (NoSuchMessageException e) {
            try {
                msg = errorMessages.getMessage(-1 + "", null, null);
            }
            catch (NoSuchMessageException e1) {
                msg = "Exception Code property file missing";
            }
        }
        return msg;
    }
}
