package minvakt.managers;

/**
 * Created by OlavH on 09-Jan-17.
 */
@Deprecated
public enum ReturnCode {

    OK,
    USER_NOT_FOUND,
    USER_IS_EMPTY,
    HAS_NO_SHIFTS,
    SHIFT_NOT_FOUND,
    SHIFT_ALREADY_IN_LIST,
    SHIFT_OVERLAPS
}
