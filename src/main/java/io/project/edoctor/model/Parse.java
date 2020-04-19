package io.project.edoctor.model;

import java.awt.*;
import java.util.List;

public class Parse {

    //nlp
    private List<Mention> mentions;
    private boolean obvious;

    public Parse(List<Mention> mentions, boolean obvious) {
        this.mentions = mentions;
        this.obvious = obvious;
    }

    public List<Mention> getMentions() {
        return mentions;
    }

    public void setMentions(List<Mention> mentions) {
        this.mentions = mentions;
    }

    public boolean isObvious() {
        return obvious;
    }

    public void setObvious(boolean obvious) {
        this.obvious = obvious;
    }
}
