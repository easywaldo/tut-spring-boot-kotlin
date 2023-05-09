package com.example.blog

import java.time.LocalDateTime
import jakarta.persistence.*
import java.util.*

@Entity
class Article(
		var title: String,
		var headline: String,
		var content: String,
		@ManyToOne var author: User,
		var slug: String = title.toSlug(),
		var addedAt: LocalDateTime = LocalDateTime.now(),
		@Id @GeneratedValue var id: Long? = null,
		@ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.REMOVE])
		var tags: MutableList<Tags>? = null)

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
	@JoinColumn(name = "article")
	@ManyToOne var article: Article,
	@Id @GeneratedValue var id: Long? = null)