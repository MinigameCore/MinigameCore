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

package io.github.minigamecore.plugin.util;

import static java.lang.String.format;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import javax.annotation.Nullable;

/*
 * Reflection helper class.
 */
public final class Reflector {

    private static final Logger LOGGER = LoggerFactory.getLogger("minigamecore|reflect");
    private static Field MODIFIER_FIELD;

    static {
        try {
            MODIFIER_FIELD = Field.class.getDeclaredField("modifiers");
            setAssessible(MODIFIER_FIELD, true);
        } catch (NoSuchFieldException e) {
            LOGGER.error(format("Unable to remove final from field %s. Field is not accessible.", "modifiers"), e);
        }
    }

    private Reflector() {
    }

    public static void setAssessible(Field field, boolean assessible) {
        field.setAccessible(assessible);
    }

    public static void toggleFinal(Field field) {
        try {
            MODIFIER_FIELD.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        } catch (IllegalAccessException e) {
            LOGGER.error(format("Unable to remove final from field %s. Field is not accessible.", field.getName()), e);
        }
    }

    public static void assignValue(Field field, @Nullable Object instance, Object value) {
        try {
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            LOGGER.error(format("Unable to set value %s for field %s. Field is not accessible", value.toString(), field.getName()), e);
        }
    }

    public static void assignCatalogeValue(Field field, Object value) {
        toggleFinal(field);
        assignValue(field, null, value);
    }

}
