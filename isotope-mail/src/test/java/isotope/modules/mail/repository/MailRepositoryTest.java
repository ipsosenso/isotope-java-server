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

import isotope.modules.mail.helper.MailRecipientSpec;
import isotope.modules.mail.model.MAIL_STATUS;
import isotope.modules.mail.model.Mail;
import isotope.modules.mail.model.MailRecipient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MailRepositoryTest {


    @Autowired
    private MailRepository repository;


    @Test
    public void testDeleteOldMails(){

        // 1er janvier 2017 11h10
        LocalDateTime willBeDeleted = LocalDateTime.of(2017,01,01,15,0);
        LocalDateTime wontBeDeleted = LocalDateTime.of(2017,05,01,15,0);
        LocalDateTime limit = LocalDateTime.of(2017,02,01,15,0);


        Mail mail = new Mail();
        mail.setCreationDate(willBeDeleted);
        mail.setBody("Fair is foul, and foul is fair; Hover through the fog and filthy air.");
        mail.setSubject("William S.");
        mail.setPriority(0);
        mail.setStatus(MAIL_STATUS.SENT.name());
        mail.setMailFrom("willy@ipsosenso.com");
        mail.setMailReplyTo("willy+to@ipsosenso.com");

        MailRecipient r = new MailRecipient();
        r.setMailTo("macbeth@ipsosenso.com");
        r.setType(MailRecipientSpec.TYPE.CC.name());
        r.setMail(mail);
        mail.addRecipient(r);

        repository.save(mail);


        Mail recentMail = new Mail();
        recentMail.setCreationDate(wontBeDeleted);
        recentMail.setBody("So foul and fair a day I have not seen.");
        recentMail.setSubject("Shake shake shake");
        recentMail.setPriority(0);
        recentMail.setStatus(MAIL_STATUS.SENT.name());
        recentMail.setMailFrom("willy@ipsosenso.com");
        recentMail.setMailReplyTo("willy+to@ipsosenso.com");
        repository.save(recentMail);


        // check intermédiaire
        assertThat(repository.findAll().size()).isEqualTo(2);

        assertThat(repository.deleteByCreationDateBefore(limit)).isEqualTo(1);

    }
}
