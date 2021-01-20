package com.ceruleansource.SmoothieWebsite.backend.Services;

import com.ceruleansource.SmoothieWebsite.backend.Models.Post;
import com.ceruleansource.SmoothieWebsite.backend.Repositories.PostRepository;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.server.StreamResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    @Transactional
    public Set<Post> retrieveAllPosts() {
        Iterable<Post> posts = postRepository.findAll();
        Set<Post> postSet = new HashSet<>();
        posts.forEach(postSet::add);

        return postSet;
    }

    @Transactional
    public void savePost(Post post) {

    }

    @Transactional
    public Image generateImage(Post post) {
        Long id = post.getId();
        StreamResource sr = new StreamResource("post", () -> {
            Optional<Post> postOptional = postRepository.findById(id);
            return postOptional.map(value -> new ByteArrayInputStream(value.getPostImage())).orElse(null);
        });
        sr.setContentType("image/png");
        return new Image(sr, "post-picture");
    }

}
