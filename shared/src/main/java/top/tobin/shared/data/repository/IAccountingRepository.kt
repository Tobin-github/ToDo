package top.tobin.shared.data.repository

import kotlinx.coroutines.flow.Flow
import top.tobin.shared.data.entity.AccountingEntity
import top.tobin.shared.data.remote.DataResult
import top.tobin.shared.model.AccountingModel
import java.util.*

interface IAccountingRepository {
    suspend fun fetchAccountList(): Flow<DataResult<List<AccountingModel>>>
    suspend fun addAccount(accountingEntity: AccountingEntity): Flow<DataResult<Boolean>>
    suspend fun updateSchedule(accountingEntity: AccountingEntity): Flow<DataResult<Boolean>>
}