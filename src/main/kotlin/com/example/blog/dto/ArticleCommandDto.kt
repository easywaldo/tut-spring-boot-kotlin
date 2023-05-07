package com.example.blog.dto

data class ArticleCommandDto(val title: String,
                             val headLine: String,
                             val content: String,
                             val authorId: Long,
                             val tagList: List<TagCommandDto>
) {
    data class TagCommandDto(val name: String)
}

data class UpdateArticleCommandDto(val headLine: String,
                                   val content: String,
                                   val tagList: List<ArticleCommandDto.TagCommandDto>
)