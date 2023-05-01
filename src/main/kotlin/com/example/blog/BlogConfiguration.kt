package com.example.blog

import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BlogConfiguration {

	@Bean
	fun databaseInitializer(userRepository: UserRepository,
							articleRepository: ArticleRepository,
							tagsRepository: TagsRepository,
	) = ApplicationRunner {

		val johnDoe = userRepository.save(User("johnDoe", "John", "Doe"))
		val article_one = articleRepository.save(Article(
				title = "Kotlin",
				headline = "Lorem",
				content = "kotlin is latest language.",
				author = johnDoe
		))
		val article_two = articleRepository.save(Article(
				title = "Spring",
				headline = "Ipsum",
				content = "sample",
				author = johnDoe
		))

		tagsRepository.save(
			Tags(
				tag = "language",
				article = article_one,
			)
		)
		tagsRepository.save(
			Tags(
				tag = "kotlin",
				article = article_one,
			)
		)
	}
}
