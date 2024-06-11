package top.tobin.common.preferences

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import top.tobin.common.base.Initializer
import top.tobin.common.utils.LogUtil
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

open class DataStoreOwner(name: String) : IDataStoreOwner {
    private val Context.dataStore by preferencesDataStore(name)
    override val dataStore get() = context.dataStore
}

interface IDataStoreOwner {
    val context: Context get() = application

    val dataStore: DataStore<Preferences>

    fun intPreference(default: Int? = null): ReadOnlyProperty<IDataStoreOwner, DataStorePreference<Int>> =
        PreferenceProperty(::intPreferencesKey, default)

    fun doublePreference(default: Double? = null): ReadOnlyProperty<IDataStoreOwner, DataStorePreference<Double>> =
        PreferenceProperty(::doublePreferencesKey, default)

    fun longPreference(default: Long? = null): ReadOnlyProperty<IDataStoreOwner, DataStorePreference<Long>> =
        PreferenceProperty(::longPreferencesKey, default)

    fun floatPreference(default: Float? = null): ReadOnlyProperty<IDataStoreOwner, DataStorePreference<Float>> =
        PreferenceProperty(::floatPreferencesKey, default)

    fun booleanPreference(default: Boolean? = null): ReadOnlyProperty<IDataStoreOwner, DataStorePreference<Boolean>> =
        PreferenceProperty(::booleanPreferencesKey, default)

    fun stringPreference(default: String? = null): ReadOnlyProperty<IDataStoreOwner, DataStorePreference<String>> =
        PreferenceProperty(::stringPreferencesKey, default)

    fun stringSetPreference(default: Set<String>? = null): ReadOnlyProperty<IDataStoreOwner, DataStorePreference<Set<String>>> =
        PreferenceProperty(::stringSetPreferencesKey, default)

    class PreferenceProperty<V>(
        private val key: (String) -> Preferences.Key<V>,
        private val default: V? = null
    ) :
        ReadOnlyProperty<IDataStoreOwner, DataStorePreference<V>> {

        private var cache: DataStorePreference<V>? = null
        override fun getValue(
            thisRef: IDataStoreOwner,
            property: KProperty<*>
        ): DataStorePreference<V> =
            cache ?: DataStorePreference(
                thisRef.dataStore,
                key(property.name),
                default
            ).also { cache = it }
    }

    companion object {
        internal var application: Application = Initializer.application
    }
}

operator fun <V> Preferences.get(preference: DataStorePreference<V>) = this[preference.key]

open class DataStorePreference<V>(
    private val dataStore: DataStore<Preferences>,
    val key: Preferences.Key<V>,
    open val default: V?
) {

    suspend fun set(block: suspend V?.(Preferences) -> V?): Preferences =
        dataStore.edit { preferences ->
            val value = block(preferences[key] ?: default, preferences)
            LogUtil.d("dataStore.edit key: $key value: $value")
            if (value == null) {
                preferences.remove(key)
            } else {
                preferences[key] = value
            }
        }

    suspend fun set(value: V?): Preferences = set { value }

    fun asFlow(): Flow<V?> = dataStore.data.map { it[key] ?: default }

    fun asLiveData(): LiveData<V?> = asFlow().asLiveData()

    suspend fun get(): V? = asFlow().first()

    suspend fun getOrDefault(): V = get() ?: throw IllegalStateException("No default value")
}