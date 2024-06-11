package top.tobin.shared.data.repository

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import top.tobin.shared.data.entity.AccountingEntity
import top.tobin.shared.data.remote.DataResult
import top.tobin.shared.data.local.AppDataBase
import top.tobin.shared.data.remote.AccountingInterface
import top.tobin.shared.model.AccountingModel
import java.util.*

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: AccountingRepository.
 */
class AccountingRepository(
    private val api: AccountingInterface, private val db: AppDataBase
) : IAccountingRepository {

    override suspend fun fetchAccountList(): Flow<DataResult<List<AccountingModel>>> {
//        return db.accountingDao().getAccountingList()
        return flow {
            delay(10000)
            emitAll(
                db.accountingDao().getAccountingList().map {
                    val accountingModels = mutableListOf<AccountingModel>()
                    for (accountingEntity in it) {
                        accountingModels.add(
                            AccountingModel(
                                id = accountingEntity.id,
                                title = accountingEntity.title
                            )
                        )
                    }
                    DataResult.Success(accountingModels)
                }
            )
        }
    }

    override suspend fun addAccount(accountingEntity: AccountingEntity): Flow<DataResult<Boolean>> {
        return flow {

        }
    }

    override suspend fun updateSchedule(accountingEntity: AccountingEntity): Flow<DataResult<Boolean>> {
        return flow {

        }
    }
}