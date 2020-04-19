package io.project.edoctor.model;

public class Mention {

    private String id;
    private String name;
    private String common_name;
    private String orth;
    private String choice_id;
    private String type; //symptom ex.

    public Mention(String id, String name, String common_name, String orth, String choice_id, String type) {
        this.id = id;
        this.name = name;
        this.common_name = common_name;
        this.orth = orth;
        this.choice_id = choice_id;
        this.type = type;
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

    public String getCommon_name() {
        return common_name;
    }

    public void setCommon_name(String common_name) {
        this.common_name = common_name;
    }

    public String getOrth() {
        return orth;
    }

    public void setOrth(String orth) {
        this.orth = orth;
    }

    public String getChoice_id() {
        return choice_id;
    }

    public void setChoice_id(String choice_id) {
        this.choice_id = choice_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
