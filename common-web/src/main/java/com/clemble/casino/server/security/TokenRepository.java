package com.clemble.casino.server.security;

/**
 * Created by mavarazy on 6/14/14.
 */
public interface TokenRepository {

    ClembleOAuthProviderToken findOne(String token);

    void save(ClembleOAuthProviderToken token);

    void delete(String token);

}
