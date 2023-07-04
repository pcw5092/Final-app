package org.edupoll.model.dto;

import org.edupoll.model.entity.FeedAttach;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FeedAttachWrapper {
	private String mediaUrl; // 업로드
	private String type; // 사진 or 동영상
	
	public FeedAttachWrapper(FeedAttach e) {
		this.type = e.getType();
		this.mediaUrl = e.getMediaUrl();
	}
}
