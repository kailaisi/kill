package com.example.kill

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * 增强logger功能
 */
inline fun <reified T> logger(from: T): Logger {
    return LoggerFactory.getLogger(T::class.java)
}