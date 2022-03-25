package com.espritx.client.entities;

import com.codename1.properties.*;

import java.util.Objects;

public class User implements PropertyBusinessObject {
    public final IntProperty<User> id = new IntProperty<>("id");
    public final Property<String, User> first_name = new Property<>("first_name");
    public final Property<String, User> last_name = new Property<>("last_name");
    public final Property<String, User> email = new Property<>("email");
    public final Property<String, User> userStatus = new Property<>("userStatus");
    public final Property<String, User> classe = new Property<>("class");
    public final Property<String, User> avatarFile = new Property<>("avatarFile");
    public final Property<String, User> identityType = new Property<>("identityType");
    public final Property<String, User> phoneNumber = new Property<>("phoneNumber");
    public final Property<String, User> identityDocumentNumber = new Property<>("identityDocumentNumber");
    public final Property<String, User> plainPassword = new Property<>("plainPassword");
    public final ListProperty<Group, User> groups = new ListProperty<>("groups", Group.class);

    public final PropertyIndex idx = new PropertyIndex(this, "User",
            id, avatarFile, first_name, last_name, email, phoneNumber, classe, userStatus,
            identityType, identityDocumentNumber,
            groups, plainPassword
    );

    public boolean isStudent() { return this.isOfGroup("student"); }
    public boolean isSuperAdmin() { return this.isOfGroup("super admin"); }
    public boolean isSiteStaff() { return this.isOfGroup("site staff"); }
    public boolean isFacultyStaff() { return this.isOfGroup("faculty staff"); }
    public boolean isTeacher() { return this.isOfGroup("teachers"); }

    private boolean isOfGroup(String groupName) {
        for (Group g : this.groups.asList()) {
            if (g.group_type.get().equals(groupName)) return true;
        }
        return false;
    }

    @Override
    public PropertyIndex getPropertyIndex() {
        return idx;
    }

    public User() {
        avatarFile.setLabel("Avatar");
        first_name.setLabel("First Name");
        last_name.setLabel("Last Name");
        email.setLabel("E-Mail");
        phoneNumber.setLabel("Phone Number");
        userStatus.setLabel("User Status");
        classe.setLabel("Enrolled in Class");
        identityType.setLabel("Identity Document Type");
        identityDocumentNumber.setLabel("Identity Document Number");
        plainPassword.setLabel("Plain Password");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id.getInt() == user.id.getInt();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
