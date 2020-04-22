package io.project.edoctor.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Symptom {

    private String id;

    private String name;

    @JsonProperty("common_name")
    private String commonName;

    private String question;

    @JsonProperty("sex_filter")
    private String sexFilter;

    private String category;

    private String seriousness;

    private Object extras;

    private List<Children> children;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("image_source")
    private String imageSource;

    @JsonProperty("parent_id")
    private String parentId;

    @JsonProperty("parent_relation")
    private String parentRelation;

    public Symptom() {
    }

    public Symptom(String id, String name, String commonName, String question, String sexFilter, String category, String seriousness, Object extras, List<Children> children, String imageUrl, String imageSource, String parentId, String parentRelation) {
        this.id = id;
        this.name = name;
        this.commonName = commonName;
        this.question = question;
        this.sexFilter = sexFilter;
        this.category = category;
        this.seriousness = seriousness;
        this.extras = extras;
        this.children = children;
        this.imageUrl = imageUrl;
        this.imageSource = imageSource;
        this.parentId = parentId;
        this.parentRelation = parentRelation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getSexFilter() {
        return sexFilter;
    }

    public void setSexFilter(String sexFilter) {
        this.sexFilter = sexFilter;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSeriousness() {
        return seriousness;
    }

    public void setSeriousness(String seriousness) {
        this.seriousness = seriousness;
    }

    public Object getExtras() {
        return extras;
    }

    public void setExtras(Object extras) {
        this.extras = extras;
    }

    public List<Children> getChildren() {
        return children;
    }

    public void setChildren(List<Children> children) {
        this.children = children;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentRelation() {
        return parentRelation;
    }

    public void setParentRelation(String parentRelation) {
        this.parentRelation = parentRelation;
    }
}
