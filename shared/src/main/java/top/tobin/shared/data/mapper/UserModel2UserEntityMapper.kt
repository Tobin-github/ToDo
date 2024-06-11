package top.tobin.shared.data.mapper

import top.tobin.shared.data.entity.UserEntity
import top.tobin.shared.model.UserModel

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: UserModel2UserEntityMapper.
 */
class UserModel2UserEntityMapper : Mapper<UserModel, UserEntity> {
    override fun map(input: UserModel): UserEntity {
        return convert2UserEntity(input)
    }

    private fun convert2UserEntity(userModel: UserModel): UserEntity {
        return userModel.run {
            UserEntity(
                id = this.id,
                username = this.username,
                password = this.password
            )
        }
    }
}