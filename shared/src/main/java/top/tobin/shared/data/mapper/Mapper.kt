package top.tobin.shared.data.mapper

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: Mapper.
 */
interface Mapper<I, O> {
    fun map(input: I): O
}