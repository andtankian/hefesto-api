package br.com.hefesto.resources.impl.tickets.maintenance.dao;

import br.com.wsbasestructure.dto.impl.GenericHolder;

/**
 *
 * @author Andrew
 */
public class UpdatingMaintenanceTicketHolder extends GenericHolder{
    
    private String changedTitle;
    private String changedDescription;
    private String changedPriority;
    private String changedService;
    private String changedResponsible;
    private String changedOwner;
    private String changedEquipment;
    private String changedRequestedProducts;

    public String getChangedTitle() {
        return changedTitle;
    }

    public void setChangedTitle(String changedTitle) {
        this.changedTitle = changedTitle;
    }

    public String getChangedDescription() {
        return changedDescription;
    }

    public void setChangedDescription(String changedDescription) {
        this.changedDescription = changedDescription;
    }

    public String getChangedPriority() {
        return changedPriority;
    }

    public void setChangedPriority(String changedPriority) {
        this.changedPriority = changedPriority;
    }

    public String getChangedService() {
        return changedService;
    }

    public void setChangedService(String changedService) {
        this.changedService = changedService;
    }

    public String getChangedResponsible() {
        return changedResponsible;
    }

    public void setChangedResponsible(String changedResponsible) {
        this.changedResponsible = changedResponsible;
    }

    public String getChangedOwner() {
        return changedOwner;
    }

    public void setChangedOwner(String changedOwner) {
        this.changedOwner = changedOwner;
    }

    public String getChangedEquipment() {
        return changedEquipment;
    }

    public void setChangedEquipment(String changedEquipment) {
        this.changedEquipment = changedEquipment;
    }

    public String getChangedRequestedProducts() {
        return changedRequestedProducts;
    }

    public void setChangedRequestedProducts(String changedRequestedProducts) {
        this.changedRequestedProducts = changedRequestedProducts;
    }
    
}
