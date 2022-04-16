package com.parkinguk.parkinguk.service.validation;

public interface ValidationService <T>{
    boolean checkIfExist(T arg) throws Exception;

}
