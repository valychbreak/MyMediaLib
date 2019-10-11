package com.valychbreak.mymedialib.dto.collection;

import com.valychbreak.mymedialib.data.movie.impl.MediaFullDetailsImpl;
import com.valychbreak.mymedialib.dto.UserDTO;

import java.util.List;

public class MediaCollectionDTO {
    private Long id;
    private String name;

    // Not using interface here because Gson lib works only with fields
    private List<MediaFullDetailsImpl> mediaList;

    private UserDTO owner;

    protected MediaCollectionDTO() {
    }

    public MediaCollectionDTO(Long id, String name, List<MediaFullDetailsImpl> mediaList, UserDTO owner) {
        this.id = id;
        this.name = name;
        this.mediaList = mediaList;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MediaFullDetailsImpl> getMediaList() {
        return mediaList;
    }

    public void setMediaList(List<MediaFullDetailsImpl> mediaList) {
        this.mediaList = mediaList;
    }

    public UserDTO getOwner() {
        return owner;
    }

    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MediaCollectionDTO)) return false;

        MediaCollectionDTO that = (MediaCollectionDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return mediaList != null ? mediaList.equals(that.mediaList) : that.mediaList == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (mediaList != null ? mediaList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MediaCollectionDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mediaList=" + mediaList +
                '}';
    }
}
