package by.bsuir.suite.util;

import org.apache.wicket.Application;
import org.apache.wicket.protocol.http.WebApplication;

import javax.servlet.ServletContext;
import java.io.File;

/**
 * @author i.sukach
 */
public final class UploadUtils {

    private static final String DEFAULT_AVATAR_IMAGE_PATH = "images/avatar.png";
    private static final String AVATAR_FOLDER_NAME = "avatars";
    private static final String AVATAR_FOLDER_WITH_SLASH = "avatars/";

    private UploadUtils() {
    }

    public static String getAvatarFolderFullPath(Application application) {
        ServletContext context = ((WebApplication) application).getServletContext();
        String realPath = context.getRealPath(AVATAR_FOLDER_NAME);
        realPath = realPath.replaceAll("\\\\", "/");
        return realPath;
    }

    public static String generateFilePathForImage(Application application, String fileName) {
        String filePath = DEFAULT_AVATAR_IMAGE_PATH;
        String avatarFolderPath = getAvatarFolderFullPath(application);
        File fold = new File(avatarFolderPath);
        if(fold.exists() && fold.isDirectory()) {
            for(File file : fold.listFiles()){
                if(file.getName().equals(fileName)) {
                    filePath = AVATAR_FOLDER_WITH_SLASH + fileName;
                }
            }
        }
        return filePath;
    }
}
