package br.com.wsbasestructure.dto;

import br.com.wsbasestructure.domain.abstracts.Entity;
import java.util.List;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Andrew Ribeiro
 */
public class SearchModel {

    private Long limit;
    private Long offset;
    private String[] sort;
    private List filters;
    private String between;
    private Entity entity;
    private String search;

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public void getBasics(UriInfo uriInfo) {
        String slimit = uriInfo.getQueryParameters().get("limit") != null ? uriInfo.getQueryParameters().get("limit").get(0) : "5";
        String soffset = uriInfo.getQueryParameters().get("offset") != null ? uriInfo.getQueryParameters().get("offset").get(0) : "0";
        try {
            this.limit = Long.parseLong(slimit);
        } catch (NumberFormatException n) {
            this.limit = (long) 5;
        }

        try {
            Long number = Long.parseLong(soffset);
            this.offset = number;
        } catch (NumberFormatException n) {
            this.offset = (long) 0;
        }

        filters = uriInfo.getQueryParameters().get("filters");

        between = uriInfo.getQueryParameters().get("between") != null ? uriInfo.getQueryParameters().get("between").get(0) : "";

    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public String[] getSort() {
        return sort;
    }

    public void setSort(String[] sort) {
        this.sort = sort;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public List getFilters() {
        return filters;
    }

    public void setFilters(List filters) {
        this.filters = filters;
    }

    public String getBetween() {
        return between;
    }

    public void setBetween(String between) {
        this.between = between;
    }

}
