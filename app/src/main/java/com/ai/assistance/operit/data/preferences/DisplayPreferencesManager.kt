package com.ai.assistance.operit.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

// DataStore 实例
private val Context.displayPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "display_preferences"
)

/**
 * DisplayPreferencesManager
 * 管理消息显示相关的偏好设置
 * 这些设置独立于角色卡和主题系统
 * 使用单例模式，避免重复创建实例
 */
class DisplayPreferencesManager private constructor(private val context: Context) {

    companion object {
        @Volatile
        private var INSTANCE: DisplayPreferencesManager? = null

        fun getInstance(context: Context): DisplayPreferencesManager {
            return INSTANCE ?: synchronized(this) {
                val instance = DisplayPreferencesManager(context.applicationContext)
                INSTANCE = instance
                instance
            }
        }

        // 消息显示设置的 Key
        private val KEY_SHOW_MODEL_PROVIDER = booleanPreferencesKey("show_model_provider")
        private val KEY_SHOW_MODEL_NAME = booleanPreferencesKey("show_model_name")
        private val KEY_SHOW_ROLE_NAME = booleanPreferencesKey("show_role_name")
        private val KEY_SHOW_USER_NAME = booleanPreferencesKey("show_user_name")
        private val KEY_SHOW_MESSAGE_TOKEN_STATS = booleanPreferencesKey("show_message_token_stats")
        private val KEY_SHOW_MESSAGE_TIMING_STATS = booleanPreferencesKey("show_message_timing_stats")
        private val KEY_SHOW_MESSAGE_TIMESTAMP = booleanPreferencesKey("show_message_timestamp")
        
        // 全局用户设置的 Key
        private val KEY_GLOBAL_USER_AVATAR_URI = stringPreferencesKey("global_user_avatar_uri")
        private val KEY_GLOBAL_USER_NAME = stringPreferencesKey("global_user_name")
        
        // 显示相关设置的 Key
        private val KEY_SHOW_FPS_COUNTER = booleanPreferencesKey("show_fps_counter")
        private val KEY_ENABLE_REPLY_NOTIFICATION = booleanPreferencesKey("enable_reply_notification")
        private val KEY_ENABLE_REPLY_NOTIFICATION_SOUND =
            booleanPreferencesKey("enable_reply_notification_sound")
        private val KEY_ENABLE_REPLY_NOTIFICATION_VIBRATION =
            booleanPreferencesKey("enable_reply_notification_vibration")
        private val KEY_ENABLE_ENTER_TO_SEND = booleanPreferencesKey("enable_enter_to_send")
        private val KEY_ENABLE_NAVIGATION_ANIMATION =
            booleanPreferencesKey("enable_navigation_animation")

        // 自动化显示与行为相关设置的 Key
        private val KEY_ENABLE_BACKGROUND_KEEP_ALIVE =
            booleanPreferencesKey("enable_background_keep_alive")
        private val KEY_ENABLE_EXPERIMENTAL_VIRTUAL_DISPLAY =
            booleanPreferencesKey("enable_experimental_virtual_display")
        private val KEY_HIDE_RUNTIME_TASK_VIEW =
            booleanPreferencesKey("hide_runtime_task_view")

        private val KEY_SCREENSHOT_FORMAT = stringPreferencesKey("screenshot_format")
        private val KEY_SCREENSHOT_QUALITY = intPreferencesKey("screenshot_quality")
        private val KEY_SCREENSHOT_SCALE_PERCENT = intPreferencesKey("screenshot_scale_percent")

        // 虚拟屏幕相关设置的 Key
        private val KEY_VIRTUAL_DISPLAY_BITRATE_KBPS = intPreferencesKey("virtual_display_bitrate_kbps")

        // 工具折叠设置（多个只读工具 / 多个任意工具 / 全部工具）
        private val KEY_TOOL_COLLAPSE_MODE = stringPreferencesKey("tool_collapse_mode")
    }

    /**
     * 是否显示模型供应商
     * 默认值：false
     */
    val showModelProvider: Flow<Boolean> =
        context.displayPreferencesDataStore.data.map { preferences ->
            preferences[KEY_SHOW_MODEL_PROVIDER] ?: false
        }

    /**
     * 是否显示模型名称
     * 默认值：false
     */
    val showModelName: Flow<Boolean> =
        context.displayPreferencesDataStore.data.map { preferences ->
            preferences[KEY_SHOW_MODEL_NAME] ?: false
        }

    /**
     * 是否显示角色卡名称
     * 默认值：false
     */
    val showRoleName: Flow<Boolean> =
        context.displayPreferencesDataStore.data.map { preferences ->
            preferences[KEY_SHOW_ROLE_NAME] ?: false
        }

