package hello.service;

import hello.domain.Startup;
import hello.domain.Topic;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Service;

@Service
@RepositoryRestResource(collectionResourceRel = "startups", path = "startups")
public interface StartupRepository extends PagingAndSortingRepository<Startup, Long> {
}
