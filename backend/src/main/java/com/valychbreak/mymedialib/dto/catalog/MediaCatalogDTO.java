package com.valychbreak.mymedialib.dto.catalog;

import com.valychbreak.mymedialib.data.movie.impl.MediaFullDetailsImpl;

import java.util.ArrayList;
import java.util.List;

public class MediaCatalogDTO {
    private Long id;
    private String name;
    private MediaCatalogDTO parent;

    private List<MediaCatalogDTO> subCatalogs = new ArrayList<>();

    // Not using interface here because Gson lib works only with fields
    private List<MediaFullDetailsImpl> mediaList;

    public MediaCatalogDTO(Long id, String name, List<MediaFullDetailsImpl> mediaList, List<MediaCatalogDTO> subCatalogs) {
        this(id, name, null, mediaList, subCatalogs);
    }

    public MediaCatalogDTO(Long id, String name, MediaCatalogDTO parent, List<MediaFullDetailsImpl> mediaList, List<MediaCatalogDTO> subCatalogs) {
        this.id = id;
        this.name = name;
        this.parent = parent;
        this.subCatalogs = subCatalogs;
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

    public List<MediaCatalogDTO> getSubCatalogs() {
        return subCatalogs;
    }

    public void setSubCatalogs(List<MediaCatalogDTO> subCatalogs) {
        this.subCatalogs = subCatalogs;
    }

    public List<MediaFullDetailsImpl> getMediaList() {
        return mediaList;
    }

    public void setMediaList(List<MediaFullDetailsImpl> mediaList) {
        this.mediaList = mediaList;
    }

    public MediaCatalogDTO getParent() {
        return parent;
    }

    public void setParent(MediaCatalogDTO parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MediaCatalogDTO)) return false;

        MediaCatalogDTO that = (MediaCatalogDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (subCatalogs != null ? !subCatalogs.equals(that.subCatalogs) : that.subCatalogs != null) return false;
        return mediaList != null ? mediaList.equals(that.mediaList) : that.mediaList == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (subCatalogs != null ? subCatalogs.hashCode() : 0);
        result = 31 * result + (mediaList != null ? mediaList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MediaCatalogDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subCatalogs=" + subCatalogs +
                ", mediaList=" + mediaList +
                '}';
    }
}
