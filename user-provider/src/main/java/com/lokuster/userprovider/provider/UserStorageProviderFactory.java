package com.lokuster.userprovider.provider;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;

@Slf4j
public class UserStorageProviderFactory implements org.keycloak.storage.UserStorageProviderFactory<UserStorageProvider> {
    public static final String PROVIDER_ID = "online-shop-user-storage";


    @Override
    public UserStorageProvider create(KeycloakSession session, ComponentModel model) {
        return new UserStorageProvider(session, model);
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public String getHelpText() {
        return "Online Shop User Storage Provider";
    }

    @Override
    public void close() {
        log.info("<<<<<< Closing factory");
    }
}
