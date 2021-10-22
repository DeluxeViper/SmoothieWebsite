package com.ceruleansource.SmoothieWebsite.backend.Services;

import com.ceruleansource.SmoothieWebsite.backend.Models.Post;
import com.ceruleansource.SmoothieWebsite.backend.Models.Smoothie;
import com.ceruleansource.SmoothieWebsite.backend.Repositories.PostRepository;
import com.ceruleansource.SmoothieWebsite.backend.Repositories.SmoothieRepository;
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

    public static final String TAG = "PostService";

    @Autowired
    PostRepository postRepository;

    @Autowired
    SmoothieRepository smoothieRepository;

    /**
     *
     * @return - returns all posts (primarily to display in the forum page)
     */
    @Transactional
    public Set<Post> retrieveAllPosts() {
        Iterable<Post> posts = postRepository.findAll();
        Set<Post> postSet = new HashSet<>();
        posts.forEach(postSet::add);

        return postSet;
    }

    /**
     *
     * @param post - saves this into database
     * @return - boolean: true if save was successful - otherwise returns false.
     */
    @Transactional
    public boolean savePost(Post post) {
        post.getSmoothie().setPost(post);
        Smoothie savedSmoothie = smoothieRepository.save(post.getSmoothie());
//        System.out.println(TAG + ": savedSmoothie: " + savedSmoothie);
//        System.out.println(TAG + ": savedPost: " + savedSmoothie.getPost());
        return postRepository.findById(savedSmoothie.getPost().getId()).isPresent();
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

    @Transactional
    public Post getPost(Long postId) {
        Optional<Post> post = postRepository.findById(postId);

        return post.orElse(null);
    }

}
