package com.example.snapsolve.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.snapsolve.dto.community.CommentDTO;
import com.example.snapsolve.dto.community.CommentResponseDTO;
import com.example.snapsolve.models.Comment;
import com.example.snapsolve.services.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // Lấy tất cả comment gốc của một bài viết
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentResponseDTO>> getRootCommentsByPostId(@PathVariable Long postId) {
        List<Comment> comments = commentService.getRootCommentsByPostId(postId);
        List<CommentResponseDTO> commentDTOs = comments.stream()
                .map(CommentResponseDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(commentDTOs, HttpStatus.OK);
    }

    // Lấy tất cả reply của một comment
    @GetMapping("/{commentId}/replies")
    public ResponseEntity<List<CommentResponseDTO>> getRepliesByParentCommentId(@PathVariable Long commentId) {
        List<Comment> replies = commentService.getRepliesByParentCommentId(commentId);
        List<CommentResponseDTO> replyDTOs = replies.stream()
                .map(CommentResponseDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(replyDTOs, HttpStatus.OK);
    }

    // Tạo comment mới cho bài viết
    @PostMapping
    public ResponseEntity<CommentResponseDTO> createComment(@RequestBody CommentDTO commentDTO) {
        Comment newComment = commentService.createComment(commentDTO);
        CommentResponseDTO responseDTO = new CommentResponseDTO(newComment);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    // Tạo reply cho một comment
    @PostMapping("/reply")
    public ResponseEntity<CommentResponseDTO> createReply(@RequestBody CommentDTO commentDTO) {
        Comment newReply = commentService.createReply(commentDTO);
        CommentResponseDTO responseDTO = new CommentResponseDTO(newReply);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    // Lấy thông tin một comment theo ID
    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDTO> getCommentById(@PathVariable Long id) {
        Comment comment = commentService.getCommentById(id);
        if (comment != null) {
            CommentResponseDTO responseDTO = new CommentResponseDTO(comment);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Xóa comment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Đếm số lượng comment gốc của một bài viết
    @GetMapping("/post/{postId}/count")
    public ResponseEntity<Long> countRootCommentsByPostId(@PathVariable Long postId) {
        long count = commentService.countRootCommentsByPostId(postId);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}