package br.com.hefesto.resources.impl.equipments.view;

import br.com.hefesto.domain.impl.Equipment;
import br.com.hefesto.resources.impl.equipments.rules.AppendBulkBeforeNotifyCommand;
import br.com.hefesto.resources.impl.equipments.rules.ValidateBulkEquipmentsCommand;
import br.com.hefesto.resources.impl.rules.NotifyContentCommand;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Calendar;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;

/**
 *
 * @author Andrew Ribeiro
 */
public class NewBulkEquipmentsViewHelper extends AbstractViewHelper {

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        typeRequest = "bulk";
        GenericHolder gh = new GenericHolder();

        InputStream is;
        FormDataMultiPart fdmp = fc.getCr().readEntity(FormDataMultiPart.class);

        try {
            is = fdmp.getField("equipments").getEntityAs(InputStream.class);
        } catch (NullPointerException npe) {
            is = null;
        }
        String bulkContent;
        String[] separated = null;
        try {
            bulkContent = IOUtils.toString(is, "UTF-8");
            bulkContent = bulkContent.trim();
        } catch (IOException ex) {
            bulkContent = null;
        }

        if (bulkContent != null && !bulkContent.isEmpty()) {
            separated = bulkContent.split("____");
        }
        BufferedReader br;
        for (String string : separated) {
            if (string.isEmpty()) {
                continue;
            }
            Equipment e = new Equipment();
            e.setDateReg(new Timestamp(Calendar.getInstance().getTimeInMillis()));
            e.setDescription(string.substring(string.indexOf("Fabricante do sistema:"), string.indexOf("Pasta do Windows:") - 1));
            e.setDescription(e.getDescription() + string.substring(string.indexOf("Memória física total:"), string.indexOf("Memória física disponível:") -1));
            e.setDescription(e.getDescription() + string.substring(string.indexOf("Placa(s) de Rede:")));
            e.setName(string.substring(string.indexOf("Nome do host:") + "Nome do host:".length(), string.indexOf("Nome do sistema operacional:")-1));
            e.setPatrimonial(string.substring(string.indexOf("Patrimonio:") + "Patrimonio: ".length(), string.indexOf("Nome do host:")-1));
            
            gh.getEntities().add(e);
        }
        
        loadBusinessRulesBeforeMainFlow();
        loadBusinessRulesAfterMainFlow();
        return gh;

    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {        
        getRulesBeforeMainFlow().add(new ValidateBulkEquipmentsCommand());
    }

    @Override
    public void loadBusinessRulesAfterMainFlow() {
        getRulesAfterMainFlow().add(new AppendBulkBeforeNotifyCommand());
        getRulesAfterMainFlow().add(new NotifyContentCommand(new String[]{"noland"}));
    }

    
    
}
