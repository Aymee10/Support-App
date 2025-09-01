package dev.aymee.support_api.topic;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<TopicEntity, Long>  {

    List<TopicEntity> findAllByOrderByIdAsc();
    Optional<TopicEntity> findByName(String name);
}
