package com.lokuster.userprovider.adapter;

import com.lokuster.userprovider.model.Role;
import lombok.RequiredArgsConstructor;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleContainerModel;
import org.keycloak.models.RoleModel;
import org.keycloak.storage.ReadOnlyException;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class RoleAdapter implements RoleModel {
    private final Role role;
    private final RealmModel realmModel;

    @Override
    public String getName() {
        return this.role.getName();
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void setDescription(String s) {
        throw new ReadOnlyException("role is read only");
    }

    @Override
    public String getId() {
        return String.valueOf(this.role.getId());
    }

    @Override
    public void setName(String s) {
        throw new ReadOnlyException("role is read only");
    }

    @Override
    public boolean isComposite() {
        return false;
    }

    @Override
    public void addCompositeRole(RoleModel roleModel) {
        throw new ReadOnlyException("role is read only");
    }

    @Override
    public void removeCompositeRole(RoleModel roleModel) {
        throw new ReadOnlyException("role is read only");
    }

    @Override
    public Stream<RoleModel> getCompositesStream(String s, Integer integer, Integer integer1) {
        return Stream.empty();
    }

    @Override
    public boolean isClientRole() {
        return false;
    }

    @Override
    public String getContainerId() {
        return realmModel.getId();
    }

    @Override
    public RoleContainerModel getContainer() {
        return this.realmModel;
    }

    @Override
    public boolean hasRole(RoleModel roleModel) {
        return this.equals(roleModel) || this.role.getName().equals(roleModel.getName());
    }

    @Override
    public void setSingleAttribute(String s, String s1) {
        throw new ReadOnlyException("role is read only");
    }

    @Override
    public void setAttribute(String s, List<String> list) {
        throw new ReadOnlyException("role is read only");
    }

    @Override
    public void removeAttribute(String s) {
        throw new ReadOnlyException("role is read only");
    }

    @Override
    public Stream<String> getAttributeStream(String s) {
        return Stream.empty();
    }

    @Override
    public Map<String, List<String>> getAttributes() {
        return Map.of();
    }

    @Override
    public String toString() {
        return "RoleAdapter{" +
                "role=" + role.getName() +
                '}';
    }
}
