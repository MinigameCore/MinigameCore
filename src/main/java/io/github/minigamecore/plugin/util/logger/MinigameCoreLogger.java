/*
 * This file is part of MinigameCore, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2016 - 2016 MinigameCore <http://minigamecore.github.io>
 * Copyright (c) Contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.minigamecore.plugin.util.logger;

import static io.github.minigamecore.plugin.config.Configurations.get;
import static io.github.minigamecore.plugin.util.logger.MinigameCoreLoggerUtil.Level.DEBUG;
import static io.github.minigamecore.plugin.util.logger.MinigameCoreLoggerUtil.Level.ERROR;
import static io.github.minigamecore.plugin.util.logger.MinigameCoreLoggerUtil.Level.INFO;
import static io.github.minigamecore.plugin.util.logger.MinigameCoreLoggerUtil.Level.TRACE;
import static io.github.minigamecore.plugin.util.logger.MinigameCoreLoggerUtil.Level.WARN;
import static io.github.minigamecore.plugin.util.logger.MinigameCoreLoggerUtil.addToBuffer;
import static java.lang.String.format;
import static java.time.LocalTime.now;
import static org.slf4j.LoggerFactory.getLogger;

import com.google.common.base.Objects;
import com.google.inject.Singleton;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import org.slf4j.Logger;
import org.slf4j.Marker;

import javax.annotation.Nullable;

/**
 * The MinigameCore implementation of {@link Logger}.
 */
@Singleton
public class MinigameCoreLogger implements Logger {

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private final CommentedConfigurationNode globalConfig = get("global").get().get();
    private final Logger logger;

    @SuppressWarnings("ConstantConditions")
    public MinigameCoreLogger() {
        this((String)null);
    }

    public MinigameCoreLogger(@Nullable Class suffix) {
        this(suffix == null ? null : suffix.getCanonicalName());
    }

    public MinigameCoreLogger(@Nullable String suffix) {
        final String prefix = "minigamecore";
        logger = getLogger((suffix == null) ? prefix : prefix + "|" + suffix);
    }

    @Override
    public String getName() {
        return logger.getName();
    }

