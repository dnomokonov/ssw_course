package io.swagger.api;

import io.swagger.model.Comments;
import io.swagger.service.CommentService;
import io.swagger.api.NotFoundException;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2025-03-26T05:32:19.242722438Z[GMT]")
@RestController
public class CommentApiController implements CommentApi {

    private static final Logger log = LoggerFactory.getLogger(CommentApiController.class);

    private final CommentService commentService;

    public CommentApiController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Override
    public ResponseEntity<Comments> addComment(
            @Parameter(in = ParameterIn.DEFAULT, description = "Create a new comment", required = true, schema = @Schema())
            @Valid @RequestBody Comments body) {
        log.info("Adding new comment: {}", body);
        Comments createdComment = commentService.createComment(body);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteComment(
            @Parameter(in = ParameterIn.PATH, description = "Comment ID to delete", required = true, schema = @Schema())
            @PathVariable("cmntId") Long cmntId,
            @Parameter(in = ParameterIn.HEADER, description = "", schema = @Schema())
            @RequestHeader(value = "api_key", required = false) String apiKey) throws NotFoundException {
        log.info("Deleting comment with ID: {}", cmntId);
        commentService.deleteComment(cmntId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Comments> getCommentById(
            @Parameter(in = ParameterIn.PATH, description = "ID of comment to return", required = true, schema = @Schema())
            @PathVariable("cmntId") Long cmntId) throws NotFoundException {
        log.info("Fetching comment with ID: {}", cmntId);
        Comments comment = commentService.getCommentById(cmntId);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Comments> updateComment(
            @Parameter(in = ParameterIn.DEFAULT, description = "Update an existing comment", required = true, schema = @Schema())
            @Valid @RequestBody Comments body) throws NotFoundException {
        log.info("Updating comment: {}", body);
        Comments updatedComment = commentService.updateComment(body.getId(), body);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateCommentWithForm(
            @Parameter(in = ParameterIn.PATH, description = "ID of comment that needs to be updated", required = true, schema = @Schema())
            @PathVariable("cmntId") Long cmntId,
            @Parameter(in = ParameterIn.QUERY, description = "Text of comment that needs to be updated", schema = @Schema())
            @Valid @RequestParam(value = "text", required = false) String text) throws NotFoundException {
        log.info("Updating comment with ID: {} with new text: {}", cmntId, text);
        Comments comment = new Comments(cmntId, text);
        commentService.updateComment(cmntId, comment);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}