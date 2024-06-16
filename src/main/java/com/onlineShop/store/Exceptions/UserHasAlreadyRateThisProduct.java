package com.onlineShop.store.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserHasAlreadyRateThisProduct  extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public UserHasAlreadyRateThisProduct(String message) {
        super(message);
    }
}