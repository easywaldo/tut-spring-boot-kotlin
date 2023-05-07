package com.example.blog

import org.springframework.data.repository.CrudRepository

interface ArticleRepository : CrudRepository<Article, Long> {
	fun findBySlug(slug: String): Article?
	fun findAllByOrderByAddedAtDesc(): Iterable<Article>
}

interface UserRepository : CrudRepository<User, Long> {
	fun findByLogin(login: String): User?
}

interface TagsRepository: CrudRepository<Tags, Long> {
	fun findByTag(tag: String): Iterable<Tags>
	fun findByArticle(article: Article): Iterable<Tags>
	fun deleteByArticle(article: Article)
}