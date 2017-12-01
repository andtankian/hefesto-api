package br.com.hefesto.resources.impl.user.holder;

import br.com.wsbasestructure.dto.SearchModel;
import br.com.wsbasestructure.dto.abstracts.AbstractHolder;
import java.util.ArrayList;

/**
 *
 * @author andrew
 */
public class UserHomeWidgetsHolder extends AbstractHolder {

    public UserHomeWidgetsHolder() {
        entities = new ArrayList();
        sm = new SearchModel();
        recentInteractionsHomeWidget = new RecentInteractionsHomeWidget();
    }
    
    

    private RecentInteractionsHomeWidget recentInteractionsHomeWidget;

    public RecentInteractionsHomeWidget getRecentInteractionsHomeWidget() {
        return recentInteractionsHomeWidget;
    }

    public void setRecentInteractionsHomeWidget(RecentInteractionsHomeWidget recentInteractionsHomeWidget) {
        this.recentInteractionsHomeWidget = recentInteractionsHomeWidget;
    }
}
