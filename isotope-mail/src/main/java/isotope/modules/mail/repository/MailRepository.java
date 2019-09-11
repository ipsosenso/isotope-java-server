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
package isotope.modules.mail.repository;

import isotope.modules.mail.model.Mail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.Stream;

/**
 * Manage mail persistence
 */
@Repository
public interface MailRepository extends MailDefaultRepository {

    /**
     * Return a stream of mail with specific attributes :
     *
     * @param dateLimite the upper limit (inclusive) to filter mail by creation date
     * @param mailStatus expected status see {@link isotope.modules.mail.model.MAIL_STATUS}
     * @return
     */
    @Query("select m from Mail m where m.creationDate<= :dateLimite and m.status=:mailStatus")
    Stream<Mail> streamAllEntities(@Param("dateLimite") LocalDateTime dateLimite, @Param("mailStatus") String mailStatus);

    /**
     * Deletes all records where creationDate is before limit
     */
    @Transactional
    int deleteByCreationDateBefore(LocalDateTime limit);

}
