package io.swagger.service;

import io.swagger.model.Comments;
import io.swagger.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import io.swagger.api.NotFoundException;

@Service
public class CommentService {

    private static final Logger log = LoggerFactory.getLogger(CommentService.class);

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comments createComment(Comments comment) {
        log.info("Creating comment: {}", comment);
        return commentRepository.save(comment);
    }

    public Comments getCommentById(Long commentId) throws NotFoundException {
        log.info("Requesting comment with ID: {}", commentId);
        return commentRepository.findById(commentId)
                .orElseThrow(() -> {
                    log.error("Comment with ID {} not found", commentId);
                    return new NotFoundException(404, "Comment not found with ID: " + commentId);
                });
    }

    public Comments updateComment(Long commentId, Comments comment) throws NotFoundException {
        log.info("Updating comment with ID: {}", commentId);
        if (!commentRepository.existsById(commentId)) {
            log.error("Comment with ID {} not found", commentId);
            throw new NotFoundException(404, "Comment not found with ID: " + commentId);
        }
        comment.setId(commentId);
        return commentRepository.save(comment);
    }

    public void deleteComment(Long commentId) throws NotFoundException {
        log.info("Deleting comment with ID: {}", commentId);
        if (!commentRepository.existsById(commentId)) {
            log.error("Comment with ID {} not found", commentId);
            throw new NotFoundException(404, "Comment not found with ID: " + commentId);
        }
        commentRepository.deleteById(commentId);
    }
}