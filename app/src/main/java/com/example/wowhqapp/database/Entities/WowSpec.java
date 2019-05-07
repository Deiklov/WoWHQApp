package com.example.wowhqapp.database.Entities;
// package com.example;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.example.wowhqapp.database.Interfaces.IEntity;
import com.squareup.moshi.Json;

@Entity(
        indices = {
                @Index(value = {"classId"})
        },
        foreignKeys = @ForeignKey(
            entity = WowClass.class,
            parentColumns = "id",
            childColumns = "classId"
        )
)
public class WowSpec implements IEntity {
    @NonNull
    @PrimaryKey
    @Json(name = "id")
    private int id;
    @NonNull
    @Json(name = "class_id")
    private int classId;
    @Json(name = "name")
    private String name;
    @Json(name = "description")
    private String description;
    @Json(name = "icon")
    private String icon;

    /**
     * No args constructor for use in serialization
     * 
     */
    public WowSpec() {
    }

    /**
     * 
     * @param icon
     * @param classId
     * @param description
     * @param name
     * @param id
     */
    @Ignore
    public WowSpec(int classId, int id, String name, String description, String icon) {
        super();
        this.classId = classId;
        this.id = id;
        this.name = name;
        this.description = description;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int specId) { this.id = specId; }

    public int getClassId() { return classId; }

    public void setClassId(int classId) { this.classId = classId; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}
