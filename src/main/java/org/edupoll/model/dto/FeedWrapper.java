package org.edupoll.model.dto;

import java.util.List;

import org.edupoll.model.entity.Feed;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FeedWrapper {

	public FeedWrapper(Feed e) {
		this.id = e.getId();
		this.description = e.getDescription();
		this.viewCount = e.getViewCount();
		this.writer = new UserWrapper(e.getWriter());

		this.attaches = e.getAttaches().stream().map(t -> new FeedAttachWrapper(t)).toList();
	}

	private Long id;
	private UserWrapper writer;
	private String description; // 본문
	private Long viewCount; // 조회수
	private List<FeedAttachWrapper> attaches;

}
