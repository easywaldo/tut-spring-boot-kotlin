package com.example.blog

import java.time.LocalDateTime
import jakarta.persistence.*

@Entity
class Article(
		var title: String,
		var headline: String,
		var content: String,
		@ManyToOne var author: User,
		var slug: String = title.toSlug(),
		var addedAt: LocalDateTime = LocalDateTime.now(),
		@Id @GeneratedValue var id: Long? = null)

@Entity
class User(
		var login: String,
		var firstname: String,
		var lastname: String,
		var description: String? = null,
		@Id @GeneratedValue var id: Long? = null)


@Entity
class Tags(
	var tag: String,
	@ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE]) var article: Article,
	@Id @GeneratedValue var id: Long? = null)