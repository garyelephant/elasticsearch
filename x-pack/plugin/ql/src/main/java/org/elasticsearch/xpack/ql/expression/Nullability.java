/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0; you may not use this file except in compliance with the Elastic License
 * 2.0.
 */
package org.elasticsearch.xpack.ql.expression;

public enum Nullability {
    TRUE,    // Whether the expression can become null
    FALSE,   // The expression can never become null
    UNKNOWN; // Cannot determine if the expression supports possible null folding

    /**
     * Return the logical AND of a list of {@code Nullability}
     * <pre>
     *  UNKNOWN AND TRUE/FALSE/UNKNOWN = UNKNOWN
     *  FALSE AND FALSE = FALSE
     *  TRUE AND FALSE/TRUE = TRUE
     * </pre>
     */
    public static Nullability and(Nullability... nullabilities) {
        Nullability value = null;
        for (Nullability n: nullabilities) {
            switch (n) {
                case UNKNOWN:
                    return UNKNOWN;
                case TRUE:
                    value = TRUE;
                    break;
                case FALSE:
                    if (value == null) {
                        value = FALSE;
                    }
            }
        }
        return value != null ? value : FALSE;
    }
}
