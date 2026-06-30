package top.tobin.shared.data.di

import android.content.Context
import top.tobin.shared.data.RepositoryFactory
import top.tobin.shared.data.local.AppDataBase
import top.tobin.shared.data.remote.UserInterface
import top.tobin.shared.data.repository.IUserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import top.tobin.shared.data.remote.AccountingInterface
import top.tobin.shared.data.remote.ScheduleInterface
import top.tobin.shared.data.repository.IAccountingRepository
import top.tobin.shared.data.repository.IGalleryRepository
import top.tobin.shared.data.repository.IScheduleRepository
import javax.inject.Singleton

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: RepositoryModule.
 */
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideUserRepository(
        api: UserInterface,
        db: AppDataBase
    ): IUserRepository {
        return RepositoryFactory.makeUserRepository(api, db)
    }

    @Singleton
    @Provides
    fun provideScheduleRepository(
        api: ScheduleInterface,
        db: AppDataBase
    ): IScheduleRepository {
        return RepositoryFactory.makeScheduleRepository(api, db)
    }

    @Singleton
    @Provides
    fun provideGalleryRepository(
        @ApplicationContext context: Context
    ): IGalleryRepository {
        return RepositoryFactory.makeGalleryRepository(context)
    }

    @Singleton
    @Provides
    fun provideAccountingRepository(
        api: AccountingInterface,
        db: AppDataBase
    ): IAccountingRepository {
        return RepositoryFactory.makeAccountingRepository(api, db)
    }

}