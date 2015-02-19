package common;

import java.util.Arrays;
import java.util.List;

public class Constants {
	
	public static boolean WITHOUT_LOGIN = true;
	
	public static  List<String> ICON_SET = Arrays.asList("bmp", "c", "cpp", "css", "dat", "doc", "docx", "exe", "gif", "html", "java", "jpg", "js", "pdf", "php", "png", "ppt", "pptx", "py", "rar",
			"rtf", "sql", "txt", "xls", "xlsx", "xml", "zip");
	
	public static final int SUCCESS = 200;
	public static final int NOT_FOUND = 404;
	public static final int NOT_AUTHORIZED = 401;
	public static final int INTERNAL_ERROR = 500;
	
	public static final String NOT_FOUND_TEXT = "Die Sharepoint-Ressource wurde nicht gefunden!";
	public static final String NOT_AUTHORIZED_TEXT = "Der Username oder das Passwort war nicht richtig!";
	public static final String INTERNAL_ERROR_TEXT = "Es ist ein interner Fehler aufgetreten. Mehr Inforamtionen finden Sie im Log.";
	
	public static final int LOGIN_VIEW = 1;
	public static final int SHAREPOINT_VIEW = 2;
}
