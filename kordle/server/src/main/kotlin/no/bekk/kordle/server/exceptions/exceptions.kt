package no.bekk.kordle.server.exceptions

class GjettetHarUgyldigLengdeException(message: String? = null) : RuntimeException(message)
class GjettetErIkkeIOrdlistaException(message: String? = null) : RuntimeException(message)
class BrukerenEksistererAllerede(message: String? = null) : RuntimeException(message)
class BrukerenEksistererIkke(message: String? = null) : RuntimeException(message)
