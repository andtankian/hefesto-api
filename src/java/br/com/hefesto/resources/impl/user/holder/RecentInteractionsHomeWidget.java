package br.com.hefesto.resources.impl.user.holder;

/**
 *
 * @author andrew
 */
public class RecentInteractionsHomeWidget {
    
    private Long[] all_days;
    private String[] all_days_formated;
    private Long[] all_interactions;
    private Long[] user_interactions;

    public Long[] getAll_days() {
        return all_days;
    }

    public void setAll_days(Long[] all_days) {
        this.all_days = all_days;
    }

    public Long[] getAll_interactions() {
        return all_interactions;
    }

    public void setAll_interactions(Long[] all_interactions) {
        this.all_interactions = all_interactions;
    }

    public Long[] getUser_interactions() {
        return user_interactions;
    }

    public void setUser_interactions(Long[] user_interactions) {
        this.user_interactions = user_interactions;
    }

    public String[] getAll_days_formated() {
        return all_days_formated;
    }

    public void setAll_days_formated(String[] all_days_formated) {
        this.all_days_formated = all_days_formated;
    }

    
}
