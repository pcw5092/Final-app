package org.edupoll.repository;

import java.util.List;

import org.edupoll.model.dto.FeedAttachWrapper;
import org.edupoll.model.entity.FeedAttach;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedAttachRepository extends JpaRepository<FeedAttach, Long> {

}
