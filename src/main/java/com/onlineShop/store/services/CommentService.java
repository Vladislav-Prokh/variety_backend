package com.onlineShop.store.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineShop.store.entities.Comment;
import com.onlineShop.store.entities.CommentStatus;
import com.onlineShop.store.entities.Product;
import com.onlineShop.store.entities.User;
import com.onlineShop.store.repositories.CommentRepository;

@Service
public class CommentService {
	
	@Autowired
	private CommentRepository commentRep;
	
	@Autowired
	private UserUtils userUtils;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductService productService;
	
	public List<Comment> findAllCommentForProductById(long productId){
		return commentRep.findByUser_Id(productId);
	}
	
	public boolean setCommentForProduct(long productId, String userEmail, String comment) {
		
		boolean hasUserBoughtThisProduct = userUtils.checkIfUserByEmailBoughtProductById(productId,userEmail);
		
		if(!hasUserBoughtThisProduct) {
			return false;
		}
		else {
			boolean ifExistsCommentByUser = commentRep.existsByUserEmailAndProductId(userEmail, productId);
			if(ifExistsCommentByUser) {
				return false;
			}
		}
		User user = userService.findUserByEmail(userEmail);
		Product productToComment  = productService.findProductById(productId);
		Comment newComment = new Comment(null,comment,LocalDate.now(),user,CommentStatus.NonReadByModerator,productToComment);
		commentRep.save(newComment);
		return true;
		
	}
	
	
	public List<Comment> findByStatus(CommentStatus status){
		return commentRep.findByStatus(status);
	}
	
	public List<Comment> findByProductId(long productId){
		return commentRep.findByProductId(productId);
	}
	
	public void deleteById(long id) {
		 this.commentRep.deleteById(id);
	}

	public void deleteAllCommentsByProductId(Long id) {
		commentRep.deleteAllCommentsByProductId(id);
		
	}

	public void changeStatusToRead(Integer commentId) {
		Comment comment = commentRep.findById((long)commentId).get();
		if(comment!=null) {
			comment.setStatus(CommentStatus.ReadByModerator);
			commentRep.save(comment);
		}
	}
	

}
