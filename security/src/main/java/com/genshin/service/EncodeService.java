package com.genshin.service;

public interface EncodeService {
    boolean matches(String inputPassword, String realPassword);

    String encoder(String password);

}
