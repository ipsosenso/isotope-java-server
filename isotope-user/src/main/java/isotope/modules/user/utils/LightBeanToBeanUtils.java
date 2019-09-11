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

package isotope.modules.user.utils;

import isotope.modules.user.lightbeans.*;
import isotope.modules.user.model.*;

import java.util.stream.Collectors;

/**
 * Utilitaire qui permet de transformer les beans en light bean
 * <p>
 * Created by mperalta on 18/10/2016.
 */
public class LightBeanToBeanUtils {

	public static AssoFunctionRoleLightBean copyFrom(AssoFunctionRole assofunctionRole) {
		AssoFunctionRoleLightBean assoFunctionRoleLightBean = new AssoFunctionRoleLightBean();
		assoFunctionRoleLightBean.setId(assofunctionRole.getId());
		assoFunctionRoleLightBean.setIdFunction(assofunctionRole.getIdFunction());
		assoFunctionRoleLightBean.setIdRole(assofunctionRole.getIdRole());
		return assoFunctionRoleLightBean;
	}

	public static FunctionLightBean copyFrom(Function function) {
		FunctionLightBean functionLightBean = new FunctionLightBean();
		functionLightBean.setId(function.getId());
		functionLightBean.setCode(function.getCode());
		functionLightBean.setDateCreation(function.getDateCreation());
		function.getType().ifPresent(functionLightBean::setType);
		return functionLightBean;
	}

	public static RoleLightBean copyFrom(Role role) {
		RoleLightBean roleLightBean = new RoleLightBean();
		roleLightBean.setId(role.getId());
		roleLightBean.setCode(role.getCode());
		roleLightBean.setDateCreation(role.getDateCreation());
		return roleLightBean;
	}

	public static UrlLightBean copyFrom(Url url) {
		UrlLightBean urlLightBean = new UrlLightBean();
		urlLightBean.setId(url.getId());
		urlLightBean.setUrl(url.getUrl());
		url.getCodeLangue().ifPresent(urlLightBean::setCodeLangue);
		urlLightBean.setIdFunction(url.getIdFunction());
		urlLightBean.setDateCreation(url.getDateCreation());
		return urlLightBean;
	}

	public static UserLightbean copyFrom(User user) {
		UserLightbean userLightbean = new UserLightbean();
		userLightbean.setId(user.getId());
		userLightbean.setLastname(user.getLastname());
		userLightbean.setFirstname(user.getFirstname());
		userLightbean.setEmail(user.getEmail());
		userLightbean.setLogin(user.getLogin());
		user.getPhoneNumber().ifPresent(userLightbean::setPhoneNumber);
		user.getCompany().ifPresent(userLightbean::setCompany);
		userLightbean.setDisabled(user.isDisabled());
		userLightbean.setGroups(user.getGroups());
		userLightbean.setRoles(user.getRoles().stream().map(LightBeanToBeanUtils::copyFrom).collect(Collectors.toSet()));
		return userLightbean;
	}

}
