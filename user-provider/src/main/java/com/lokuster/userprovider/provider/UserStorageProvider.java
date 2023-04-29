package com.lokuster.userprovider.provider;

import com.lokuster.userprovider.adapter.UserAdapter;
import com.lokuster.userprovider.model.User;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.component.ComponentModel;
import org.keycloak.connections.jpa.JpaConnectionProvider;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.ReadOnlyException;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.user.UserLookupProvider;
import org.keycloak.storage.user.UserQueryProvider;
import org.keycloak.storage.user.UserRegistrationProvider;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Slf4j
public class UserStorageProvider implements
        org.keycloak.storage.UserStorageProvider,
        UserRegistrationProvider,
        UserLookupProvider,
        UserQueryProvider,
        CredentialInputValidator {

    protected EntityManager entityManager;

    protected ComponentModel model;
    protected KeycloakSession session;

    UserStorageProvider(KeycloakSession session, ComponentModel model) {
        this.session = session;
        this.model = model;
        this.entityManager = session
                .getProvider(JpaConnectionProvider.class, "user-persistence-unit")
                .getEntityManager();
    }

    @Override
    public void close() {

    }

    @Override
    public boolean supportsCredentialType(String credentialType) {
        return PasswordCredentialModel.TYPE.equals(credentialType);
    }

    @Override
    public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
        return supportsCredentialType(credentialType);
    }

    @Override
    public boolean isValid(RealmModel realm, UserModel user, CredentialInput credentialInput) {
        log.info("isValid user = {} with credentialInput = {}", user, credentialInput);
        if (!supportsCredentialType(credentialInput.getType())) {
            return false;
        }
        Long persistenceId = Long.valueOf(StorageId.externalId(user.getId()));
        String userPassword = entityManager
                .createNamedQuery("getUserPasswordById", String.class)
                .setParameter("id", persistenceId)
                .getSingleResult();
        return userPassword.equals(credentialInput.getChallengeResponse());
    }

    @Override
    public UserModel getUserById(RealmModel realm, String id) {
        log.info("getUserById: " + id);
        Long persistenceId = Long.valueOf(StorageId.externalId(id));
        User entity = entityManager.find(User.class, persistenceId);
        if (entity == null) {
            return null;
        }
        return new UserAdapter(session, realm, model, entity);
    }

    @Override
    public UserModel getUserByUsername(RealmModel realm, String username) {
        log.info("getUserByUsername: " + username);
        List<User> result = entityManager
                .createNamedQuery("getUserByUsername", User.class)
                .setParameter("username", username)
                .getResultList();
        if (result.isEmpty()) {
            return null;
        }
        return new UserAdapter(session, realm, model, result.get(0));
    }

    @Override
    public UserModel getUserByEmail(RealmModel realm, String email) {
        log.info("getUserByEmail: " + email);
        TypedQuery<User> query = entityManager.createNamedQuery("getUserByEmail", User.class);
        query.setParameter("email", email);
        List<User> result = query.getResultList();
        if (result.isEmpty()) {
            return null;
        }
        return new UserAdapter(session, realm, model, result.get(0));
    }

    @Override
    public int getUsersCount(RealmModel realm) {
        log.info("getUsersCount");
        return entityManager
                .createNamedQuery("getUserCount", Integer.class)
                .getSingleResult();
    }

    @Override
    public Stream<UserModel> searchForUserStream(RealmModel realm, String search, Integer firstResult, Integer maxResults) {
        log.info("searchForUserStream realm={}, search={}, firstResult={}, maxResults={}",
                realm, search, firstResult, maxResults);
        if (search.equals("*") || search.isBlank()) {
            return getAllUserStream(realm, firstResult, maxResults);
        }
        return entityManager.createNamedQuery("searchForUser", User.class)
                .setParameter("search", "%" + search.toLowerCase() + "%")
                .setFirstResult(firstResult)
                .setMaxResults(maxResults)
                .getResultList()
                .stream()
                .map(user -> new UserAdapter(session, realm, model, user));
    }

    private Stream<UserModel> getAllUserStream(RealmModel realm, Integer firstResult, Integer maxResults) {
        return entityManager.createNamedQuery("getAllUsers", User.class)
                .setFirstResult(firstResult)
                .setMaxResults(maxResults)
                .getResultList()
                .stream()
                .map(user -> new UserAdapter(session, realm, model, user));
    }

    @Override
    public Stream<UserModel> searchForUserStream(RealmModel realm, Map<String, String> params, Integer firstResult, Integer maxResults) {
        return Stream.empty();
    }

    @Override
    public Stream<UserModel> getGroupMembersStream(RealmModel realm, GroupModel group, Integer firstResult, Integer maxResults) {
        return Stream.empty();
    }

    @Override
    public Stream<UserModel> searchForUserByUserAttributeStream(RealmModel realm, String attrName, String attrValue) {
        return Stream.empty();
    }

    @Override
    public UserModel addUser(RealmModel realm, String username) {
        throw new ReadOnlyException("User is read only");
    }

    @Override
    public boolean removeUser(RealmModel realm, UserModel user) {
        throw new ReadOnlyException("User is read only");
    }
}
