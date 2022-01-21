package com.mooop.board.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Project : MSimpleBoard
 * Package :com.mooop.board.config.property
 * Author :  MOoop
 * Date : 20/01/2022
 * Desc :
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "msb.confirm")
public class ConfirmProperties {

    private String confirm_url;

    private Integer max_retry_count;

    private String from_id;

    private String from_pwd;

}
