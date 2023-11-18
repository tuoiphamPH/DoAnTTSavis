/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.test_spring_boot.Dto.NganLuongDto;

import java.io.Serializable;

/**
 *
 * @author TaiND
 */
public class ResponseInfo implements Serializable {
    
    private String _error_code;
    private String _checkout_url;
    private String _Token;
    private String _description;

    public String getError_code() {
        return _error_code;
    }

    public void setError_code(String _error_code) {
        this._error_code = _error_code;
    }

    public String getCheckout_url() {
        return _checkout_url;
    }

    public void setCheckout_url(String _checkout_url) {
        this._checkout_url = _checkout_url;
    }

    public String getToken() {
        return _Token;
    }

    public void setToken(String _Token) {
        this._Token = _Token;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String _description) {
        this._description = _description;
    }
}
