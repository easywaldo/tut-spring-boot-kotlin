package com.example.blog.dto

data class ArticleQueryDto (val title: String, val content: String, val author: UserQueryDto, val tags: List<TagQueryDto>) {
    data class UserQueryDto(val login: String, val firstname: String, val lastname: String)
    data class TagQueryDto(val tag: String)
}