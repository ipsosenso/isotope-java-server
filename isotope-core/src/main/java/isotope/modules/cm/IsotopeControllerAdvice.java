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

package isotope.modules.cm;

import isotope.commons.validation.ValidationError;
import isotope.commons.validation.ValidationErrorBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by qletel on 07/08/2017.
 */
@ControllerAdvice
public class IsotopeControllerAdvice {

	/**
	 * Intercepte les {@link BindException} et les convertit en {@link ValidationError}.
	 *
	 * @param e l'exception
	 * @return l'exception convertie
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	@ResponseBody
	public ValidationError handleBadRequest(BindException e) {
		return fromBindingResult(e.getBindingResult());
	}

	/**
	 * Intercepte les {@link MethodArgumentNotValidException} et les convertit en {@link ValidationError}.
	 *
	 * @param e l'exception
	 * @return l'exception convertie
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ValidationError handleBadRequest(MethodArgumentNotValidException e) {
		return fromBindingResult(e.getBindingResult());
	}

	/**
	 * Crée un {@link ValidationError} à partir d'un {@link BindingResult}.
	 *
	 * @param bindingResult les erreurs sous la forme d'un {@link BindingResult}
	 * @return les erreurs sous la forme d'un {@link ValidationError}
	 */
	private ValidationError fromBindingResult(BindingResult bindingResult) {
		if (bindingResult == null) {
			return null;
		}
		return ValidationErrorBuilder.fromBindingErrors(bindingResult);
	}

}
