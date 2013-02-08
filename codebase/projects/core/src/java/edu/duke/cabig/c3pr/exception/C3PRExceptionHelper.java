/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import edu.duke.cabig.c3pr.domain.Error;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Aug 23, 2007 Time: 1:45:43 PM To change this template
 * use File | Settings | File Templates.
 */
public class C3PRExceptionHelper {

    private static MessageSource errorMessages;

    public C3PRExceptionHelper() {
	}

	public C3PRExceptionHelper(MessageSource errorMessages){
        this.errorMessages=errorMessages;
    }
    
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
    
    public C3PRCodedException getException(Error error) {
        int code=Integer.parseInt(error.getErrorCode());
        return new C3PRCodedException(code, getExceptionMessageFromCode(code, null, null)+error.getErrorMessage());
    }
    
    public C3PRCodedRuntimeException getRuntimeException(int code) {
        return new C3PRCodedRuntimeException(code, getExceptionMessageFromCode(code, null, null));
    }

    public C3PRCodedRuntimeException getRuntimeException(int code, Throwable throwable) {
        return new C3PRCodedRuntimeException(code, getExceptionMessageFromCode(code, null, null),
                        throwable);
    }

    public C3PRCodedRuntimeException getRuntimeException(int code, Object[] params, Throwable throwable) {
        return new C3PRCodedRuntimeException(code, getExceptionMessageFromCode(code, params, null),
                        throwable);
    }

    public C3PRCodedRuntimeException getRuntimeException(int code, Object[] params) {
        return new C3PRCodedRuntimeException(code, getExceptionMessageFromCode(code, params, null));
    }

    public C3PRCodedRuntimeException getRuntimeException(int code, Object[] params, Throwable throwable,
                    Locale locale) {
        return new C3PRCodedRuntimeException(code, getExceptionMessageFromCode(code, params, locale),
                        throwable);
    }
    
//    public C3PRCodedRuntimeException getRuntimeException(Error error) {
//        int code=Integer.parseInt(error.getErrorCode());
//        return new C3PRCodedRuntimeException(code, getExceptionMessageFromCode(code, null, null)+error.getErrorMessage());
//    }
    
    public C3PRCodedRuntimeException getMultisiteException(Error error) {
        int code=Integer.parseInt(error.getErrorCode());
        return new MultisiteException(code, getExceptionMessageFromCode(code, null, null)+error.getErrorMessage());
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
    
    public C3PRCodedRuntimeException transformException(Exception e){
        String errorMsg=e.getMessage();
        if(errorMsg.indexOf(":")==3){
            try {
                int code=Integer.parseInt(errorMsg.substring(0, 3));
                return getRuntimeException(code, e);
            }
            catch (NumberFormatException e1) {
            }
        }
        return getRuntimeException(-1,e);
    }

	/**
	 * @param code
	 * @return
	 */
	public ConversionException getConversionException(int code) {
		return new ConversionException(code, getExceptionMessageFromCode(code, null, null));
	}
	
    /**
     * @param code
     * @param params
     * @return
     */
    public ConversionException getConversionException(int code, Object[] params) {
        return new ConversionException(code, getExceptionMessageFromCode(code, params, null));
    }

}
