package ru.netology

import java.lang.RuntimeException

data class Post(
    var id: Int,
    val text: String = "text",
    val likes: Likes = Likes(),
    val comment: Comment = Comment()
)

data class Likes(
    var count: Int = 0
)

data class Comment(
    var id: Int = 0,
    val fromId: Int = 0,
    val date: Int = 0,
    val text: String = "text",
    val donut: Donut = Donut(),
    val replyToUser: Int = 0,
    val replyToComment: Int = 0,
    val thread: Thread = Thread(),
    val attachment: Array<Attachment> = arrayOf(
        PhotoAttachment(Photo(1, 0)),
        VideoAttachment(Video(0, 1)),
        AudioAttachment(Audio(2, 2))
    )
)

data class Donut(
    val isDon: Boolean = true,
    val placeholder: String = "placeholder"
)

interface Attachment {
    val type: String
}

data class Photo(
    val id: Int,
    val ownerId: Int
)

data class PhotoAttachment(val photo: Photo) : Attachment {
    override val type = "photo"
}

data class Video(
    val id: Int,
    val ownerId: Int
)

data class VideoAttachment(val video: Video) : Attachment {
    override val type = "video"
}

data class Audio(
    val id: Int,
    val ownerId: Int
)

data class AudioAttachment(val audio: Audio) : Attachment {
    override val type = "audio"
}

data class Thread(
    val count: Int = 0,
    val canPost: Boolean = true,
    val showReplyButton: Boolean = true,
    val groupsCanPost: Boolean = true
)

class PostNotFoundException(message: String): RuntimeException(message)

object WallService {
    private var posts = emptyArray<Post>()
    private var comments = emptyArray<Comment>()
    private var lastId = 0
    private var lastCommentId = 0

    fun add(post: Post): Post {
        posts += post.copy(id = ++lastId, likes = post.likes.copy())
        return posts.last()
    }

    fun createComment(postId: Int, comment: Comment): Comment {
        for (post in posts) {
            if (post.id == postId) {
                comments += comment.copy(id = ++lastCommentId)
                return comments.last()
            }
        }
        throw PostNotFoundException("No post with $postId")
    }

    fun print() {
        for (post in posts) {
            println(post)
        }
    }
}

fun main() {
    WallService.add(Post(1))
    println(WallService.createComment(1, Comment()))
    WallService.add(Post(2))
    println(WallService.createComment(2, Comment()))
    println(WallService.createComment(2, Comment()))
    WallService.add(Post(3))
    println(WallService.createComment(3, Comment()))
    WallService.print()
}