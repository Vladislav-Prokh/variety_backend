package com.onlineShop.store.contollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.onlineShop.store.entities.Comment;
import com.onlineShop.store.entities.CommentStatus;
import com.onlineShop.store.services.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@RequestMapping(value = "/find/{status}",method = RequestMethod.GET)
	public List<Comment> findByStatus(@PathVariable("status") CommentStatus status){
		return commentService.findByStatus(status);
	}
	@RequestMapping(value = "/{productId}",method = RequestMethod.GET)
	public List<Comment> findByProductId(@PathVariable("productId") Integer productId){
		return commentService.findByProductId((long)productId);
	}
	@RequestMapping(value = "/delete/{productId}",method = RequestMethod.POST)
	public void deleteById(@PathVariable("productId") Integer productId) {
		 commentService.deleteById((long) productId);
	}
	@RequestMapping(value = "/add/{productId}/{userEmail}/{comment}",method = RequestMethod.POST)
	public boolean addComentToProduct(@PathVariable("productId") Integer productId,@PathVariable("userEmail") String userEmail, @PathVariable("comment") String comment) {
		return commentService.setCommentForProduct((long)productId, userEmail, comment);
	}
	@RequestMapping(value = "/changeStatusToRead/{comment_id}",method = RequestMethod.POST)
	public void changeStatusToRead(@PathVariable("comment_id") Integer commentId) {
		commentService.changeStatusToRead(commentId);
	}

}
