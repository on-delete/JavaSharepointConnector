package solr;

public class MetaContent {

	private String fileName = null;
	private String contentType = null;
	private String content = null;
	
	public MetaContent(){}
	
	public MetaContent(String fileName, String contentType, String content) {
		super();
		this.fileName = fileName;
		this.contentType = contentType;
		this.content = content;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
