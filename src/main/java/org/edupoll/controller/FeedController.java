package org.edupoll.controller;

import java.io.IOException;
import java.util.List;

import org.edupoll.exception.NotExistUserException;
import org.edupoll.model.dto.FeedAttachWrapper;
import org.edupoll.model.dto.FeedWrapper;
import org.edupoll.model.dto.request.CreateFeedRequest;
import org.edupoll.model.dto.response.FeedListResponse;
import org.edupoll.model.entity.FeedAttach;
import org.edupoll.service.FeedService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/feed")
@RequiredArgsConstructor
@CrossOrigin
public class FeedController {

	private final FeedService feedService;

	// ======================================================================================
	// 신규 글 등록해주는 API
	@PostMapping("/storage")
	public ResponseEntity<?> createNewFeedHandle(@AuthenticationPrincipal String principal, CreateFeedRequest request)
			throws NotExistUserException, IllegalStateException, IOException {

		boolean r = feedService.createNewFeed(principal, request);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	// ======================================================================================
	// 전체 글(or 특정조건의) 목록 제공해주는 API
	@GetMapping("/post")
	public ResponseEntity<?> readallFeedHandle(@RequestParam(defaultValue = "1") int page) {
		
		Long total = feedService.size();
		List<FeedWrapper> feeds = feedService.allItems(page);
		
		FeedListResponse response = new FeedListResponse(total, feeds);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// ======================================================================================
	// 특정 글 제공해주는 API
	public ResponseEntity<?> readSpecificFeedHandleA() {

		return null;
	}

	public ResponseEntity<?> readSpecificFeedHandleB() {

		return null;
	}

	// ======================================================================================
	// 특정 글 삭제해주는 API
	@DeleteMapping("")
	public ResponseEntity<?> deleteSpecificFeedHandle() {

		return null;
	}

}
