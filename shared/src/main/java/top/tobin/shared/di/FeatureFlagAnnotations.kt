package top.tobin.shared.di

import javax.inject.Qualifier

@Qualifier
@Target(
    AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER
)
@Retention(AnnotationRetention.BINARY)
annotation class ExploreArEnabledFlag

// Whether or not the Map feature is enabled
@Qualifier
@Target(
    AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER
)
@Retention(AnnotationRetention.BINARY)
annotation class MapFeatureEnabledFlag

// Whether or not the Codelabs feature is enabled
@Qualifier
@Target(
    AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER
)
@Retention(AnnotationRetention.BINARY)
annotation class CodelabsEnabledFlag

// Whether or not the Search Schedule feature is enabled
@Qualifier
@Target(
    AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER
)
@Retention(AnnotationRetention.BINARY)
annotation class SearchScheduleEnabledFlag

// Whether or not the Search feature uses Room
@Qualifier
@Target(
    AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER
)
@Retention(AnnotationRetention.BINARY)
annotation class SearchUsingRoomEnabledFlag

// Whether or not the I/O app for Google Assistant callout is enabled
@Qualifier
@Target(
    AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER
)
@Retention(AnnotationRetention.BINARY)
annotation class AssistantAppEnabledFlag

// Whether or not the reservation feature is enabled
@Qualifier
@Target(
    AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER
)
@Retention(AnnotationRetention.BINARY)
annotation class ReservationEnabledFlag

// Whether or not the Feed feature is enabled
@Qualifier
@Target(
    AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER
)
@Retention(AnnotationRetention.BINARY)
annotation class FeedEnabledFlag
