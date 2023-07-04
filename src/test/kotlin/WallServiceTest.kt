import org.junit.Assert.*
import org.junit.Test
import ru.netology.Comment
import ru.netology.Post
import ru.netology.PostNotFoundException
import ru.netology.WallService

class WallServiceTest{

    @Test
    fun createCommentTest() {
        WallService.add(Post(1))
        val(id) = WallService.createComment(1, Comment())

        val result = id

        assertEquals(1, result)
    }

    @Test(expected = PostNotFoundException::class)
    fun shouldThrow() {
        WallService.add(Post(1))
        WallService.add(Post(2))
        WallService.createComment(4, Comment())
    }
}