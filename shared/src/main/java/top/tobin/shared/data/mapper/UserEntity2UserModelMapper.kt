package top.tobin.shared.data.mapper

import top.tobin.shared.data.entity.UserEntity
import top.tobin.shared.model.UserModel

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: UserEntity2UserModelMapper.
 */
class UserEntity2UserModelMapper : Mapper<UserEntity, UserModel> {
    override fun map(input: UserEntity): UserModel {
        return convert2UserEntity(input)
    }

    private fun convert2UserEntity(userEntity: UserEntity): UserModel {
        return userEntity.run {
            UserModel(
                id = this.id,
                username = this.username,
                password = this.password
            )
        }
    }
}