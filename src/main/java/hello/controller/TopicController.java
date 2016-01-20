package hello.controller;

import hello.domain.Topic;
import hello.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
public class TopicController {

    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private MutableAclService mutableAclService;

    @RequestMapping(value = "/topics", method = RequestMethod.GET)
    public Iterable<Topic> list() {
        return topicRepository.findAll();
    }

    @RequestMapping(value = "/topics", method = RequestMethod.POST)
    @Transactional
    public Topic create(final @RequestBody Topic request) {
        Topic topic = topicRepository.save(request);
        ObjectIdentity identity = new ObjectIdentityImpl(topic);
        MutableAcl acl = mutableAclService.createAcl(identity);

        acl.insertAce(acl.getEntries().size(), BasePermission.ADMINISTRATION,
                new PrincipalSid(SecurityContextHolder.getContext().getAuthentication()),
                true);

        mutableAclService.updateAcl(acl);
        return topic;
    }

    @RequestMapping(value = "/topics/{id}", method = RequestMethod.DELETE)
    @Transactional
    public void delete(final @PathVariable Long id) {
        Topic topic = topicRepository.findOne(id);
        ObjectIdentity identity = new ObjectIdentityImpl(topic);
        mutableAclService.deleteAcl(identity, true);
        topicRepository.delete(id);
    }

    @RequestMapping("/topics/{id}")
    @PreAuthorize("hasPermission(#id, 'hello.domain.Topic', 'administration')")
    public Topic get(final @PathVariable Long id) {
        return topicRepository.findOne(id);
    }
}
