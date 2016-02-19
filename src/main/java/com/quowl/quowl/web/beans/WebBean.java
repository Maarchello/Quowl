package com.quowl.quowl.web.beans;


public interface WebBean<D> {

    void copyDataFromDomain(D obj);

    void copyDataToDomain(D obj);


}
