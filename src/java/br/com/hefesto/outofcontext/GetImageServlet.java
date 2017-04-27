package br.com.hefesto.outofcontext;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.DirectColorModel;
import java.awt.image.PixelGrabber;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Andrew Ribeiro
 */
@WebServlet(name = "GetImageServlet", urlPatterns = {"/images/*"})
public class GetImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = req.getPathInfo();
        String ext = null;
        File f = null;
        BufferedImage bi = null;
        boolean isValid = true;
        if (path != null) {
            String[] splitted = path.split("/");
            if (splitted != null && splitted.length > 0 && !splitted.equals("/")) {
                String type = req.getParameter("type") != null ? req.getParameter("type") : "500x500";
                ext = splitted[splitted.length - 1].substring(
                        splitted[splitted.length - 1].length() - 4,
                        splitted[splitted.length - 1].length());
                path = "/";
                for (int i = 0; i < splitted.length; i++) {
                    String string = splitted[i];
                    if(string.equals("")) continue;
                    else if((i + 1) == splitted.length) path+=type;
                    path += string;
                    if ((i + 1) != splitted.length) {
                        path += "/";
                    }
                }
            } else {
                isValid = false;
            }
        } else {
            isValid = false;
        }

        if (isValid) {
            isValid = true;
            f = new File(new StringBuilder("..").append(File.separator).append("..").append(path).toString());
            f.getAbsolutePath();
            if (f.exists()) {
                if (ext != null) {
                    ext = ext.equals(".jpg") ? "jpeg" : ext.split("\\.")[1];
                    if (ext.equals("jpeg")) {
                        int[] RGB_MASKS = {0xFF0000, 0xFF00, 0xFF};
                        ColorModel RGB_OPAQUE
                                = new DirectColorModel(32, RGB_MASKS[0], RGB_MASKS[1], RGB_MASKS[2]);

                        Image img = null;
                        try {
                            img = Toolkit.getDefaultToolkit().createImage(f.getCanonicalPath());
                        } catch (IOException ex) {
                            isValid = false;
                        }

                        PixelGrabber pg = new PixelGrabber(img, 0, 0, -1, -1, true);
                        try {
                            pg.grabPixels();
                        } catch (InterruptedException ex) {
                            isValid = false;
                        }
                        int width = pg.getWidth(), height = pg.getHeight();

                        DataBuffer buffer = new DataBufferInt((int[]) pg.getPixels(), pg.getWidth() * pg.getHeight());
                        WritableRaster raster = Raster.createPackedRaster(buffer, width, height, width, RGB_MASKS, null);
                        bi = new BufferedImage(RGB_OPAQUE, raster, false, null);
                    } else {
                        bi = ImageIO.read(f);
                    }
                }
            } else {
                isValid = false;
            }
        }
        
        if(isValid){
            OutputStream o = resp.getOutputStream();
            
            ImageIO.write(bi, ext, o);
            o.close();
        } else {
            resp.sendError(404);
        }
    }

}
