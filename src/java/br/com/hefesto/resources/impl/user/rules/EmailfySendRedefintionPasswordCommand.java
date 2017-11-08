package br.com.hefesto.resources.impl.user.rules;

import br.com.hefesto.domain.impl.User;
import br.com.wsbasestructure.dto.CustomEmail;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.Message;
import br.com.wsbasestructure.dto.impl.HefestoAuthenticator;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.interfaces.ICommand;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import javax.mail.MessagingException;

/**
 *
 * @author andrew
 */
public class EmailfySendRedefintionPasswordCommand implements ICommand {

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {

        User u = (User) holder.getEntities().get(0);
        Message m = new Message();
        StringBuilder emailBuilder = new StringBuilder();

        emailBuilder.append("<!DOCTYPE html><html lang=\"pt-BR\"><head>  <meta charset=\"utf-8\">  <title>Esqueceu a senha - Hefesto - Sistema de gerenciamento de tickets e solicitações de TI.</title></head><body>  <div style=\"padding: 0px; margin: 0;\"><img style=\"margin: 0 auto; display: table;\" src=\"data:image/png;base64,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\"      alt=\"\" />  </div>  <div style=\"text-align: center;\">    <h3>Redefinição de senha <strong><span style=\"color:#607D8B; text-transform: uppercase;\">Hefesto</span></strong>.</h3>    <p><strong>")
                .append(u.getFullName())
                .append("</strong>,<br/>Você esqueceu a senha e solicitou uma redefinição.    </p>    <p>Para isso, clique neste <a target=\"blank\" href=\"")
                .append(flowContainer.getHttprequest().getServletContext().getInitParameter("base-uri"))
                .append("/hefesto/usuarios/redefinepass/")
                .append(u.getUserConfig().getForgotPasswordCurrentToken())
                .append("\" style=\"text-transform: uppercase; text-decoration: none; color: #607D8B; font-weight: bolder;\">link</a> ou copie e cole em seu navegador favorito o seguinte endereço:</p>    <p style=\"color:#607D8B;\">")
                .append(flowContainer.getHttprequest().getServletContext().getInitParameter("base-uri"))
                .append("/hefesto/usuarios/redefinepass/")
                .append(u.getUserConfig().getForgotPasswordCurrentToken())
                .append("<br/></p><p>Dica: <em>Não se esqueça de estar utilizando a rede da escola para redefinir. :)</em></p>    <br/>    <br/>    <br/>     <br/>    <p><small>Este email foi enviado automaticamente pelo sistema. Não responda este email, pois não receberá feedback.</small></p>  </div></body></html>");

        CustomEmail ce = new CustomEmail("smtp", "smtp.office365.com", "587", new HefestoAuthenticator(), "hefesto.noreply@etec.sp.gov.br");
        ce.setBody(emailBuilder.toString());
        ce.setFrom("hefesto.noreply@etec.sp.gov.br");
        ce.setFromName("Hefesto - Sistema de gerenciamento tickets e solicitações de TI");
        ce.setRecipients(new ArrayList());
        ce.getRecipients().add(u.getEmail());
        ce.setSender(ce.getFrom());
        ce.setSenderName(ce.getFromName());
        ce.setSubject("Redefinição de senha para Hefesto - Sistema de gerenciamento de tickets e solicitações de TI");

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    ce.send();
                } catch (MessagingException | UnsupportedEncodingException ex) {
                    m.setError(ex.getMessage());
                }
            }
        };
        
        new Thread(runnable).start();

        return holder;
    }

}
