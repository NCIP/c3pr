package edu.duke.cabig.c3pr;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author kherm
 */
@Retention(RetentionPolicy.SOURCE)
public @interface C3PRUseCases {
	C3PRUseCase[] value();
}
