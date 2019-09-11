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

package isotope.config;


import isotope.modules.cm.menu.repository.MenuRepository;
import isotope.modules.cm.menu.service.MenuEntryService;
import isotope.modules.cm.menu.service.MenuService;
import isotope.modules.cm.menu.service.impl.MenuServiceImpl;
import isotope.modules.user.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by qletel on 19/05/2016.
 */
@Configuration
public class IsotopeServiceConfiguration {

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserService getUserService() {
        return new UserServiceImpl();
    }

    @Bean
    public GroupService getGroupService() {
        return new GroupServiceImpl();
    }

    @Bean
    public MenuService getMenuService(MenuRepository repository, FunctionService functionService, MenuEntryService menuEntryService){
        return new MenuServiceImpl(repository, functionService, menuEntryService);
    }


}
