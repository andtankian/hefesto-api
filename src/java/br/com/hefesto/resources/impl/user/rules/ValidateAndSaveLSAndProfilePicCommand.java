package br.com.hefesto.resources.impl.user.rules;

import br.com.hefesto.domain.impl.User;
import br.com.hefesto.domain.impl.UserVisual;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.Message;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.impl.GenericImageHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.interfaces.ICommand;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import org.hibernate.Session;
import org.imgscalr.Scalr;

/**
 *
 * @author Andrew Ribeiro
 */
public class ValidateAndSaveLSAndProfilePicCommand implements ICommand {

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        User user = (User) holder.getEntities().get(0);
        UserVisual uv;
        Message m = new Message();
        boolean isValid = true;
        if (!(holder instanceof GenericImageHolder)) {
            m.setError("it's not ImageHolder");
            flowContainer.getResult().setMessage(m);
            flowContainer.getResult().setStatus(Result.FATAL_ERROR);
            flowContainer.getFc().setMustContinue(false);
            return holder;
        }
        long actualmillis = Calendar.getInstance().getTimeInMillis();

        /*
        Let's verify if user exists
         */
        Long id = user.getId();

        Session s = flowContainer.getSession();
        User loaded = (User) s.get(User.class, id);
        if (loaded == null) {
            m.setError("user doesn't exist");
            isValid = false;
        } else {
            uv = loaded.getUserVisual();
            GenericImageHolder gih = (GenericImageHolder) holder;
            InputStream is = gih.getIs();

            File folder = new File(".." + File.separator + ".." + File.separator + "images" + File.separator);

            if (!folder.exists()) {
                //Checks if folder doesn't exist

                folder.mkdirs(); //Create a new folder
            }

            //Create new File object. As this time, this file represents the image that will be persisted.
            File image = new File(".." + File.separator
                    + ".." + File.separator
                    + "images" + File.separator
                    + actualmillis + ".jpg");
            try {
                image.createNewFile(); //it always creates a new file
                ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next();
                ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();
                jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                jpgWriteParam.setCompressionQuality(1f);

                jpgWriter.setOutput(new FileImageOutputStream(image));
                BufferedImage b = ImageIO.read(is);
                IIOImage outputImage = new IIOImage(b, null, null);
                jpgWriter.write(null, outputImage, jpgWriteParam);
                jpgWriter.reset();
                int[] thumbs = {400, 80, 230, 500};

                for (int i = 0; i < thumbs.length; i++) {
                    image = new File(".." + File.separator
                            + ".." + File.separator
                            + "images" + File.separator
                            + thumbs[i] + "x" + thumbs[i]
                            + actualmillis + ".jpg");
                    image.createNewFile();
                    jpgWriter.setOutput(new FileImageOutputStream(image));
                    outputImage = new IIOImage(Scalr.resize(b, thumbs[i]), null, null);
                    jpgWriter.write(null, outputImage, jpgWriteParam);
                    jpgWriter.reset();
                }
                jpgWriter.dispose();
                b.flush();
            } catch (IOException e) {
                isValid = false;
                m.setError("write error");
            } finally {
                try {
                    is.close();
                } catch (IOException ex) {
                    Logger.getLogger(ValidateAndSaveLSAndProfilePicCommand.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            uv.setDateReg(new Timestamp(actualmillis));
            uv.setLandscape("");
            uv.setProfile("/wsbasestructure/images/images/" + actualmillis + ".jpg");
            user.setUserVisual(uv);
            loaded.merge(user);
            holder.getEntities().set(0, loaded);
        }

        if (!isValid) {
            flowContainer.getResult().setStatus(Result.ERROR);
            flowContainer.getResult().setMessage(m);
            flowContainer.getFc().setMustContinue(false);
            flowContainer.getResult().setHolder(holder);
        }

        ((GenericImageHolder) holder).setIs(null);

        return holder;
    }
}
