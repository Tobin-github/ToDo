package top.tobin.my.user

import android.widget.Toast
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import top.tobin.common.eventbus.observeEvent
import top.tobin.common.utils.LogUtil
import top.tobin.common.base.Initializer
import top.tobin.my.event.TestEvent
import top.tobin.shared.data.remote.doFailure
import top.tobin.shared.data.remote.doSuccess
import top.tobin.shared.data.repository.IUserRepository
import top.tobin.shared.model.UserModel
import javax.inject.Inject

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: LoginViewModel.
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: IUserRepository
) : ViewModel() {

    private val _loading: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val loading = _loading.asSharedFlow()

    private val _historyLoginUser = MutableLiveData<List<UserModel>>()
    val historyLoginUser = _historyLoginUser


    init {
        viewModelScope.launch {
            getHistoryLoginUser().collectLatest {
                LogUtil.i("LoginViewModel", "getHistoryLoginUser：$it")
                _historyLoginUser.postValue(it)
            }
        }

        observeEvent<TestEvent>(viewModelScope) {

        }
    }

    fun postLogin(username: String, password: String) = liveData {
        userRepository.fetchLogin(username, password)
            .onStart {
                // 在调用 flow 请求数据之前，做一些准备工作，例如显示正在加载数据loading
                LogUtil.d("LoginViewModel", "onStart")
                _loading.emit(true)
            }
            .catch { ex ->
                // 捕获上游出现的异常
                ex.printStackTrace()
                LogUtil.d("LoginViewModel", "ex: $ex")
                Toast.makeText(Initializer.application, "${ex.message}", Toast.LENGTH_SHORT).show()
            }
            .onCompletion {
                // 请求完成
                LogUtil.d("LoginViewModel", "onCompletion")
                _loading.emit(false)
            }
            .collectLatest { result ->
                LogUtil.d("LoginViewModel", "collectLatest result: $result")
                result.doFailure { throwable ->
                    LogUtil.d("", "doFailure throwable: $throwable")
                    Toast.makeText(
                        Initializer.application, "${throwable.message}", Toast.LENGTH_SHORT
                    ).show()
                }

                result.doSuccess { value ->
                    emit(value)
                }
            }
    }


    fun postRegister(username: String, password: String) = liveData {
        val userModel = UserModel(username = username, password = password)
        userRepository.register(userModel).onStart {
            _loading.emit(true)
        }.catch { ex ->
            // 捕获上游出现的异常
            LogUtil.d("LoginViewModel", "ex: $ex")
            Toast.makeText(Initializer.application, "${ex.message}", Toast.LENGTH_SHORT).show()
        }.onCompletion {
            // 请求完成
            LogUtil.d("LoginViewModel", "onCompletion")
            _loading.emit(false)
        }.collectLatest { result ->
            LogUtil.d("LoginViewModel", "collectLatest result: $result")
            result.doFailure { throwable ->
                LogUtil.d("LoginViewModel", "doFailure throwable: $throwable")
                Toast.makeText(
                    Initializer.application, "${throwable.message}", Toast.LENGTH_SHORT
                ).show()
            }

            result.doSuccess { value ->
                emit(value)
            }
        }
    }

    fun deleteUser(userModel: UserModel) = viewModelScope.launch {
        userRepository.removeLocalUser(userModel)
    }

    private suspend fun getHistoryLoginUser() = userRepository.getHistoryLoginUser()
}