package com.divao.mesanews.domain.exception

abstract class FieldValidationException : RuntimeException()
class InvalidFormFieldException : FieldValidationException()
class EmptyRequiredFieldException : FieldValidationException()