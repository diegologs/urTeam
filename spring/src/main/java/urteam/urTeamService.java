package urteam;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

@Service
public class urTeamService {
	
	public Boolean uploadImageFile( MultipartFile file, String name, String type, String generatedId) throws Exception {

		String folderPath = "imgs";

		if (!file.isEmpty()) {
			String fileName = name + ".jpeg";
			try {
				switch (type) {
				case ConstantsUrTeam.USER_AVATAR:
					folderPath = ConstantsUrTeam.USERS_AVATAR_FOLDER;
					break;
				case ConstantsUrTeam.EVENT_AVATAR:
					folderPath = ConstantsUrTeam.EVENTS_FOLDER + "/" + generatedId;
					break;
				case ConstantsUrTeam.EVENT_IMGS:
					folderPath = ConstantsUrTeam.EVENTS_FOLDER + "/" + generatedId + "/gallery";
					break;
				case ConstantsUrTeam.COMMUNITY_AVATAR:
					folderPath = ConstantsUrTeam.COMMUNITIES_FOLDER + "/" + generatedId;
					break;
				case ConstantsUrTeam.COMMUNITY_IMGS:
					folderPath = ConstantsUrTeam.COMMUNITIES_FOLDER + "/" + generatedId + "/gallery";
					break;
				default:
					break;
				}
				File filesFolder = new File(folderPath);
				if (!filesFolder.exists()) {
					filesFolder.mkdirs();
				}
				File uploadedFile = new File(filesFolder.getAbsolutePath(), fileName);
				file.transferTo(uploadedFile);

				return true;

			} catch (Exception e) {
				throw new Exception(fileName + e.getClass().getName() + ":" + e.getMessage());
 
			}
		} else {
			return false;
		}

	}

	public void handleFileDownload(String type, String generatedId,
			@PathVariable String fileName, HttpServletResponse res) throws FileNotFoundException, IOException {

		String filePath = null;

		switch (type) {
		case ConstantsUrTeam.USER_AVATAR:
			filePath = ConstantsUrTeam.USERS_AVATAR_FOLDER + "/" + fileName + ".jpeg";
			break;
		case ConstantsUrTeam.EVENT_AVATAR:
			filePath = ConstantsUrTeam.EVENTS_FOLDER + "/" + generatedId + "/" + fileName + ".jpeg";
			break;
		case ConstantsUrTeam.EVENT_IMGS:
			filePath = ConstantsUrTeam.EVENTS_FOLDER + "/" + generatedId + "/gallery/" + fileName + ".jpeg";
			break;
		case ConstantsUrTeam.COMMUNITY_AVATAR:
			filePath = ConstantsUrTeam.COMMUNITIES_FOLDER + "/" + generatedId + "/" + fileName + ".jpeg";
			break;
		case ConstantsUrTeam.COMMUNITY_IMGS:
			filePath = ConstantsUrTeam.COMMUNITIES_FOLDER + "/" + generatedId + "/gallery/" + fileName + ".jpeg";
			break;
		default:
			break;
		}
		File file = new File(filePath);

		if (file.exists()) {
			res.setContentType("image/jpeg");
			res.setContentLength(new Long(file.length()).intValue());
			FileCopyUtils.copy(new FileInputStream(file), res.getOutputStream());
		} else {
			res.sendError(404, "File" + fileName + "(" + file.getAbsolutePath() + ") does not exist");
		}
	}
	
	
//	public InputStream getFile(String type, String generatedId,
//			String fileName) throws FileNotFoundException, IOException {
//
//		String filePath = null;
//
//		switch (type) {
//		case ConstantsUrTeam.USER_AVATAR:
//			filePath = ConstantsUrTeam.USERS_AVATAR_FOLDER + "/" + fileName + ".jpeg";
//			break;
//		case ConstantsUrTeam.EVENT_AVATAR:
//			filePath = ConstantsUrTeam.EVENTS_FOLDER + "/" + generatedId + "/" + fileName + ".jpeg";
//			break;
//		case ConstantsUrTeam.EVENT_IMGS:
//			filePath = ConstantsUrTeam.EVENTS_FOLDER + "/" + generatedId + "/gallery/" + fileName + ".jpeg";
//			break;
//		case ConstantsUrTeam.COMMUNITY_AVATAR:
//			filePath = ConstantsUrTeam.COMMUNITIES_FOLDER + "/" + generatedId + "/" + fileName + ".jpeg";
//			break;
//		case ConstantsUrTeam.COMMUNITY_IMGS:
//			filePath = ConstantsUrTeam.COMMUNITIES_FOLDER + "/" + generatedId + "/gallery/" + fileName + ".jpeg";
//			break;
//		default:
//			break;
//		}
//		File file = new File(filePath);
//
//		if (file.exists()) {
//			InputStream inputStream = new FileInputStream(file);
//			return inputStream;
//		}
//		return null;
//	}
}
