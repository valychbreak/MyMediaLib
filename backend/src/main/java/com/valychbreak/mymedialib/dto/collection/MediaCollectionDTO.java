package com.valychbreak.mymedialib.dto.collection;

import com.valychbreak.mymedialib.data.movie.impl.MediaFullDetailsImpl;

import java.util.List;

public class MediaCollectionDTO {
    private Long id;
    private String name;
    private MediaCollectionDTO parent;

    private List<MediaCollectionDTO> subCollections;

    // Not using interface here because Gson lib works only with fields
    private List<MediaFullDetailsImpl> mediaList;

    protected MediaCollectionDTO() {
    }

    public MediaCollectionDTO(Long id, String name, MediaCollectionDTO parent, List<MediaFullDetailsImpl> mediaList, List<MediaCollectionDTO> subCollections) {
        this.id = id;
        this.name = name;
        this.parent = parent;
        this.subCollections = subCollections;
        this.mediaList = mediaList;
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

    public List<MediaCollectionDTO> getSubCollections() {
        return subCollections;
    }

    public void setSubCollections(List<MediaCollectionDTO> subCollections) {
        this.subCollections = subCollections;
    }

    public List<MediaFullDetailsImpl> getMediaList() {
        return mediaList;
    }

    public void setMediaList(List<MediaFullDetailsImpl> mediaList) {
        this.mediaList = mediaList;
    }

    public MediaCollectionDTO getParent() {
        return parent;
    }

    public void setParent(MediaCollectionDTO parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MediaCollectionDTO)) return false;

        MediaCollectionDTO that = (MediaCollectionDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (subCollections != null ? !subCollections.equals(that.subCollections) : that.subCollections != null) return false;
        return mediaList != null ? mediaList.equals(that.mediaList) : that.mediaList == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (subCollections != null ? subCollections.hashCode() : 0);
        result = 31 * result + (mediaList != null ? mediaList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MediaCollectionDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subCollections=" + subCollections +
                ", mediaList=" + mediaList +
                '}';
    }
}
