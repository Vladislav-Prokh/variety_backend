package com.onlineShop.store.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String comment;
	@Column
	private LocalDate date;
	@ManyToOne
	private User user;
	@Enumerated(EnumType.STRING)
	private CommentStatus status;
	@ManyToOne
	private Product product;
	public Comment(String comment, LocalDate date, User user, CommentStatus status, Product product) {
		super();
		this.comment = comment;
		this.date = date;
		this.user = user;
		this.status = status;
		this.product = product;
	}
	
	

}
