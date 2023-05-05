package com.example.blog.dto

data class ArticleCommandDto(val title: String,
                             val headLine: String,
                             val content: String,
                             val authorId: Long,
)