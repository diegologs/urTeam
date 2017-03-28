package urteam.community;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import urteam.ConstantsUrTeam;
import urteam.urteamController;
import urteam.news.News;
import urteam.user.User;
import urteam.user.UserComponent;
import urteam.user.UserRepository;


@Service
public class CommunityService {

	@Autowired
	private CommunityRepository repository;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserComponent userComponent;
	
	@Autowired
	private urteamController urteam;
	

	public Community findOne(long id) {
		return repository.findOne(id);
	}

	public List<Community> findAll() {
		return repository.findAll();
	}
	
	public void save(Community community) {
		
	repository.save(community);
		
	}

	public void saveOwner(Community community) {
		if(isOwner(community)){
			repository.save(community);
		}
	}

	public void delete(long id) {
		repository.delete(id);
	}

	public void editInfo(Community community, String info){
		community.setInfo(info);
		saveOwner(community);
	}
	
	public void addNews(Community community, News news){
		community.getNews().add(news);
		saveOwner(community);
	}
	
	
	public boolean isOwner(Community community){
		
		User ownerCommunity = userRepo.findOne(community.getOwner_id().getId());
		
		if ((userComponent.isLoggedUser())) {
			long userLogged_id = userComponent.getLoggedUser().getId();
			User userLogged = userRepo.findOne(userLogged_id);
					

			if (userLogged.getId() == ownerCommunity.getId()) {
					return true;
			}else{
				return false;
			}
		
		}else{
				return false;
			
				
		}
	}
	
	public void addImage(Community community, MultipartFile file){
		
				/*
				if (urteam.uploadImageFile(model, file, filename, ConstantsUrTeam.COMMUNITY_IMGS,
						community.getCommunityId())) {
					community.addImage(filename);
				}
				
				*/
				saveOwner(community);
			
			
	}

	public void add(Community community, MultipartFile file) {
		
		SimpleDateFormat formater = new SimpleDateFormat("mmddyyyy-hhMMss");
		Date date = new Date();

		// EventId generator
		SimpleDateFormat communityIdFormater = new SimpleDateFormat("mmddyyyy-hhMMss");
		String communitytId = communityIdFormater.format(date);
		community.setCommunityId(communitytId);
		community.setOwner_id(userComponent.getLoggedUser());
	
		String filename = "avatar-" + formater.format(date);
	
		/*
		if (urteam.uploadImageFile(model, file, filename, ConstantsUrTeam.COMMUNITY_AVATAR,
			community.getCommunityId())) {
			community.setMain_photo(filename);
		}
				
		*/
		save(community);
		
	}

	public void follow(Community community) {
		
		if ((userComponent.isLoggedUser())) {
		
			long userLogged_id = userComponent.getLoggedUser().getId();
			User userLogged = userRepo.findOne(userLogged_id);
			
			if (userLogged.getCommunityList().contains(community)) {
				userLogged.removeCommunity(community);
			} else {
				userLogged.addCommunity(community);
			}
			
			save(community);
			userRepo.save(userLogged);
		}

		
		
	}

}