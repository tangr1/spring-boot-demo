package hello.service;

import hello.domain.Topic;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

@Service
public interface TopicRepository extends PagingAndSortingRepository<Topic, Long> {
}