    /**
     * 是否显示用户名字
     * 默认值：false
     */
    val showUserName: Flow<Boolean> =
        context.displayPreferencesDataStore.data.map { preferences ->
            preferences[KEY_SHOW_USER_NAME] ?: false
        }

    /**
     * 是否显示消息 Token 统计
     * 默认值：false
     */
    val showMessageTokenStats: Flow<Boolean> =
        context.displayPreferencesDataStore.data.map { preferences ->
            preferences[KEY_SHOW_MESSAGE_TOKEN_STATS] ?: false
        }

    /**
     * 是否显示消息耗时统计
     * 默认值：false
     */
    val showMessageTimingStats: Flow<Boolean> =
        context.displayPreferencesDataStore.data.map { preferences ->
            preferences[KEY_SHOW_MESSAGE_TIMING_STATS] ?: false
        }

    /**
     * 是否显示消息时间
     * 默认值：false
     */
    val showMessageTimestamp: Flow<Boolean> =
        context.displayPreferencesDataStore.data.map { preferences ->
            preferences[KEY_SHOW_MESSAGE_TIMESTAMP] ?: false
        }

    /**
     * 全局用户头像URI
     */
    val globalUserAvatarUri: Flow<String?> =
        context.displayPreferencesDataStore.data.map { preferences ->
            preferences[KEY_GLOBAL_USER_AVATAR_URI]
        }

    /**
     * 全局用户名称
     */
    val globalUserName: Flow<String?> =
        context.displayPreferencesDataStore.data.map { preferences ->
            preferences[KEY_GLOBAL_USER_NAME]
        }

    /**
     * 是否显示FPS计数器
     * 默认值：false
     */
    val showFpsCounter: Flow<Boolean> =
        context.displayPreferencesDataStore.data.map { preferences ->
            preferences[KEY_SHOW_FPS_COUNTER] ?: false
        }

    /**
     * 是否启用回复通知
     * 默认值：true
     */
    val enableReplyNotification: Flow<Boolean> =
        context.displayPreferencesDataStore.data.map { preferences ->
            preferences[KEY_ENABLE_REPLY_NOTIFICATION] ?: true
        }

    /**
     * 是否启用回复通知提示音
     * 默认值：false
     */
    val enableReplyNotificationSound: Flow<Boolean> =
        context.displayPreferencesDataStore.data.map { preferences ->
            preferences[KEY_ENABLE_REPLY_NOTIFICATION_SOUND] ?: false
        }

    /**
     * 是否启用回复通知震动
     * 默认值：false
     */
    val enableReplyNotificationVibration: Flow<Boolean> =
        context.displayPreferencesDataStore.data.map { preferences ->
            preferences[KEY_ENABLE_REPLY_NOTIFICATION_VIBRATION] ?: false
        }

    /**
     * 是否启用回车发送
     * 默认值：false
     */
    val enableEnterToSend: Flow<Boolean> =
        context.displayPreferencesDataStore.data.map { preferences ->
            preferences[KEY_ENABLE_ENTER_TO_SEND] ?: false
        }

    /**
     * 是否启用新版导航动画
     * 默认值：true
     */
    val enableNavigationAnimation: Flow<Boolean> =
        context.displayPreferencesDataStore.data.map { preferences ->
            preferences[KEY_ENABLE_NAVIGATION_ANIMATION] ?: true
        }

    val enableBackgroundKeepAlive: Flow<Boolean> =
        context.displayPreferencesDataStore.data.map { preferences ->
            preferences[KEY_ENABLE_BACKGROUND_KEEP_ALIVE] ?: false
        }

    val enableExperimentalVirtualDisplay: Flow<Boolean> =
        context.displayPreferencesDataStore.data.map { preferences ->
            preferences[KEY_ENABLE_EXPERIMENTAL_VIRTUAL_DISPLAY] ?: true
        }

    val hideRuntimeTaskView: Flow<Boolean> =
        context.displayPreferencesDataStore.data.map { preferences ->
            preferences[KEY_HIDE_RUNTIME_TASK_VIEW] ?: false
        }

    val screenshotFormat: Flow<String> =
        context.displayPreferencesDataStore.data.map { preferences ->
            preferences[KEY_SCREENSHOT_FORMAT] ?: "JPG"
        }

    val screenshotQuality: Flow<Int> =
        context.displayPreferencesDataStore.data.map { preferences ->
            preferences[KEY_SCREENSHOT_QUALITY] ?: 75
        }

    val screenshotScalePercent: Flow<Int> =
        context.displayPreferencesDataStore.data.map { preferences ->
            preferences[KEY_SCREENSHOT_SCALE_PERCENT] ?: 75
        }

