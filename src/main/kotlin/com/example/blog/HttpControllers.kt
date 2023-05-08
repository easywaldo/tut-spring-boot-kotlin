package com.example.blog

import com.example.blog.dto.ArticleCommandDto
import com.example.blog.dto.ArticleQueryDto
import com.example.blog.dto.UpdateArticleCommandDto
import org.springframework.http.HttpStatus.*
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/article")
class ArticleController(
	private val repository: ArticleRepository,
	private val tagsRepository: TagsRepository,
	private val userRepository: UserRepository,
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

	@PostMapping("/")
	fun create(@RequestBody command: ArticleCommandDto) {

		repository.save(Article(
			title = command.title,
			headline = command.headLine,
			content = command.content,
			author = userRepository.findById(command.authorId).get(),
		)).also {
			command.tagList.forEach {t ->
				tagsRepository.save(Tags(
					tag = t.name,
					article = it,
				))
			}
		}
	}

	@PutMapping("/{id}")
	@Transactional
	fun update(@PathVariable id: Long,
			   @RequestBody command: UpdateArticleCommandDto) {

		repository.findById(id).also {
			it.get().headline = command.headLine
			it.get().content = command.content
			tagsRepository.deleteByArticle(it.get())
			command.tagList.forEach{
				t -> tagsRepository.save(
					Tags(tag = t.name, article = it.get(),
				))
			}
		}
	}

	@DeleteMapping("/{id}")
	@Transactional
	fun delete(@PathVariable id: Long) {
		tagsRepository.deleteByArticle(repository.findById(id).get())
		repository.deleteById(id)
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
