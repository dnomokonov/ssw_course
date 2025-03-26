package io.swagger.api;

import io.swagger.model.Comments;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentApiController.class)
public class ApiControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    private Comments comment;

    @BeforeEach
    public void setUp() {
        comment = new Comments(1L, "Test comment");
    }

    @Test
    public void testAddComment_Success() throws Exception {
        Comments newComment = new Comments(null, "New comment");
        Comments createdComment = new Comments(1L, "New comment");

        when(commentService.createComment(any(Comments.class))).thenReturn(createdComment);

        mockMvc.perform(post("/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newComment)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.text").value("New comment"));

        verify(commentService, times(1)).createComment(any(Comments.class));
    }

    @Test
    public void testDeleteComment_Success() throws Exception {
        doNothing().when(commentService).deleteComment(1L);

        mockMvc.perform(delete("/comment/1")
                        .header("api_key", "test-api-key"))
                .andExpect(status().isNoContent());

        verify(commentService, times(1)).deleteComment(1L);
    }

    @Test
    public void testGetCommentById_Success() throws Exception {
        when(commentService.getCommentById(1L)).thenReturn(comment);

        mockMvc.perform(get("/comment/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.text").value("Test comment"));

        verify(commentService, times(1)).getCommentById(1L);
    }


    @Test
    public void testUpdateComment_Success() throws Exception {
        Comments updatedComment = new Comments(1L, "Updated comment");

        when(commentService.updateComment(eq(1L), any(Comments.class))).thenReturn(updatedComment);

        mockMvc.perform(put("/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedComment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.text").value("Updated comment"));

        verify(commentService, times(1)).updateComment(eq(1L), any(Comments.class));
    }

    @Test
    public void testUpdateCommentWithForm_Success() throws Exception {
        when(commentService.updateComment(eq(1L), any(Comments.class))).thenReturn(comment);

        mockMvc.perform(post("/comment/1")
                        .param("text", "Updated text"))
                .andExpect(status().isNoContent());

        verify(commentService, times(1)).updateComment(eq(1L), any(Comments.class));
    }
}
