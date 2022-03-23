package com.espritx.client.entities;

import com.codename1.properties.IntProperty;
import com.codename1.properties.Property;
import com.codename1.properties.PropertyBusinessObject;
import com.codename1.properties.PropertyIndex;

public class Group implements PropertyBusinessObject {
    public final IntProperty<Group> id = new IntProperty<>("id");
    public final Property<String, Group> display_name = new Property<>("display_name");
    public final Property<String, Group> security_title = new Property<>("security_title");
    public final Property<String, Group> group_type = new Property<>("group_type");
    public final PropertyIndex idx = new PropertyIndex(this, "Group", id, display_name, security_title, group_type);

    @Override
    public PropertyIndex getPropertyIndex() {
        return idx;
    }
}
