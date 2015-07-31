package com.gtan.singerdream.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by user on 15-7-31.
 */
public class CustomPage<T> {

    @JsonProperty("_embedded")
    private List<T> embedded;

    private LittlePage page;

    public CustomPage() {
    }

    public CustomPage(List<T> embedded, LittlePage page) {
        this.embedded = embedded;
        this.page = page;
    }

    public static <T> CustomPage<T> getFromPage(Page<T> page) {
        return new CustomPage<T>(
                page.getContent(), new LittlePage(page.getSize(),
                page.getTotalElements(), page.getTotalPages(),
                page.getNumber()));
    }

    public List<T> getEmbedded() {
        return embedded;
    }

    public void setEmbedded(List<T> embedded) {
        this.embedded = embedded;
    }

    public LittlePage getPage() {
        return page;
    }

    public void setPage(LittlePage page) {
        this.page = page;
    }
}
