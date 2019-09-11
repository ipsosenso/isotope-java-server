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

package isotope.modules.security.locale.impl;

import isotope.modules.user.model.User;
import isotope.modules.security.locale.ILocaleService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Created by mperalta on 29/11/2016.
 */
@ConditionalOnMissingClass("LocaleService")
@Service
public class DefaultLocaleService implements ILocaleService {
    @Override
    public Locale getUserLocale(User user) {
        return Locale.getDefault();
    }
}
