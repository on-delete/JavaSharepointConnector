package service.model;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SharepointModel {
	
	private String creation;
	private String lastModified;
	private String href;
	private String modifiedBy;
	private String displayName;
	private String downloadLocation = null;
	private String project = null;
	private boolean isFolder;
	private List<SharepointModel> subItems = null;
	private InputStream fileContent = null;
	
	/**
	 * Constructor for files.
	 * @param href
	 * @param author
	 * @param last_modified
	 * @param isFolder
	 */
	public SharepointModel(Date creation, Date lastModified, String displayName, String href, boolean isFolder, String modifiedBy) {
		super();
		setCreation(creation);
		setLastModified(lastModified);
		this.displayName = displayName;
		this.href = href;
		this.modifiedBy = modifiedBy;
		this.isFolder = isFolder;
	}

	/**
	 * Constructor for folders.
	 * @param href
	 * @param modifiedBy
	 * @param last_modified
	 * @param isFolder
	 */
	public SharepointModel(Date creation, Date lastModified, String displayName, String href, boolean isFolder) {
		super();
		setCreation(creation);
		setLastModified(lastModified);
		this.displayName = displayName;
		this.href = href;
		this.isFolder = isFolder;
	}

	public String getCreation() {
		return creation;
	}

	public void setCreation(Date creation) {
		if(creation!=null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(creation);
			this.creation = String.valueOf(calendar.get(Calendar.YEAR)) + "-" + String.valueOf(calendar.get(Calendar.MONTH)+1) + "-" + String.valueOf(calendar.get(Calendar.DATE)) + "T" + String.valueOf(calendar.get(Calendar.HOUR)) +":"+ String.valueOf(calendar.get(Calendar.MINUTE)) + ":"+ String.valueOf(calendar.get(Calendar.SECOND)) + "Z";
		}
		else
			this.creation =  null;
	}

	public String getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		if(lastModified!=null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(lastModified);
			this.lastModified = String.valueOf(calendar.get(Calendar.YEAR)) + "-" + String.valueOf(calendar.get(Calendar.MONTH)+1) + "-" + String.valueOf(calendar.get(Calendar.DATE)) + "T" + String.valueOf(calendar.get(Calendar.HOUR)) +":"+ String.valueOf(calendar.get(Calendar.MINUTE)) + ":"+ String.valueOf(calendar.get(Calendar.SECOND)) + "Z";
		}
		else
			this.lastModified =  null;
	}

	public String getHref() {
		return href;
	}
	
	public String getHrefAsURI(){
		String hrefUri = this.href.replace(" ", "%20");
		return hrefUri;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDownloadLocation() {
		return downloadLocation;
	}

	public void setDownloadLocation(String downloadLocation) {
		this.downloadLocation = downloadLocation;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public boolean isFolder() {
		return isFolder;
	}

	public void setFolder(boolean isFolder) {
		this.isFolder = isFolder;
	}

	public List<SharepointModel> getSubItems() {
		return subItems;
	}
	
	
	public void setSubItems(List<SharepointModel> subItems) {
		this.subItems = subItems;
	}

	public InputStream getFileContent() {
		return fileContent;
	}

	public void setFileContent(InputStream fileContent) {
		this.fileContent = fileContent;
	}
}
