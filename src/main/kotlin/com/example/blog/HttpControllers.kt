package com.example.blog

import com.example.blog.dto.ArticleQueryDto
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/article")
class ArticleController(
	private val repository: ArticleRepository,
	private val tagsRepository: TagsRepository,
) {

	@GetMapping("/")
	fun findAll() = repository.findAllByOrderByAddedAtDesc()

	@GetMapping("/{slug}")
	fun findOne(@PathVariable slug: String): ArticleQueryDto {
		val article = repository.findBySlug(slug.lowercase()) ?: throw ResponseStatusException(
			NOT_FOUND,
			"This article does not exist"
		)

		val tags = tagsRepository.findByArticle(article)

		val responseDto = article.responseDto(article.author, tags.toList())
		return responseDto
	}

}

fun Article.responseDto(user: User, tags: List<Tags>) = ArticleQueryDto(
	title = title,
	content = content,
	author = ArticleQueryDto.UserQueryDto(login = user.login, firstname = user.firstname, lastname = user.lastname),
	tags = tags.map {
		ArticleQueryDto.TagQueryDto(it.tag)
	}.toList(),
)

@RestController
@RequestMapping("/api/user")
class UserController(private val repository: UserRepository) {

	@GetMapping("/")
	fun findAll() = repository.findAll()

	@GetMapping("/{login}")
	fun findOne(@PathVariable login: String) = repository.findByLogin(login) ?: throw ResponseStatusException(NOT_FOUND, "This user does not exist")
}
