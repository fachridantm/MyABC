package com.outivox.myabc.core.util

/**
 * Searches the query string for the first value with the given key.
 *
 * @param key the key to search for
 * @return the decoded value, or null if the key is not found
 */
expect fun PlatformUri.getUriQueryParameter(key: String): String?
