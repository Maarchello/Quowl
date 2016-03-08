package com.quowl.quowl.utils;


public enum ExecutionStatus {


    OK,
    This_user_name_already_registered,
    Wrong_email_address,
    User_data_empty,

    Invalid_user_data,

    /**
     * S000 - статус для неизвестных ошибок
     */
    S000,

    /**
     * S100 - данный статус говорит о том, что книга уже прочитана.
     */
    S100,

    /**
     * S200 - статус говорит о том, что количество цитат на странице Feed достигло максимума
     */
    S200,

}
