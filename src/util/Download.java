package util;

import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;


public class Download {
    private static Download instance= new Download(); 
    private Download() {

    }

    public static Download getInstance(){
		return instance;
    }

    public void performDownload(
    			DownloadData downloadData, 
				HttpServletResponse response){
        try {
            // Get servlet output stream
            ServletOutputStream out = response.getOutputStream();

            // Set content type
            response.setContentType(downloadData.getContentType());

            if (downloadData.getFilename() != null) {
                response.setHeader("Content-Disposition",
                    "attachment; filename=\"" + downloadData.getFilename()
                    + "\"");
            }

            // A buffer
            byte[] buffer = new byte[1024];

            // Get download data input stream
            InputStream in = downloadData.getStream();

            int bytes = 0;

            // Read all data
            while ((bytes = in.read(buffer)) != -1) {
                // Write data on the servlet output stream
                out.write(buffer, 0, bytes);
            }

            // Close download data input stream
            in.close();

            // Close servlet output stream
            out.close();
        } catch (Exception e) {
        }
    }   
}
