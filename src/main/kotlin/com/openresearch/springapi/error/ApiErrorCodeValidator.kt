package com.openresearch.springapi.error

class ApiErrorCodeValidator {

    companion object {

        private val usedCodes: MutableSet<Int> = mutableSetOf()
        private val range = 100000..999999

        fun validateCode(code: Int): Int {
            if (code !in range) {
                throw IllegalArgumentException("error code $code out of range $range")
            }
            if (code in usedCodes) {
                throw IllegalArgumentException("error code $code already used")
            }
            usedCodes.add(code)
            return code
        }
    }

}
