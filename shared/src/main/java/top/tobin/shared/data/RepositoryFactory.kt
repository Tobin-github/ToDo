package top.tobin.shared.data

import top.tobin.shared.data.local.AppDataBase
import top.tobin.shared.data.mapper.UserEntity2UserModelMapper
import top.tobin.shared.data.mapper.UserModel2UserEntityMapper
import top.tobin.shared.data.remote.AccountingInterface
import top.tobin.shared.data.remote.BingInterface
import top.tobin.shared.data.remote.ScheduleInterface

import top.tobin.shared.data.remote.UserInterface
import top.tobin.shared.data.repository.AccountingRepository
import top.tobin.shared.data.repository.GalleryRepository
import top.tobin.shared.data.repository.IAccountingRepository
import top.tobin.shared.data.repository.IGalleryRepository
import top.tobin.shared.data.repository.IScheduleRepository
import top.tobin.shared.data.repository.ScheduleRepository
import top.tobin.shared.data.repository.UserRepository
import top.tobin.shared.data.repository.IUserRepository

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: RepositoryFactory.
 */
object RepositoryFactory {
    fun makeUserRepository(api: UserInterface, db: AppDataBase): IUserRepository =
        UserRepository(
            api,
            db,
            UserEntity2UserModelMapper(),
            UserModel2UserEntityMapper()
        )

    fun makeScheduleRepository(api: ScheduleInterface, db: AppDataBase): IScheduleRepository =
        ScheduleRepository(
            api,
            db,
        )

    fun makeGalleryRepository(api: BingInterface, db: AppDataBase): IGalleryRepository =
        GalleryRepository(
            api,
            db,
        )

    fun makeAccountingRepository(api: AccountingInterface, db: AppDataBase): IAccountingRepository =
        AccountingRepository(
            api,
            db,
        )

}