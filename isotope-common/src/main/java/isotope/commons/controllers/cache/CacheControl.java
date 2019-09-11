/*
 * Isotope 1.6
 * Copyright (C) 2019 IpsoSenso
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package isotope.commons.controllers.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by oturpin on 20/06/16.
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheControl {
    /**
     * The <code>cache-control</code> policies to apply to the response.
     *
     * @see CachePolicy
     */
    CachePolicy[] policy() default { CachePolicy.NO_CACHE };

    /**
     *  The maximum amount of time, in seconds, that this content will be considered fresh.
     */
    int maxAge() default 0;

    /**
     * The maximum amount of time, in seconds, that this content will be considered fresh
     * only for shared caches (e.g., proxy) caches.
     */
    int sharedMaxAge() default -1;
}
