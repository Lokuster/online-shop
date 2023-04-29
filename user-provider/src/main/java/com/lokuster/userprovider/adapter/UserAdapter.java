package com.lokuster.userprovider.adapter;

import com.lokuster.userprovider.model.User;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.common.util.MultivaluedHashMap;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.adapter.AbstractUserAdapterFederatedStorage;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class UserAdapter extends AbstractUserAdapterFederatedStorage {
    private static final String USERNAME_ATTRIBUTE = "username";
    private static final String EMAIL_ATTRIBUTE = "email";
    private static final String BALANCE_ATTRIBUTE = "balance";
    private static final String ACTIVE_ATTRIBUTE = "active";
    private final User user;
    private final String keycloakId;

    public UserAdapter(KeycloakSession session, RealmModel realm, ComponentModel model, User user) {
        super(session, realm, model);
        this.user = user;
        keycloakId = StorageId.keycloakId(model, user.getId());
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public void setUsername(String username) {
        user.setUsername(username);

    }

    @Override
    public void setEmail(String email) {
        user.setEmail(email);
    }

    @Override
    public String getEmail() {
        return user.getEmail();
    }

    @Override
    public String getId() {
        return keycloakId;
    }

    @Override
    public Long getCreatedTimestamp() {
        return this.user.getCreateDate().getTime();
    }

    @Override
    public boolean isEmailVerified() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.user.getActive();
    }


    @Override
    public void setSingleAttribute(String name, String value) {
        switch (name) {
            case USERNAME_ATTRIBUTE -> user.setUsername(value);
            case EMAIL_ATTRIBUTE -> user.setEmail(value);
            case BALANCE_ATTRIBUTE -> user.setBalance(Double.valueOf(value));
            case ACTIVE_ATTRIBUTE -> user.setActive(Boolean.valueOf(value));
            default -> super.setSingleAttribute(name, value);
        }
    }

    @Override
    public void removeAttribute(String name) {
        super.removeAttribute(name);
    }

    @Override
    public void setAttribute(String name, List<String> values) {
        switch (name) {
            case USERNAME_ATTRIBUTE -> user.setUsername(values.get(0));
            case EMAIL_ATTRIBUTE -> user.setEmail(values.get(0));
            case BALANCE_ATTRIBUTE -> user.setBalance(Double.valueOf(values.get(0)));
            case ACTIVE_ATTRIBUTE -> user.setActive(Boolean.valueOf(values.get(0)));
            default -> super.setAttribute(name, values);
        }
    }

    @Override
    public String getFirstAttribute(String name) {
        switch (name) {
            case USERNAME_ATTRIBUTE -> {
                return user.getUsername();
            }
            case EMAIL_ATTRIBUTE -> {
                return user.getEmail();
            }
            case BALANCE_ATTRIBUTE -> {
                return String.valueOf(user.getBalance());
            }
            case ACTIVE_ATTRIBUTE -> {
                return String.valueOf(user.getActive());
            }
            default -> {
                return super.getFirstAttribute(name);
            }
        }
    }

    @Override
    public Map<String, List<String>> getAttributes() {
        Map<String, List<String>> attrs = super.getAttributes();
        MultivaluedHashMap<String, String> all = new MultivaluedHashMap<>();
        all.putAll(attrs);
        all.add("username", user.getUsername());
        all.add("email", user.getEmail());
        all.add("balance", String.valueOf(user.getBalance()));
        all.add("active", String.valueOf(user.getActive()));
        return all;
    }

    @Override
    public Stream<String> getAttributeStream(String name) {
        switch (name) {
            case USERNAME_ATTRIBUTE -> {
                return Stream.of(user.getUsername());
            }
            case EMAIL_ATTRIBUTE -> {
                return Stream.of(user.getEmail());
            }
            case BALANCE_ATTRIBUTE -> {
                return Stream.of(String.valueOf(user.getBalance()));
            }
            case ACTIVE_ATTRIBUTE -> {
                return Stream.of(String.valueOf(user.getActive()));
            }
            default -> {
                return super.getAttributeStream(name);
            }
        }
    }

    @Override
    protected Set<RoleModel> getRoleMappingsInternal() {
        if (user.getRoles() == null) {
            return Set.of();
        }
        return this.user.getRoles()
                .stream()
                .map(role -> new RoleAdapter(role, realm))
                .collect(Collectors.toSet());
    }

}
