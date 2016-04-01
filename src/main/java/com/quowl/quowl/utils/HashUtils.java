package com.quowl.quowl.utils;

import org.hashids.Hashids;

/**
 * This class provides methods to encode and decode some kind of
 * information.
 * For example to encode user id:
 * <pre>
 *     HashUtils.encodeUserID(Long id);
 * </pre>
 *
 * @see Hashids
 */
public class HashUtils {

    /**
     * Salt to encode data.
     */
    private static final String salt = "ItsSaltDudeSoDonthackUs";
    /**
     * Hast for user IDs.
     */
    private static Hashids userIdHas = new Hashids(salt);

    /**
     * Encodes given user id.
     *
     * @param id id to encode.
     * @return the encrypt string.
     */
    public static String encodeUserID(Long id) {
        return userIdHas.encode(id);
    }

    /**
     * Decodes user id;
     *
     * @param hash the encrypted string.
     * @return user id.
     */
    public static Long decodeUserID(String hash) {
        return userIdHas.decode(hash)[0];
    }

}
