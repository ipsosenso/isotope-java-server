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

package isotope.modules.mail.config;

import isotope.modules.mail.service.ITemplateService;
import isotope.modules.mail.service.impl.ThymleafTemplateService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.validation.constraints.NotNull;

/**
 * Configuration du module mail
 * Created by oturpin on 02/01/17.
 */
@Configuration
@EnableAutoConfiguration
@PropertySource("classpath:application-isotope-mail.properties")
@ConfigurationProperties(prefix = "isotope.mail")
public class MailModuleConfiguration {

    /**
     * Expression régulière permettant de filtrer les envois de mails
     * Exemple :
     * isotope.mail.filter=ipsosenso.com : autorisera uniquement les mails ipsosenso.com
     * isotope.mail.filter=ipsosenso.com,sample.com :  autorisera les mails *.ipsosenso.com et *.sample.com
     * isotope.mail.filter=otu@ipsosenso.com :  autorisera uniquement les mails vers otu@ipsosenso.com
     */
    private String filter = "";

    /**
     * Cron expression permettant de programmer l'exécution de la tâche d'envoi des mails
     * Exemple :
     * isotope.mail.send-mail-cron-expression=0 0 * * * * the top of every hour of every day.
     *
     * See also {@link org.springframework.scheduling.support.CronSequenceGenerator}
     */
    @NotNull
    private String sendMailCronExpression;

    /**
     * Cron expression permettant de programmer l'exécution de la tâche de suppression des mails
     * Exemple pour une tâche exécutée à 4H du matin
     * isotope.mail.delete-mail-cron-expression=0 0 4 * * *
     *
     * See also {@link org.springframework.scheduling.support.CronSequenceGenerator}
     */
    @NotNull
    private String deleteMailCronExpression;


    /**
     *  Durée maximale de rétention d'un mail avant sa suppression de la base de données
     */
    @NotNull
    private Integer maxRetentionBeforeDelete;


    /**
     * Configuration du TemplateService - Par défaut utilise l'implémentation Thymleaf
     *
     * Il est possible de surcharger le templateService en définissant le bean "overrideMailTemplateService" dans la
     * configuration projet
     */
    @Bean
    @ConditionalOnMissingBean(name = "overrideMailTemplateService")
    public ITemplateService getMailTemplateService() {
        return new ThymleafTemplateService();
    }


    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getSendMailCronExpression() {
        return sendMailCronExpression;
    }

    public void setSendMailCronExpression(String sendMailCronExpression) {
        this.sendMailCronExpression = sendMailCronExpression;
    }

    public String getDeleteMailCronExpression() {
        return deleteMailCronExpression;
    }

    public void setDeleteMailCronExpression(String deleteMailCronExpression) {
        this.deleteMailCronExpression = deleteMailCronExpression;
    }

    public Integer getMaxRetentionBeforeDelete() {
        return maxRetentionBeforeDelete;
    }

    public void setMaxRetentionBeforeDelete(Integer maxRetentionBeforeDelete) {
        this.maxRetentionBeforeDelete = maxRetentionBeforeDelete;
    }
}
