package urteam.community;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import urteam.ConstantsUrTeam;
import urteam.urTeamService;
import urteam.news.News;
import urteam.news.NewsRepository;
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
	private NewsRepository newsRepo;

	@Autowired
	private UserComponent userComponent;

	@Autowired
	private urTeamService urteamService;
	
	@Autowired
	private urTeamService imageService;

	public Community findOne(long id) {
		return repository.findOne(id);
	}

	public Page<Community> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public void save(Community community) {

		repository.save(community);

	}

	public void delete(long id) {
		repository.delete(id);
	}

	public boolean editInfo(Community community, String info) {
		if (isOwner(community)) {
			community.setInfo(info);

			save(community);
			return true;
		} else {
			return false;

		}

	}

	public boolean edit(long id, Community community) {
		Community community2 = repository.getOne(id);
		if (community2 != null) {
			community.setId(community2.getId());
			save(community);
			return true;
		} else {
			return false;

		}

	}

	public boolean isOwner(Community community) {

		User ownerCommunity = userRepo.findOne(community.getOwner_id().getId());
		System.out.println(userComponent.isLoggedUser());
		if ((userComponent.isLoggedUser())) {
			long userLogged_id = userComponent.getLoggedUser().getId();
			User userLogged = userRepo.findOne(userLogged_id);

			if (userLogged.getId() == ownerCommunity.getId()) {
				return true;
			} else {
				return false;
			}

		} else {
			return false;

		}
	}

	public void addImage(Community community, MultipartFile file) {
		try {

			SimpleDateFormat formater = new SimpleDateFormat("mmddyyyy-hhMMss");
			Date date = new Date();

			String filename = "imageingallery-" + formater.format(date);

			if (urteamService.uploadImageFile(file, filename, ConstantsUrTeam.COMMUNITY_IMGS,
					community.getCommunityId())) {
				community.addImage(filename);
				save(community);
			}

		} catch (Exception e) {
			
			e.printStackTrace();
		}

	}

	public boolean add(Community community, MultipartFile file) {

		try {

			if ((userComponent.isLoggedUser())) {
				SimpleDateFormat formater = new SimpleDateFormat("mmddyyyy-hhMMss");
				Date date = new Date();

				// EventId generator
				SimpleDateFormat communityIdFormater = new SimpleDateFormat("mmddyyyy-hhMMss");
				String communitytId = communityIdFormater.format(date);
				community.setCommunityId(communitytId);
				community.setOwner_id(userComponent.getLoggedUser());

				String filename = "avatar-" + formater.format(date);

				/*
				if (urteamService.uploadImageFile(file, filename, ConstantsUrTeam.COMMUNITY_AVATAR,
						community.getCommunityId())) {
					community.setMain_photo(filename);
				}
				*/

				save(community);
				return true;
			}else{
				return false;
			}

		} catch (Exception e) {
			return false;
		
		}

	}

	public void follow(Community community) {

		if ((userComponent.isLoggedUser())) {

			long userLogged_id = userComponent.getLoggedUser().getId();
			User userLogged = userRepo.findOne(userLogged_id);

			if (!userLogged.getCommunityList().contains(community)) {
				userLogged.addCommunity(community);
			}

			save(community);
			userRepo.save(userLogged);
		}

	}
	
	public void unfollow(Community community) {

		if ((userComponent.isLoggedUser())) {

			long userLogged_id = userComponent.getLoggedUser().getId();
			User userLogged = userRepo.findOne(userLogged_id);

			if (userLogged.getCommunityList().contains(community)) {
				userLogged.removeCommunity(community);
			}
			

			save(community);
			userRepo.save(userLogged);
		}

	}


	public Page<Community> findAll(PageRequest pageRequest) {
		return repository.findAll(pageRequest);
	}

	public List<Community> findByName(String name) {
		return repository.findByName(name);
	}

	public List<Community> findBySport(String sport) {
		return repository.findBySport(sport);
	}

	public boolean addNews(Community community, News news) {
		if (isOwner(community)) {

			community.getNews().add(news);
			newsRepo.save(news);
			save(community);
			return true;
		}else{
			return false;
		}

	}

	public List<News> findNews(Community community) {

		return community.getNews();

	}

	public void setImage(Community community, MultipartFile file) {

		try {

			

				SimpleDateFormat formater = new SimpleDateFormat("mmddyyyy-hhMMss");
				Date date = new Date();

				String filename = "avatar-" + formater.format(date);

				if (urteamService.uploadImageFile(file, filename, ConstantsUrTeam.COMMUNITY_AVATAR,
						community.getCommunityId())) {
					community.setMain_photo(filename);
					save(community);
				}

			

		} catch (Exception e) {
			
			e.printStackTrace();
		}

	}

	public List<Community> getCommunitiesByCriteria(String criteria) {
		List<Community> foundCommunities = repository.findByNameContaining(criteria);
		if (foundCommunities != null) {
			return foundCommunities;
		} else {
			return null;
		}
	}

	public List<User> getfollowers(Community community) {
		return community.getUsers_IDs();
		
	}
	
	public byte[] getCommunityAvatar(long id) throws FileNotFoundException, IOException{
		Community community = repository.findOne(id);
		if(community != null){
			 byte[] file = imageService.getFile(ConstantsUrTeam.COMMUNITY_AVATAR, community.getCommunityId(),
					community.getMain_photo()); 
			if(file != null){
				return file;
			}
		} else {
			return null;
		}
		return null;
	}
	
	
	public byte[] getCommunityImg(long id, String img) throws FileNotFoundException, IOException{
		Community community = repository.findOne(id);
		if(community != null){
			 byte[] file = imageService.getFile(ConstantsUrTeam.COMMUNITY_IMGS, community.getCommunityId(),
					img); 
			if(file != null){
				return file;
			}
		} else {
			return null;
		}
		return null;
	}


}