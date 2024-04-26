package com.sethu.blog.bootstrap;

import com.sethu.blog.configuration.EmailConfiguration;
import com.sethu.blog.entity.Comment;
import com.sethu.blog.entity.Post;
import com.sethu.blog.entity.User;
import com.sethu.blog.repository.CommentRepository;
import com.sethu.blog.repository.PostRepository;
import com.sethu.blog.repository.UserRepository;
import com.sethu.blog.service.email.EmailService;
import com.sethu.blog.service.generator.PasswordGenerator;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;

@AllArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger logger = LogManager.getLogger(DataLoader.class);

    private  UserRepository userRepository;
    private  PostRepository postRepository;
    private  CommentRepository commentRepository;
    private  EmailService emailService;
    private  EmailConfiguration emailConfig;
    @Override
    public void run(String... args) {
        //load();
    }

    private void load(){
        // Create sample user
        User user1 = new User(null, "Sethu", PasswordGenerator.generateDefaultPassword(12), "sethuserge@gmail.com", new ArrayList<>(), new ArrayList<>(), "ADMIN");

        // Save user to the database
        User existingUser1 = userRepository.findUserByEmail(user1.getEmail());

        if(existingUser1 == null){
            userRepository.save(user1);
            logger.info("New users saved to the database");
            emailService.sendEmail(user1.getEmail(),emailConfig.getEmailSubject(),user1.getUsername(),emailConfig.getEmailBody());
            logger.info("Send registration email to "+user1.getUsername()+ " at "+ user1.getEmail());


            // Create sample posts
            Post post1 = new Post(null, "Post 1 Title", "Post 1 Content", new Date(), user1, new ArrayList<>());
            Post post2 = new Post(null, "Post 2 Title", "Post 2 Content", new Date(), user1, new ArrayList<>());

            // Save posts to the database
            postRepository.save(post1);
            postRepository.save(post2);
            logger.info("New Posts saved to the database");

            // Create sample comments
            Comment comment1 = new Comment(null, "Comment 1 Content", new Date(), user1, post1);
            Comment comment2 = new Comment(null, "Comment 2 Content", new Date(), user1, post2);

            // Save comments to the database
            commentRepository.save(comment1);
            commentRepository.save(comment2);
            logger.info("New Comments saved to the database");
        } else {
            logger.warn("User(s) already exist");
        }
    }
}
