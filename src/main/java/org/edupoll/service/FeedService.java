package org.edupoll.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.edupoll.exception.NotExistUserException;
import org.edupoll.model.dto.FeedAttachWrapper;
import org.edupoll.model.dto.FeedWrapper;
import org.edupoll.model.dto.UserWrapper;
import org.edupoll.model.dto.request.CreateFeedRequest;
import org.edupoll.model.entity.Feed;
import org.edupoll.model.entity.FeedAttach;
import org.edupoll.model.entity.User;
import org.edupoll.repository.FeedAttachRepository;
import org.edupoll.repository.FeedRepository;
import org.edupoll.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedService {

	private final UserRepository userRepository;
	private final FeedRepository feedRepository;
	private final FeedAttachRepository feedAttachRepository;

	@Value("${upload.server}")
	String uploadServer;

	@Value("${upload.basedir}")
	String uploadBaseDir;

	// 새글 등록 요청
	@Transactional
	public boolean createNewFeed(String principal, CreateFeedRequest req)
			throws NotExistUserException, IllegalStateException, IOException {
		// 1. Feed Entity 생성 후 save
		User user = userRepository.findByEmail(principal).orElseThrow(() -> new NotExistUserException());

		Feed feed = new Feed();
		feed.setDescription(req.getDescription());
		feed.setViewCount(0L);
		feed.setWriter(user);
		var save = feedRepository.save(feed);

		log.info("attaches is exist ? {}", req.getAttaches() != null);
		if (req.getAttaches() != null) { // 파일이 너머 왔다면,
			// 2. FeedAttaches 생성 후 save

			// 기본 저장 장소
			File uploadDirectory = new File(uploadBaseDir + "/feed/" + save.getId());
			uploadDirectory.mkdirs();

			for (MultipartFile multi : req.getAttaches()) { // 하나씩 반복문 돌면서

				// 어디에 File 옮겨둘 것인지 File 객체로 정의 하고
				String fileName = String.valueOf(System.currentTimeMillis());
				String extension = multi.getOriginalFilename().split("\\.")[1];
				File dest = new File(uploadDirectory, fileName + "." + extension);

				multi.transferTo(dest); // 옮기는 작업 진행

				// 업로드가 끝나면 DB에 기록
				FeedAttach feedAttach = new FeedAttach();
				feedAttach.setType(multi.getContentType());

				// 업로드를 한 곳이 어디냐에 따라서 결정이 되는 값
				feedAttach.setMediaUrl(
						uploadServer + "/resource/feed/" + save.getId() + "/" + fileName + "." + extension);
				feedAttach.setFeed(save);

				feedAttachRepository.save(feedAttach);

			}

		}

		return true;

	}

	public Long size() {

		return feedRepository.count();

	}

	public List<FeedWrapper> allItems(int page) {
		// 10개만 보내게 페이징 처리
		List<Feed> entityList = feedRepository.findAll(PageRequest.of(page - 1, 10, Sort.by("id").descending()))
				.toList();
		
		//List<Feed> entity = feedRepository.findAllOrderByIdDesc(page);
		
		return entityList.stream().map(e -> new FeedWrapper(e)).toList();
	}

}
