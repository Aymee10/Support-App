package dev.aymee.support_api.topic;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class TopicService implements ITopicService{
    private final TopicRepository topicRepository;

        public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }
    @Override
    public List<TopicDto> getAllTopics() {
    return topicRepository.findAllByOrderByIdAsc()
            .stream()
            .map(this::convertToTopicDto)
            .collect(Collectors.toList());
}


private TopicDto convertToTopicDto(TopicEntity topic) {
    TopicDto dto = new TopicDto();
    dto.setId(topic.getId());
    dto.setName(topic.getName());
    return dto;
}
    
}
