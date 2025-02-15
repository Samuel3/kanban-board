package de.mathes.kanban.backend;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.JavaType;
import org.hibernate.type.descriptor.java.CharacterArrayJavaType;

@Entity
public class UserEntity {

    @Id
    Long id;

    String name;

    @JavaType(CharacterArrayJavaType.class)
    char[] password;
}
