package com.example.gateway.api.rest.converter

import org.slf4j.LoggerFactory

class CsvProcessor {
    companion object {
        private const val DEFAULT_SEPARATOR = ','
        private const val LINE_SEPARATOR = '\n'
        private val LOG = LoggerFactory.getLogger(CsvProcessor::class.java)

        fun getCsvBits(csv: String): HashMap<String, String> {
            val bits = csv.split(LINE_SEPARATOR)
            val keyBits = bits[0].split(DEFAULT_SEPARATOR)
            val valBits = bits[1].split(DEFAULT_SEPARATOR)

            val map = HashMap<String, String>()
            if (keyBits.size != valBits.size) {
                LOG.error("Could not convert the message to CSV!!!")
                return map
            }

            for (n in 0..keyBits.size - 1) {
                map[keyBits[n]] = valBits[n]
            }

            return map
        }
    }

}
