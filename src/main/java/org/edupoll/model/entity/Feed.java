package org.edupoll.model.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "feeds")
public class Feed {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "writerId" , referencedColumnName = "email")
	@JoinColumn(name = "writerId")
	private User writer; // 작성자

	private String description; // 본문
	private Long viewCount; // 조회수
	
	@OneToMany(mappedBy = "feed")
	private List<FeedAttach> attaches;
	
}