    @Override
    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return logger.isTraceEnabled();
    }

    @Override
    public void trace(String msg) {
        addToBuffer(now(), TRACE, this, msg);
        logger.trace(msg);
    }

    @Override
    public void trace(Marker marker, String msg) {
        addToBuffer(now(), TRACE, this, msg);
        logger.trace(marker, msg);
    }

    @Override
    public void trace(String format, Object arg) {
        addToBuffer(now(), TRACE, this, format(format, arg));
        logger.trace(format, arg);
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        addToBuffer(now(), TRACE, this, format(format, arg1, arg2));
        logger.trace(format, arg1, arg2);
    }

    @Override
    public void trace(String format, Object[] arguments) {
        addToBuffer(now(), TRACE, this, format(format, arguments));
        logger.trace(format, arguments);
    }

    @Override
    public void trace(String msg, Throwable throwable) {
        addToBuffer(now(), TRACE, this, msg, throwable);
        logger.trace(msg, throwable);
    }

    @Override
    public void trace(Marker marker, String format, Object arg) {
        addToBuffer(now(), TRACE, this, format(format, arg));
        logger.trace(marker, format, arg);
    }

    @Override
    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        addToBuffer(now(), TRACE, this, format(format, arg1, arg2));
        logger.trace(marker, format, arg1, arg2);
    }

    @Override
    public void trace(Marker marker, String format, Object[] arguments) {
        addToBuffer(now(), TRACE, this, format(format, arguments));
        logger.trace(marker, format, arguments);
    }

    @Override
    public void trace(Marker marker, String msg, Throwable throwable) {
        addToBuffer(now(), TRACE, this, msg, throwable);
        logger.trace(marker, msg, throwable);
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return logger.isDebugEnabled(marker);
    }

    @Override
    public void debug(String msg) {
        addToBuffer(now(), DEBUG, this, msg);
        logger.debug(msg);
    }

    @Override
    public void debug(String format, Object arg) {
        addToBuffer(now(), DEBUG, this, format(format, arg));
        logger.debug(format, arg);
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        addToBuffer(now(), DEBUG, this, format(format, arg1, arg2));
        logger.debug(format, arg1, arg2);
    }

    @Override
    public void debug(String format, Object[] arguments) {
        addToBuffer(now(), DEBUG, this, format(format, arguments));
        logger.debug(format, arguments);
    }

    @Override
    public void debug(String msg, Throwable throwable) {
        addToBuffer(now(), DEBUG, this, msg, throwable);
        logger.debug(msg, throwable);
    }

    @Override
    public void debug(Marker marker, String msg) {
        addToBuffer(now(), DEBUG, this, msg);
        logger.debug(marker, msg);
    }

    @Override
    public void debug(Marker marker, String format, Object arg) {
        addToBuffer(now(), DEBUG, this, format(format, arg));
        logger.debug(marker, format, arg);
    }

    @Override
    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        addToBuffer(now(), DEBUG, this, format(format, arg1, arg2));
        logger.debug(marker, format, arg1, arg2);
    }

    @Override
    public void debug(Marker marker, String format, Object[] arguments) {
        addToBuffer(now(), DEBUG, this, format(format, arguments));
        logger.debug(marker, format, arguments);
    }

    @Override
    public void debug(Marker marker, String msg, Throwable throwable) {
        addToBuffer(now(), DEBUG, this, msg, throwable);
        logger.debug(marker, msg, throwable);
    }

    @Override
    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return logger.isInfoEnabled(marker);
    }

    @Override
    public void info(String msg) {
        addToBuffer(now(), INFO, this, msg);
        logger.info(msg);
    }

    @Override
    public void info(String format, Object arg) {
        addToBuffer(now(), INFO, this, format(format, arg));
        logger.info(format, arg);
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        addToBuffer(now(), INFO, this, format(format, arg1, arg2));
        logger.info(format, arg1, arg2);
    }

    @Override
    public void info(String format, Object[] arguments) {
        addToBuffer(now(), INFO, this, format(format, arguments));
        logger.info(format, arguments);
    }

    @Override
    public void info(String msg, Throwable throwable) {
        addToBuffer(now(), INFO, this, msg, throwable);

        if (isDebug()) {
            logger.info(msg, throwable);
        } else {
            logger.info(msg);
        }
    }

    @Override
    public void info(Marker marker, String msg) {
        addToBuffer(now(), INFO, this, msg);
        logger.info(marker, msg);
    }

    @Override
    public void info(Marker marker, String format, Object arg) {
        addToBuffer(now(), INFO, this, format(format, arg));
        logger.info(marker, format, arg);
    }

    @Override
    public void info(Marker marker, String format, Object arg1, Object arg2) {
        addToBuffer(now(), INFO, this, format(format, arg1, arg2));
        logger.info(marker, format, arg1, arg2);
    }

    @Override
    public void info(Marker marker, String format, Object[] arguments) {
        addToBuffer(now(), INFO, this, format(format, arguments));
        logger.info(marker, format, arguments);
    }

    @Override
    public void info(Marker marker, String msg, Throwable throwable) {
        addToBuffer(now(), INFO, this, msg, throwable);

        if (isDebug()) {
            logger.info(marker, msg, throwable);
        } else {
            logger.info(marker, msg);
        }
    }

    @Override
    public boolean isWarnEnabled() {
        return logger.isWarnEnabled();
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return logger.isWarnEnabled(marker);
    }

    @Override
    public void warn(String msg) {
        addToBuffer(now(), WARN, this, msg);
        logger.warn(msg);
    }

    @Override
    public void warn(String format, Object arg) {
        addToBuffer(now(), WARN, this, format(format, arg));
        logger.warn(format, arg);
    }

    @Override
    public void warn(String format, Object[] arguments) {
        addToBuffer(now(), WARN, this, format(format, arguments));
        logger.warn(format, arguments);
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        addToBuffer(now(), WARN, this, format(format, arg1, arg2));
        logger.warn(format, arg1, arg2);
    }

    @Override
    public void warn(String msg, Throwable throwable) {
        addToBuffer(now(), WARN, this, msg, throwable);

        if (isDebug()) {
            logger.warn(msg, throwable);
        } else {
            logger.warn(msg);
        }
    }

    @Override
    public void warn(Marker marker, String msg) {
        addToBuffer(now(), WARN, this, msg);
        logger.warn(marker, msg);
    }

    @Override
    public void warn(Marker marker, String format, Object arg) {
        addToBuffer(now(), WARN, this, format(format, arg));
        logger.warn(marker, format, arg);
    }

    @Override
    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        addToBuffer(now(), WARN, this, format(format, arg1, arg2));
        logger.warn(marker, format, arg1, arg2);
    }

    @Override
    public void warn(Marker marker, String format, Object[] arguments) {
        addToBuffer(now(), WARN, this, format(format, arguments));
        logger.warn(marker, format, arguments);
    }

    @Override
    public void warn(Marker marker, String msg, Throwable throwable) {
        addToBuffer(now(), WARN, this, msg, throwable);

        if (isDebug()) {
            logger.warn(marker, msg, throwable);
        } else {
            logger.warn(msg, throwable);
        }
    }

    @Override
    public boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return logger.isErrorEnabled(marker);
    }

    @Override
    public void error(String msg) {
        addToBuffer(now(), ERROR, this, msg);
        logger.error(msg);
    }

    @Override
    public void error(String format, Object arg) {
        addToBuffer(now(), ERROR, this, format(format, arg));
        logger.error(format, arg);
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        addToBuffer(now(), ERROR, this, format(format, arg1, arg2));
        logger.error(format, arg1, arg2);
    }

    @Override
    public void error(String format, Object[] arguments) {
        addToBuffer(now(), ERROR, this, format(format, arguments));
        logger.error(format, arguments);
    }

    @Override
    public void error(String msg, Throwable throwable) {
        addToBuffer(now(), ERROR, this, msg, throwable);

        if (isDebug()) {
            logger.error(msg, throwable);
        } else {
            logger.error(msg);
        }
    }

    @Override
    public void error(Marker marker, String msg) {
        addToBuffer(now(), ERROR, this, msg);
        logger.error(marker, msg);
    }

    @Override
    public void error(Marker marker, String format, Object arg) {
        addToBuffer(now(), ERROR, this, format(format, arg));
        logger.error(marker, format, arg);
    }

    @Override
    public void error(Marker marker, String format, Object arg1, Object arg2) {
        addToBuffer(now(), ERROR, this, format(format, arg1, arg2));
        logger.error(marker, format, arg1, arg2);
    }

    @Override
    public void error(Marker marker, String format, Object[] arguments) {
        addToBuffer(now(), ERROR, this, format(format, arguments));
        logger.error(marker, format, arguments);
    }

    @Override
    public void error(Marker marker, String msg, Throwable throwable) {
        addToBuffer(now(), ERROR, this, msg, throwable);

        if (isDebug()) {
            logger.error(marker, msg, throwable);
        } else {
            logger.error(msg);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(logger);
    }

    @Override
    public boolean equals(Object o) {
        return !((o == null) || !(o instanceof MinigameCoreLogger)) && Objects.equal(logger, ((MinigameCoreLogger) o).logger);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("name", getName())
                .add("debug", isDebugEnabled())
                .add("error", isErrorEnabled())
                .add("info", isInfoEnabled())
                .add("trace", isTraceEnabled())
                .add("warn", isWarnEnabled())
                .toString();
    }

    private boolean isDebug() {
        return globalConfig.getNode("logging", "debug").getBoolean();
    }

}
