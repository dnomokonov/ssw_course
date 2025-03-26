package io.swagger.service;

import io.swagger.api.NotFoundException;
import io.swagger.model.Comments;
import io.swagger.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    private Comments comment;

    @BeforeEach
    public void setUp() {
        comment = new Comments(1L, "Test comment");
    }

    @Test
    public void testCreateComment_Success() {
        when(commentRepository.save(any(Comments.class))).thenReturn(comment);

        Comments createdComment = commentService.createComment(comment);

        assertNotNull(createdComment);
        assertEquals(1L, createdComment.getId());
        assertEquals("Test comment", createdComment.getText());

        verify(commentRepository, times(1)).save(any(Comments.class));
    }

    @Test
    public void testGetCommentById_Success() {
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        Comments foundComment = null;
        try {
            foundComment = commentService.getCommentById(1L);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }

        assertNotNull(foundComment);
        assertEquals(1L, foundComment.getId());
        assertEquals("Test comment", foundComment.getText());

        verify(commentRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetCommentById_NotFound() {
        when(commentRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            commentService.getCommentById(1L);
        });

        assertEquals("Comment not found with ID: 1", exception.getMessage());
        assertEquals(404, exception.getCode());

        verify(commentRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateComment_Success() {
        when(commentRepository.existsById(1L)).thenReturn(true);
        when(commentRepository.save(any(Comments.class))).thenReturn(comment);

        Comments updatedComment = null;
        try {
            updatedComment = commentService.updateComment(1L, comment);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }

        assertNotNull(updatedComment);
        assertEquals(1L, updatedComment.getId());
        assertEquals("Test comment", updatedComment.getText());

        verify(commentRepository, times(1)).existsById(1L);
        verify(commentRepository, times(1)).save(any(Comments.class));
    }

    @Test
    public void testUpdateComment_NotFound() {
        when(commentRepository.existsById(1L)).thenReturn(false);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            commentService.updateComment(1L, comment);
        });

        assertEquals("Comment not found with ID: 1", exception.getMessage());
        assertEquals(404, exception.getCode());

        verify(commentRepository, times(1)).existsById(1L);
        verify(commentRepository, never()).save(any(Comments.class));
    }

    @Test
    public void testDeleteComment_Success() {
        when(commentRepository.existsById(1L)).thenReturn(true);
        doNothing().when(commentRepository).deleteById(1L);

        try {
            commentService.deleteComment(1L);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }

        verify(commentRepository, times(1)).existsById(1L);
        verify(commentRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteComment_NotFound() {
        OngoingStubbing<Boolean> booleanOngoingStubbing = when(commentRepository.existsById(1L)).thenReturn(false);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            commentService.deleteComment(1L);
        });

        assertEquals("Comment not found with ID: 1", exception.getMessage());
        assertEquals(404, exception.getCode());

        verify(commentRepository, times(1)).existsById(1L);
        verify(commentRepository, never()).deleteById(1L);
    }
}
