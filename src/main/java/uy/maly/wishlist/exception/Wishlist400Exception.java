package uy.maly.wishlist.exception;

import java.io.Serializable;

/**
 * @author JMaly
 * @project wishlist
 */
public class Wishlist400Exception extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    private String message;

    public Wishlist400Exception(String message){
        this.message=message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
