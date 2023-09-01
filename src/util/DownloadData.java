package util;

import java.io.InputStream;


public class DownloadData{
  
    private String contentType;
    private String filename;
    private InputStream stream;   
	
	public String getContentType() {
		return contentType;
	}
	
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public InputStream getStream() {
		return stream;
	}
	
	public void setStream(InputStream stream) {
		this.stream = stream;
	}
}