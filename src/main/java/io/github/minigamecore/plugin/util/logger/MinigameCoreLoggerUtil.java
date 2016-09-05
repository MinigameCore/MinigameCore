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

import static com.google.common.base.Throwables.getStackTraceAsString;
import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.spongepowered.api.Sponge.getScheduler;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import io.github.minigamecore.plugin.MinigameCore;
import io.github.minigamecore.plugin.config.Configurations;
import org.slf4j.Logger;
import org.spongepowered.api.scheduler.Task;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.List;
import java.util.zip.GZIPOutputStream;

/**
 * The utility class for {@link MinigameCoreLogger}.
 */
public final class MinigameCoreLoggerUtil {

    private static final List<String> logBuffer = Lists.newArrayList();
    private static File logFile;

    static void addToBuffer(LocalTime time, Level level, Logger logger, String message) {
        String val = "[" + time.toString() + "] [" + level + "] [" + logger.getName() + "]: " + message + "\n";
        logBuffer.add(val);
    }

    static void addToBuffer(LocalTime time, Level level, Logger logger, String message, Throwable throwable) {
        addToBuffer(time, level, logger, message);
        addToBuffer(time, level, logger, getStackTraceAsString(throwable));
    }

    public static void compress(Logger logger) {

        try (BufferedOutputStream outputStream = new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(logFile.toString() + ".gz")));
                BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(logFile))) {
            byte[] buffer = new byte[1024];
            int length;

            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }

            Files.deleteIfExists(logFile.toPath());
        } catch (IOException e) {
            logger.error(format("Error compressing %s to %s. Deleting %s", logFile, logFile.toString() + ".gz", logFile.toString() + ".gz"), e);

            try {
                Files.deleteIfExists(Paths.get(logFile.toString() + ".gz"));
            } catch (IOException e1) {
                logger.error(format("Error deleting %s", logFile.toString() + ".gz"), e);
            }
        }
    }

    public static void createLogFile(MinigameCore plugin, String date, Logger logger) {
        Path dir = Paths.get("logs", plugin.getPluginContainer().getId());

        if (Files.notExists(dir)) {

            try {
                Files.createDirectories(dir);
            } catch (IOException e) {
                e.printStackTrace(); // TODO log
            }
        }

        //noinspection ConstantConditions
        for (int i = 1; i <= Short.MAX_VALUE; i++) {
            Path file = dir.resolve(date + "-" + i + ".log");

            if (Files.notExists(file) && Files.notExists(Paths.get(file.toString() + ".gz"))) {
                try {
                    Files.createFile(file);
                    schedule(plugin);
                } catch (IOException e) {
                    e.printStackTrace(); // TODO log
                }

                logFile = file.toFile();
                schedule(plugin);
                break;
            }
        }
    }

    public static void flush(Logger logger) {
        List<String> logBuffer = ImmutableList.copyOf(MinigameCoreLoggerUtil.logBuffer);
        MinigameCoreLoggerUtil.logBuffer.clear();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
            logBuffer.forEach(log -> {

                try {
                    writer.write(log);
                } catch (IOException e) {
                    logger.warn("Could not save log message.", e);
                }
            });
        } catch (IOException e) {
            logger.warn("Could not save log message.", e);
        }
    }

    public static void schedule(MinigameCore plugin) {
        //noinspection OptionalGetWithoutIsPresent
        final long interval = Configurations.get("global").get().get().getNode("logging", "flush").getLong(15L);
        getScheduler().createTaskBuilder().async().name(plugin.getPluginContainer().getId() + "-A-640").delay(interval, SECONDS)
                .interval(interval, SECONDS).execute(() -> flush(plugin.getLogger())).submit(plugin);
    }

    public static void cancelTask(MinigameCore plugin) {
        getScheduler().getTasksByName(plugin.getPluginContainer().getId() + "-A-640").forEach(Task::cancel);
    }

    private MinigameCoreLoggerUtil() {
    }

    /**
     * The {@link Logger} levels for saving to log file.
     */
    public enum Level {
        DEBUG,
        ERROR,
        INFO,
        TRACE,
        WARN
    }

}
