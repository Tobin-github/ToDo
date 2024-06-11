package top.tobin.shared.data.di

import android.content.Context
import androidx.room.Room
import top.tobin.shared.data.local.AppDataBase
import top.tobin.shared.data.local.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import top.tobin.shared.data.local.AccountingDao
import top.tobin.shared.data.local.ScheduleDao
import javax.inject.Singleton

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: 这里使用了 SingletonComponent，因此 NetworkModule 绑定到 Application 的生命周期.
 */
@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    private const val DB_NAME = "todo.db"

    /**
     * @Provides 常用于被 @Module 注解标记类的内部的方法，并提供依赖项对象.
     * @Singleton 提供单例.
     */
    @Provides
    @Singleton
    fun provideAppDataBase(
        @ApplicationContext context: Context
    ): AppDataBase = Room
        .databaseBuilder(context, AppDataBase::class.java, DB_NAME)
        .fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build()

    @Provides
    @Singleton
    fun providerUserDao(appDataBase: AppDataBase): UserDao {
        return appDataBase.userDao()
    }

    @Provides
    @Singleton
    fun providerScheduleDao(appDataBase: AppDataBase): ScheduleDao {
        return appDataBase.scheduleDao()
    }

    @Provides
    @Singleton
    fun providerAccountingDao(appDataBase: AppDataBase): AccountingDao {
        return appDataBase.accountingDao()
    }

}