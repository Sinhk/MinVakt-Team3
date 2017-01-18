package minvakt.datamodel.enums;

import minvakt.datamodel.EmployeeCategory;

/**
 * Created by OlavH on 09-Jan-17.
 */
public enum EmployeeType {

    ADMIN,
    HEALTHWORKER,
    NURSE,
    ASSISTENT;

    public static EmployeeType of(EmployeeCategory category){

        return valueOf(String.valueOf(category.getCategoryId()));

    }
}
